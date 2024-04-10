package io.github.zhoujunlin94.example.security.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.github.zhoujunlin94.meet.common.util.EncryptUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author zhoujunlin
 * @date 2023年03月30日 22:53
 * @desc
 */
@Slf4j
public class JwtTokenUtil {

    private static final String KEY_STORE_TYPE = "JKS";
    private static final String KEY_PASS = "Meet123456";
    private static final String STORE_PASS = "123456Meet";
    private static final String KEY_STORE_ALIAS = "security-test";

    private static final InputStream JKS_INPUT_STREAM = Thread.currentThread().getContextClassLoader().getResourceAsStream("security-test.keystore");
    private static PrivateKey privateKey = null;
    private static String privateKeyStr = null;
    private static PublicKey publicKey = null;
    private static String publicKeyStr = null;


    static {
        try {
            KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE);
            keyStore.load(JKS_INPUT_STREAM, STORE_PASS.toCharArray());
            privateKey = (PrivateKey) keyStore.getKey(KEY_STORE_ALIAS, KEY_PASS.toCharArray());
            privateKeyStr = EncryptUtil.encryptBASE64(privateKey.getEncoded());
            publicKey = keyStore.getCertificate("security-test").getPublicKey();
            publicKeyStr = EncryptUtil.encryptBASE64(publicKey.getEncoded());
        } catch (Exception e) {
            log.error("从jks中解析公钥私钥出错", e);
        }
    }

    public static String generateToken(String subject, int expirationSeconds) {
        return Jwts.builder()
                //.setClaims()
                .setSubject(subject)
                .setExpiration(DateUtil.offsetSecond(DateUtil.date(), expirationSeconds))
                //.signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .setId(IdUtil.fastSimpleUUID())
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }


    public static String parseSubjectFromToken(String token) {
        String subject = StrUtil.EMPTY;
        try {
            Claims claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
            subject = claims.getSubject();
        } catch (Exception e) {
            log.error("解析token失败", e);
        }
        return subject;
    }

}