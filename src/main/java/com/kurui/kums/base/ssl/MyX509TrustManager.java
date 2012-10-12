package com.kurui.kums.base.ssl;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;

public class MyX509TrustManager implements X509TrustManager {

	private String trustedCert;
	private String passphrase;
	private String algorithm;
	private String provider;
	X509TrustManager userdefinedX509TrustManager;

	public MyX509TrustManager(String myTrustedCert, String trustPass,
			String myAlgorithm, String myProvider) throws Exception {
		trustedCert = "";
		passphrase = "";
		algorithm = "";
		provider = "";
		init(myTrustedCert, trustPass, myAlgorithm, myProvider);
		appointTrustManager();
	}

	public void init(String myTrustedCert, String trustPass,
			String myAlgorithm, String myProvider) {
		trustedCert = myTrustedCert;
		passphrase = trustPass;
		algorithm = myAlgorithm;
		provider = myProvider;
	}

	public void appointTrustManager() throws Exception {
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(new FileInputStream(trustedCert), passphrase.toCharArray());
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(algorithm,
				provider);
		tmf.init(ks);
		TrustManager tms[] = tmf.getTrustManagers();
		for (int i = 0; i < tms.length; i++)
			if (tms[i] instanceof X509TrustManager) {
				userdefinedX509TrustManager = (X509TrustManager) tms[i];
				return;
			}

		throw new Exception("Couldn't initialize");
	}

	public static TrustManager[] getTrustManager(String mytrust,
			String trustpass, String myAlgorithm, String myProvider) {
		TrustManager tms[];
		try {
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(new FileInputStream(mytrust), trustpass.toCharArray());
			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance(myAlgorithm);
			tmf.init(ks);
			tms = tmf.getTrustManagers();

			return tms;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	public void checkClientTrusted(X509Certificate chain[], String authType)
			throws CertificateException {
		try {
			userdefinedX509TrustManager.checkClientTrusted(chain, authType);
		} catch (CertificateException certificateexception) {
		}
	}

	public void checkServerTrusted(X509Certificate chain[], String authType)
			throws CertificateException {
		try {
			userdefinedX509TrustManager.checkServerTrusted(chain, authType);
		} catch (CertificateException certificateexception) {
		}
	}

	public X509Certificate[] getAcceptedIssuers() {
		return userdefinedX509TrustManager.getAcceptedIssuers();
	}

	public String getTrustedCert() {
		return trustedCert;
	}

	public void setTrustedCert(String trustedCert) {
		this.trustedCert = trustedCert;
	}

	public String getPassphrase() {
		return passphrase;
	}

	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
}
