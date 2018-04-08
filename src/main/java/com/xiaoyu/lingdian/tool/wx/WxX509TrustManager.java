package com.xiaoyu.lingdian.tool.wx;

import java.security.cert.CertificateException;  
import java.security.cert.X509Certificate;  
import javax.net.ssl.X509TrustManager;  
  
/** 
 * 证书信任管理器（用于WX https请求） 
 */
public class WxX509TrustManager implements X509TrustManager {  
  
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
    }  
  
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
    }  
  
    public X509Certificate[] getAcceptedIssuers() {  
        return null;  
    }

}