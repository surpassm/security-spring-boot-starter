package com.github.surpassm.security.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author mc
 * version 1.0v
 * date 2019/2/10 19:55
 * description TODO
 */
@Slf4j
public class JwtTokenUtils implements Serializable {

    private static final String AUTHORITIES_KEY = "userId";
	/**
	 * 签名密钥
	 */
	private static final String SECRET_KEY ="surpassm";
	/**签名方式*/
	private final static SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
	/**
	 * 过期时间是3600秒，既是1个小时
	 */
    private static final long EXPIRATION = 3600L;
	/**
	 * 选择了记住我之后的过期时间为7天
	 */
    private static final long EXPIRATION_REMEMBER = 604800L;


	/**
	 * 创建Token 只带用户名
	 * @param userId
	 * @param isRememberMe
	 * @return
	 */
    public static String createToken(String userId,boolean isRememberMe){
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
		//创建Token令牌
        return Jwts.builder()
				//设置面向用户
                .setSubject(userId)
				//添加权限属性
                .claim(AUTHORITIES_KEY,userId)
				//设置失效时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))

				//生成签名
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

	/**
	 * 获取用户id
	 * @param token
	 * @return
	 */
	public static String getUsernameFromToken(String token){
		//解析Token的payload
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();

		return  claims.get(AUTHORITIES_KEY).toString();
	}
	/**
	 * 验证Token是否正确
	 * @param token
	 * @return
	 */
	public static boolean validateToken(String token){
		try {
			//通过密钥验证Token
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {                                 //JWT格式错误
			log.info("Invalid JWT token.");
			log.trace("Invalid JWT token trace: {}", e);
		} catch (ExpiredJwtException e) {                                   //JWT过期
			log.info("Expired JWT token.");
			log.trace("Expired JWT token trace: {}", e);
		} catch (UnsupportedJwtException e) {                               //不支持该JWT
			log.info("Unsupported JWT token.");
			log.trace("Unsupported JWT token trace: {}", e);
		} catch (IllegalArgumentException e) {                              //参数错误异常
			log.info("JWT token compact of handler are invalid.");
			log.trace("JWT token compact of handler are invalid trace: {}", e);
		}
		return false;
	}




	/**
	 * 生成token 可以携带资源和设置过期时间
	 * @param subject  用户名
	 * @param claims
	 * @return
	 */
	public static String generateAccessToken(String subject, Map<String, Object> claims, Integer expiration) {
		return generateToken(subject, claims, expiration);
	}

	/**
	 *
	 * @param subject
	 * @param claims
	 * @param expiration
	 * @return
	 */
	private static String generateToken(String subject, Map<String, Object> claims, long expiration) {
		return Jwts.builder()
				//一个map 可以资源存放东西进去
				.setClaims(claims)
				//  用户名写入标题
				.setSubject(subject)
				.setId(UUID.randomUUID().toString())
				.setIssuedAt(new Date())
				//过期时间
				.setExpiration(generateExpirationDate(expiration))
				//数字签名
				.signWith(SIGNATURE_ALGORITHM, SECRET_KEY)
				.compact();
	}

	/**
	 * 生成失效时间
	 * @param expiration
	 * @return
	 */
	private static Date generateExpirationDate(long expiration) {
		return new Date(System.currentTimeMillis() + expiration);
	}

	/**
	 * 根据token 获取生成时间
	 * @param token
	 * @return
	 */
	public Date getCreatedDateFromToken(String token) {
		Date created;
		try {
			final Claims claims = getClaimsFromToken(token);
			created = claims.getIssuedAt();
		} catch (Exception e) {
			created = null;
		}
		return created;
	}

	/**
	 * token 是否过期
	 * @param token
	 * @return
	 */
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * 根据token 获取过期时间
	 * @param token
	 * @return
	 */
	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	/**
	 * 重置(更新)token 过期时间
	 * @param token
	 * @param expiration
	 */
	public String restTokenExpired(String token,long expiration){

		final Claims claims = getClaimsFromToken(token);
		Jwts.builder()
				//一个map 可以资源存放东西进去
				.setClaims(claims)
				//  用户名写入标题
				.setSubject(claims.getSubject())
				.setExpiration(new Date(expiration));
		//claims.setExpiration(new Date(expiration));
		// String refreshedToken = generateAccessToken(claims.getSubject(), claims,expiration);
		return "";
	}

	/***
	 * 解析token 信息
	 * @param token
	 * @return
	 */
	public static Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					//签名的key
					.setSigningKey(SECRET_KEY)
					// 签名token
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}


}
