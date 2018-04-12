package com.xiaoyu.lingdian.tool.encrypt;

import java.security.AlgorithmParameters;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import net.sf.json.JSONObject;

public class SecretUtils {

    private SecretUtils() {
    }

    public static String AES128CBCdecrypt(String encryptedData, String iv, String appId, String sessionKey) throws Exception {

// 被加密的数据

        byte[] dataByte = Base64.decodeBase64(encryptedData);

// 加密秘钥

        byte[] keyByte = Base64.decodeBase64(sessionKey);

// 偏移量

        byte[] ivByte = Base64.decodeBase64(iv);

        Security.addProvider(new BouncyCastleProvider());

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");

        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");

        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");

        parameters.init(new IvParameterSpec(ivByte));

        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化

        byte[] resultByte = cipher.doFinal(dataByte);

        if (null != resultByte && resultByte.length > 0) {

            String result = new String(resultByte, "UTF-8");

            JSONObject obj = JSONObject.fromObject(result);

            JSONObject watermark = obj.getJSONObject("watermark");

            if (!watermark.getString("appid").equals(appId)) {

                throw new Exception("与小程序appid不符合");

            }

            if (System.currentTimeMillis() - watermark.getLong("timestamp") * 1000 > 10000) {

                throw new Exception("验证时间过长");

            }

            return result;

        }

        return null;

    }

}
