package com.imooc.wechatshop.utils.otherUtils;//package com.example.demo.utils;
//
//import com.aries.core.constant.AriesConstantInterface;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtBuilder;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.apache.commons.codec.binary.Base64;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * JWT 生成token工具类
// *
// * Created by liuyi on 17/11/28.
// */
//
//
//@Component
//public class JwtUtil {
//
//	/**
//	 * 由字符串生成加密key
//	 * @return
//	 */
//	public static SecretKey generalKey(){
//		String stringKey = AriesConstantInterface.JWT_SECRET;
//		byte[] encodedKey = Base64.decodeBase64(stringKey);
//	    SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
//	    return key;
//	}
//
//	/**
//	 * 创建jwt
//	 * @param userId
//	 * @param channelId
//	 * @param mobile
//	 * @param ttlMillis
//	 * @return
//	 * @throws Exception
//	 */
//	public static Map<String,Object> createJWT(String userId, String channelId, String mobile, long ttlMillis) throws Exception {
//		Map<String,Object> result = new HashMap<String, Object>();
//		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//		long nowMillis = System.currentTimeMillis();
//		SecretKey key = generalKey();
//		JwtBuilder builder = Jwts.builder()
//				.setIssuer(userId)
//				.setAudience(channelId)
//				.setSubject(mobile)
//		    	.signWith(signatureAlgorithm, key);
//		long expMillis = 0;
//		if (ttlMillis >= 0) {
//			expMillis = nowMillis + ttlMillis;
//		    Date exp = new Date(expMillis);
//		    builder.setExpiration(exp);
//
//		}
//		result.put("token",builder.compact());
//		result.put("exp",expMillis);
//		return result;
//	}
//
//	/**
//	 * 解密jwt
//	 * @param token
//	 * @return token信息的用户信息
//	 * @throws Exception
//	 */
//	public static Claims parseJWT(String token) throws Exception{
//		SecretKey key = generalKey();
//		Claims claims = Jwts.parser()
//		   .setSigningKey(key)
//		   .parseClaimsJws(token).getBody();
//		return claims;
//	}
//
//}
