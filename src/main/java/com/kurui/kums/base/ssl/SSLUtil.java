package com.kurui.kums.base.ssl;

import java.io.FileInputStream;
import java.io.PrintStream;
import java.security.*;
import javax.net.ssl.*;

public class SSLUtil {

	public SSLUtil() {
	}

	public static void configSSLSocketFactory(HttpsURLConnection conn,
			SSLSocketFactory factory) throws Exception {
		conn.setSSLSocketFactory(factory);
	}

	public static void configSSLSocketFactory_TrustManager(
			HttpsURLConnection conn, String trustStorePath, String trustpass,
			String algorithm, String provider) throws Exception {
		SSLSocketFactory sslFactory = getSSLSocketFactory(trustStorePath,
				trustpass, algorithm, provider);
		conn.setSSLSocketFactory(sslFactory);
	}

	public static void configSSLSocketFactory(HttpsURLConnection conn,
			String keyStorePath, String keypass, String trustStorePath,
			String trustpass, String algorithm) throws Exception {
		SSLSocketFactory sslfactory = getSSLSocketFactory(keyStorePath,
				keypass, trustStorePath, trustpass, algorithm);
		conn.setSSLSocketFactory(sslfactory);
	}

	public static SSLSocketFactory getSSLSocketFactory(String trustStorePath,
			String trustpass, String algorithm, String provider) {
		SSLSocketFactory sslFactory = null;
		try {
			TrustManager tm[] = { new MyX509TrustManager(trustStorePath,
					trustpass, algorithm, provider) };
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, tm, new SecureRandom());
			sslFactory = sslContext.getSocketFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sslFactory;
	}

	public static SSLSocketFactory getSSLSocketFactory(String keyStorePath,
			String keypass, String trustStorePath, String trustpass,
			String algorithm) {
		SSLSocketFactory sslfactory = null;
		try {
			KeyStore keystore = getKeyStore("JKS", keyStorePath, keypass);
			KeyManagerFactory keyManagerFactory = initKeyManagerFactory(
					algorithm, keystore, keypass);
			KeyStore truststore = getKeyStore("JKS", trustStorePath, trustpass);
			TrustManagerFactory trustManagerFactory = initTrustManagerFactory(
					algorithm, truststore);
			SSLContext ctx = SSLContext.getInstance("SSL");
			ctx.init(keyManagerFactory.getKeyManagers(), trustManagerFactory
					.getTrustManagers(), null);
			sslfactory = ctx.getSocketFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sslfactory;
	}

	public static KeyStore getKeyStore(String type, String storeFile,
			String password) {
		KeyStore ks = null;
		try {
			FileInputStream in = new FileInputStream(storeFile);
			ks = KeyStore.getInstance(type);
			ks.load(in, password.toCharArray());
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ks;
	}

	public static KeyManagerFactory initKeyManagerFactory(String algorithm,
			KeyStore keystore, String password) {
		KeyManagerFactory kmf = null;
		try {
			kmf = KeyManagerFactory.getInstance(algorithm);
			kmf.init(keystore, password.toCharArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kmf;
	}

	public static TrustManagerFactory initTrustManagerFactory(String algorithm,
			KeyStore truststore) {
		TrustManagerFactory tmf = null;
		try {
			tmf = TrustManagerFactory.getInstance(algorithm);
			tmf.init(truststore);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmf;
	}

	public KeyManager[] getKeyManager(KeyManagerFactory keyfactory) {
		KeyManager keyManager[] = keyfactory.getKeyManagers();
		return keyManager;
	}

	public TrustManager[] getTrustManager(TrustManagerFactory trustfactory) {
		TrustManager trustManager[] = trustfactory.getTrustManagers();
		return trustManager;
	}

	public static void trustAllHttpsCertificates() {
		TrustManager trustAllCerts[] = new TrustManager[1];
		TrustManager tm = new MyTrustManager();
		trustAllCerts[0] = tm;
		try {
			SSLContext context = SSLContext.getInstance("SSL");
			context.init(null, trustAllCerts, null);
			HttpsURLConnection.setDefaultSSLSocketFactory(context
					.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
		HostnameVerifier hv = new HostnameVerifier() {

			public boolean verify(String urlHostName, SSLSession session) {
				System.out.println((new StringBuilder("Warning: URL Host: "))
						.append(urlHostName).append(" vs. ").append(
								session.getPeerHost()).toString());
				return true;
			}

		};
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
	}

	public static void setSystemTrustStore(String trustStorePath,
			String keyStoreType) {
		System.setProperty("javax.net.ssl.trustStore", trustStorePath);
		System.setProperty("javax.net.ssl.keyStoreType", keyStoreType);
	}

	public static void setSystemKeyStore(String keyStorePath, String keypass) {
		System.setProperty("javax.net.ssl.keyStore", keyStorePath);
		System.setProperty("javax.net.ssl.keyStorePassword", keypass);
	}

	public static void setSystemProtocolHandler_IBM() {
		// import com.ibm.jsse.IBMJSSEProvider;
		// Security.addProvider(new IBMJSSEProvider());
		System.setProperty("java.protocol.handler.pkgs",
				"com.ibm.net.ssl.internal.www.protocol");
	}
}
