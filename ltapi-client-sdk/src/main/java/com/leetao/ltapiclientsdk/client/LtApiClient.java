package com.leetao.ltapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.leetao.ltapiclientsdk.model.domain.User;
import com.leetao.ltapiclientsdk.utils.SignUtils;
import java.util.HashMap;
import java.util.Map;

public class LtApiClient {

	private String accessKey;

	private String secretKey;

	private String url;

	public LtApiClient(String accessKey,String secretKey){
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}

	public LtApiClient(String accessKey,String secretKey,String url){
		this(accessKey,secretKey);
		this.url = url;
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
		map.put("sign", SignUtils.getSign(body,secretKey));
		return map;
	}

	public String getNameByGet(String name){
		Map<String,Object> map = new HashMap<>();
		map.put("name",name);
		String res = HttpUtil.get(url, map);
		System.out.println(res);
		return res;
	}

	public String getNameByPost(String name){
		Map<String,Object> map = new HashMap<>();
		map.put("name",name);
		String res = HttpUtil.post(url, map);
		System.out.println(res);
		return res;
	}

	public String getNameByPostJson(User user){
		String jsonStr = JSONUtil.toJsonStr(user);
		HttpResponse httpResponse = HttpRequest
				.post(url)
				.addHeaders(getHeaders(jsonStr))
				.body(jsonStr).execute();
		System.out.println(httpResponse.getStatus());
		String body = httpResponse.body();
		System.out.println(body);
		return body;
	}
}
