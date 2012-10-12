package com.kurui.kums.base.ssl;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;

public class MyKeyManager
{

    public MyKeyManager()
    {
    }

    public static KeyManager[] getKeyManager(String s, String s1, String s2, String s3)
    {
        try
        {
            java.security.KeyStore keystore = SSLUtil.getKeyStore("JKS", s, s1);
            KeyManagerFactory keymanagerfactory = KeyManagerFactory.getInstance(s2);
            keymanagerfactory.init(keystore, s1.toCharArray());
            KeyManager akeymanager[] = keymanagerfactory.getKeyManagers();
            return akeymanager;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return null;
    }
}
