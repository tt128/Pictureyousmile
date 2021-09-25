package com.xxxx.store.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xxxx.store.entity.User;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Repository;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Repository
public class TokenUtil {

        public static final String JWT_SECRET_KEY = "testjwt";

        /**
         * 创建Token
         *
         * @param user 用户实体
         * @return Token
         */
        public static String createToken(User user) throws UnsupportedEncodingException {
//            System.out.println(user.getId());
            // 登录成功后生成JWT
            // JWT的header部分,该map可以是空的,因为有默认值{"alg":HS256,"typ":"JWT"}
            Map<String, Object> map = new HashMap<>();

            // 30分钟过期时间
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.MINUTE,30);
//            System.out.println(user.getId());

            return JWT.create()
                    // 添加头部
                    .withHeader(map)
                    // 添加payload
                    .withClaim("userid",user.getUser_Id())
                    .withClaim("id",user.getId()+"")
                    .withClaim("username",user.getUser_Id())


                    // 设置过期时间
                    .withExpiresAt(instance.getTime())
                    // 设置签名 密钥
                    .sign(Algorithm.HMAC256(JWT_SECRET_KEY));
        }

    public TokenUtil() {
    }

    /**
         * 检查Token是否有效
         *
         * @param token Token
         * @return 是否有效
         */
        public static boolean isValid(String token) {
            if (Strings.isNotBlank(token)) {
                try {
                    //创建验证对象,这里使用的加密算法和密钥必须与生成TOKEN时的相同否则无法验证
                    JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(JWT_SECRET_KEY)).build();
                    System.out.println(jwtVerifier);
                    //验证JWT
                    DecodedJWT decodedJwt = jwtVerifier.verify(token);
//                    System.out.println(decodedJwt.getPayload());
                    return new Date().before(decodedJwt.getExpiresAt());
                } catch (Exception e) {

                    return false;
                }
            } else {
                return false;
            }

        }
    public static DecodedJWT getClaimsFromToken(String token) {
            try {
                Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY);
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT jwt = verifier.verify(token);
                String userId = jwt.getClaim("id").asString();
//                System.out.println(userId);
                return jwt;
            } catch (Exception e) {
                return null;
            }

    }
//    public static void main(String[] args) {
//        //解析token
//        Claims claims = TokenUtil.parseJwt();
//        String id = claims.getId();
//        String subject = claims.getSubject();
//        String username = (String) claims.get("username");
//        String issuer = claims.getIssuer();
//        Date issuedAt = claims.getIssuedAt();
//        System.out.println("id :"+id+ "  " + "username :" +username + "  "+ "subject :" + subject+"issuer :"+issuer + " "+issuedAt);
//    }


}
