package com.leetao.ltapi;

import com.leetao.ltapi.common.model.domain.InterfaceInfo;
import com.leetao.ltapi.common.model.domain.User;
import com.leetao.ltapi.common.service.InnerInterfaceInfoService;
import com.leetao.ltapi.common.service.InnerUserInterfaceInfoService;
import com.leetao.ltapi.common.service.InnerUserService;
import com.leetao.ltapiclientsdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 在网关过滤器中统一对请求和相应进行处理
 *
 * @author leetao
 */

@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerUserService userService;

    @DubboReference
    private InnerInterfaceInfoService interfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService userInterfaceInfoService;

    //白名单，只有这些请求源能够访问
    private static final List<String> IP_WHITE_LIST = Arrays.asList("0:0:0:0:0:0:0:1","127.0.0.1");

    private static final String INTERFACE_HOST = "http://localhost:8090";
    //5分钟
    private static final long FIVE_MINUTES = 5 * 60L;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //请求日志
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一表示："+request.getId());
        log.info("请求路径："+request.getPath());
        log.info("请求方法："+request.getMethod());
        log.info("请求参数："+request.getQueryParams());
        log.info("请求来源地址："+request.getRemoteAddress());
        //黑白名单
        String sourceAddress = request.getLocalAddress().getHostString();
        String path = INTERFACE_HOST + request.getPath().value();
        String method = Objects.requireNonNull(request.getMethod()).toString();
        ServerHttpResponse response = exchange.getResponse();
        if(!IP_WHITE_LIST.contains(sourceAddress)){
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        //用户鉴权（ak,sk是否合法）
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        // 防止中文乱码
        String body = null;
        try {
            body = URLDecoder.decode(headers.getFirst("body"), StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        // 使用accessKey去数据库查询secretKey
        User invokeUser = null;
        try {
            invokeUser = userService.getInvokeUser(accessKey);
        } catch (Exception e) {
            log.error("getInvokeUser error", e);
        }
        if (invokeUser == null) {
            return handleInvokeError(response);
        }
        String secretKey = invokeUser.getSecretKey();
        String serverSign = SignUtils.getSign(body, secretKey);
        if (sign == null || !sign.equals(serverSign)) {
            return handleInvokeError(response);
        }

        if(nonce == null || Long.parseLong(nonce) > 10000L){
            return handleNoAuth(response);
        }
        //时间和当前时间不能超过5分钟
        long currentTime = System.currentTimeMillis() / 1000;
        if(currentTime - Long.parseLong(timestamp) >= FIVE_MINUTES){
            return handleNoAuth(response);
        }
        // 4. 请求的模拟接口是否存在
        InterfaceInfo invokeInterfaceInfo = null;
        try {
            invokeInterfaceInfo = interfaceInfoService.getInvokeInterfaceInfo(path, method);
        } catch (Exception e) {
            log.error("getInvokeInterfaceInfo error", e);
        }
        if (invokeInterfaceInfo == null) {
            return handleInvokeError(response);
        }

        //是否有调用次数
        //  是否有调用次数
        if (!userInterfaceInfoService.hasInvokeNum(invokeUser.getId(), invokeInterfaceInfo.getId())) {
            return handleInvokeError(response);
        }
        // 5. 请求转发，调用模拟接口
        return handleResponse(exchange, chain, invokeUser.getId(), invokeInterfaceInfo.getId());
    }

    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    private Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long userId, long interfaceInfoId) {
        try {
            // 从交换机拿到原始response
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓冲区工厂 拿到缓存数据
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到状态码
            HttpStatus statusCode = originalResponse.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        // 对象是响应式的
                        if (body instanceof Flux) {
                            // 我们拿到真正的body
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里面写数据
                            // 拼接字符串
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // 7. 调用成功，接口调用次数+1
                                try {
                                    userInterfaceInfoService.invokeInterfaceCount(userId, interfaceInfoId);
                                } catch (Exception e) {
                                    log.error("invokeInterfaceCount error", e);
                                }
                                // data从这个content中读取
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);// 释放掉内存
                                // 6.构建日志
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                String data = new String(content, StandardCharsets.UTF_8);// data
                                rspArgs.add(data);
                                log.info("<--- status:{} data:{}"// data
                                        , rspArgs.toArray());// log.info("<-- {} {}", originalResponse.getStatusCode(), data);
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            // 8.调用失败返回错误状态码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);// 降级处理返回数据
        } catch (Exception e) {
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }

    }

    /**
     * 通过ordered编排过滤器的优先级
     * @return 返回优先级
     */
    @Override
    public int getOrder() {
        return -1;
    }

    private Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    private Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }



}