package com.halo.auth.core.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Rsa key 工具类
 *
 * @author zhihao.mao
 */
public class RsaKeyUtils {

    /**
     * 获取公钥,用于解析token
     *
     * @param filename
     * @return
     * @throws Exception
     */
    public PublicKey getPublicKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        try (DataInputStream dis = new DataInputStream(Objects.requireNonNull(resourceAsStream))) {
            byte[] keyBytes = new byte[resourceAsStream.available()];
            dis.readFully(keyBytes);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        }
    }

    /**
     * 获取密钥 用于生成token
     *
     * @param filename
     * @return
     * @throws Exception
     */
    public PrivateKey getPrivateKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        try (DataInputStream dis = new DataInputStream(Objects.requireNonNull(resourceAsStream))) {
            byte[] keyBytes = new byte[resourceAsStream.available()];
            dis.readFully(keyBytes);

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        }
    }

    /**
     * 生成密钥对
     *
     * @param password
     * @param publicKeyPath
     * @param privateKeyPath
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public void generatePairKey(String password, String publicKeyPath, String privateKeyPath) throws NoSuchAlgorithmException, IOException {
        //若密码为空则随机密码
        password = Optional.ofNullable(password).orElse(UUID.randomUUID().toString());

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();

        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();

        Files.write(Paths.get(publicKeyPath), publicKeyBytes);
        Files.write(Paths.get(privateKeyPath), privateKeyBytes);
    }

}

