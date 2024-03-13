package com.leetao.ltapiinterface.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.leetao.ltapiinterface.modol.domain.User;

import java.util.HashMap;
import java.util.Map;

import static com.leetao.ltapiinterface.utils.SignUtils.getSign;

/**
 * 开发者在后端中发送请求
 * 在该客户端中使用签名认证功能
 * 防止任意用户都能直接进行访问
 * 秘钥一定不能直接发送出去
 * 正确做法是：将用户参数与秘钥进行拼接使用加密算法生成签名，
 * 服务器使用相同的加密算法进行签名认证
 *
 * 为了防止重放：
 * 可以使用随机数，维护哪些随机数已经使用过
 * 时间戳：规定相应时间段内只能发送规定数量的请求
 */
public class NameClient {
	private final String baseUrl = "http://localhost:8081/api/name/";

	private String accessKey;

	private String secretKey;

	public NameClient(String accessKey,String secretKey){
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}

	/**
	 * 获取请求头
	 * @return 返回能够封装在请求头中的map对象
	 */
	private Map<String,String> getHeaders(String body){
		Map<String ,String> map = new HashMap<>();
		map.put("accessKey",accessKey);
		//秘钥一定不能直接发送出去
		//map.put("secretKey",secretKey);
		//随机数
		map.put("nonce", RandomUtil.randomNumbers(4));
		map.put("body",body);
		map.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
		//签名
		map.put("sign",getSign(body,secretKey));
		return map;
	}

	public String getNameByGet(String name){
		Map<String,Object> map = new HashMap<>();
		map.put("name",name);
		String res = HttpUtil.get(baseUrl, map);
		System.out.println(res);
		return res;
	}

	public String getNameByPost(String name){
		Map<String,Object> map = new HashMap<>();
		map.put("name",name);
		String res = HttpUtil.post(baseUrl, map);
		System.out.println(res);
		return res;
	}

	public String getNameByPostJson(User user){
		String jsonStr = JSONUtil.toJsonStr(user);
		HttpResponse httpResponse = HttpRequest
				.post(baseUrl + "user")
				.addHeaders(getHeaders(jsonStr))
				.body(jsonStr).execute();
		System.out.println(httpResponse.getStatus());
		String body = httpResponse.body();
		System.out.println(body);
		return body;
	}




}
