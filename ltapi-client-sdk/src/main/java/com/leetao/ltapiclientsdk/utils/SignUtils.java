package com.leetao.ltapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

public class SignUtils {
	/**
	 * 将用户参数和秘钥进行拼接生成签名
	 * @param body 用户请求体
	 * @param secretKey 秘钥
	 * @return 返回生成签名
	 */
	public static String getSign(String body,String secretKey){
		Digester md5 = new Digester(DigestAlgorithm.SHA256);
		//将请求对象和秘钥进行拼接
		String context = body + "." + secretKey;
		return md5.digestHex(context);

	}
}
