package com.kurui.kums.base.ssl;


import java.io.*;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.*;
import java.util.Enumeration;
import org.apache.commons.httpclient.protocol.Protocol;

public class MyX509Certificate {

	public MyX509Certificate() {
	}

	public static X509Certificate convertCertificate(Certificate certIn) {
		CertificateFactory cf;
		ByteArrayInputStream bais;
		try {
			cf = CertificateFactory.getInstance("X.509");
			bais = new ByteArrayInputStream(certIn.getEncoded());

			X509Certificate cert = (X509Certificate) cf
					.generateCertificate(bais);
			return cert;
		} catch (CertificateException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static X509Certificate getX509Certificate(File file,
			String password, String alias) {

		Certificate cert;
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(file);
			char nPassword[] = (char[]) null;
			if (password == null || password.trim().equals(""))
				nPassword = (char[]) null;
			else
				nPassword = password.toCharArray();
			System.out.println((new StringBuilder(
					"��ʼ\tks.load(fis, nPassword),password=")).append(password)
					.toString());
			ks.load(fis, nPassword);
			fis.close();
			System.out.println((new StringBuilder("keystore type=")).append(
					ks.getType()).toString());
			Enumeration enums = ks.aliases();
			String keyAlias = null;
			if (enums.hasMoreElements()) {
				keyAlias = (String) enums.nextElement();
				System.out.println((new StringBuilder("alias=[")).append(
						keyAlias).append("]").toString());
			}
			System.out.println((new StringBuilder("is key entry=")).append(
					ks.isKeyEntry(keyAlias)).toString());
			PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
			cert = ks.getCertificate(keyAlias);
			X509Certificate x509cert = convertCertificate(cert);
			return x509cert;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Protocol getProtocol(String keyStorePath, String keypass,
			String trustStorePath, String trustpass, String keyAlgorithm,
			String trustAlgorithm, String provider) {
		Protocol myhttps;
		javax.net.ssl.KeyManager km[] = MyKeyManager.getKeyManager(
				keyStorePath, keypass, keyAlgorithm, provider);
		javax.net.ssl.TrustManager tm[] = MyX509TrustManager.getTrustManager(
				trustStorePath, trustpass, trustAlgorithm, provider);
		MySSLSocketFactory factory = new MySSLSocketFactory(km, tm);
		myhttps = new Protocol("https", factory, 443);
		Protocol.registerProtocol("https", myhttps);
		return myhttps;

	}

	public static void main(String args[]) {
		String email = "276628@qq.com.p12";
		File file = new File("c:\\276628@qq.com.p12");
		X509Certificate x509 = getX509Certificate(file, "111111", email);
		System.out.println(x509.getIssuerDN());
		System.out.println(x509.getSubjectDN());
	}
}
