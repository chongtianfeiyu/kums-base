package com.kurui.kums.base.ssl;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class CARead extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String CA_Name;
	private String CA_ItemData[][];
	private String columnNames[] = { "证书字段标记", "内容" };

	public CARead(String CertName) {
		CA_ItemData = new String[9][2];
		CA_Name = CertName;
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel panelNormal = new JPanel();
		tabbedPane.addTab("普通信息", panelNormal);
		JPanel panelAll = new JPanel();
		panelAll.setLayout(new BorderLayout());
		tabbedPane.addTab("所有信息", panelAll);
		JPanel panelBase64 = new JPanel();
		panelBase64.setLayout(new BorderLayout());
		tabbedPane.addTab("Base64编码信息", panelBase64);
		Read_Normal(panelNormal);
		Read_Bin(panelAll);
		Read_Raw(panelBase64);
		tabbedPane.setSelectedIndex(0);
		setLayout(new GridLayout(1, 1));
		add(tabbedPane);
	}

	private int Read_Normal(JPanel panel) {
		try {
			CertificateFactory certificate_factory = CertificateFactory
					.getInstance("X.509");
			FileInputStream file_inputstream = new FileInputStream(CA_Name);
			X509Certificate x509certificate = (X509Certificate) certificate_factory
					.generateCertificate(file_inputstream);
			String Field = x509certificate.getType();
			CA_ItemData[0][0] = "类型";
			CA_ItemData[0][1] = Field;
			Field = Integer.toString(x509certificate.getVersion());
			CA_ItemData[1][0] = "版本";
			CA_ItemData[1][1] = Field;
			Field = x509certificate.getSubjectDN().getName();
			CA_ItemData[2][0] = "标题";
			CA_ItemData[2][1] = Field;
			file_inputstream.close();
			JTable table = new JTable(CA_ItemData, columnNames);
			TableColumn tc = null;
			tc = table.getColumnModel().getColumn(1);
			tc.setPreferredWidth(600);
			panel.add(table);
		} catch (Exception exception) {
			exception.printStackTrace();
			return -1;
		}
		return 0;
	}

	private int Read_Bin(JPanel panel) {
		try {
			FileInputStream file_inputstream = new FileInputStream(CA_Name);
			DataInputStream data_inputstream = new DataInputStream(
					file_inputstream);
			CertificateFactory certificatefactory = CertificateFactory
					.getInstance("X.509");
			byte bytes[] = new byte[data_inputstream.available()];
			data_inputstream.readFully(bytes);
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			JEditorPane Cert_EditorPane = new JEditorPane();
			X509Certificate Cert;
			for (; bais.available() > 0; Cert_EditorPane
					.setText((new StringBuilder(String.valueOf(Cert_EditorPane
							.getText()))).append(Cert.toString()).toString()))
				Cert = (X509Certificate) certificatefactory
						.generateCertificate(bais);

			Cert_EditorPane.disable();
			JScrollPane edit_scroll = new JScrollPane(Cert_EditorPane);
			panel.add(edit_scroll);
			file_inputstream.close();
			data_inputstream.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			return -1;
		}
		return 0;
	}

	private int Read_Raw(JPanel panel) {
		try {
			JEditorPane Cert_EditorPane = new JEditorPane();
			String CertText = null;
			File inputFile = new File(CA_Name);
			FileReader in = new FileReader(inputFile);
			char buf[] = new char[2000];
			int len = in.read(buf, 0, 2000);
			for (int i = 1; i < len; i++)
				CertText = (new StringBuilder(String.valueOf(CertText)))
						.append(buf[i]).toString();

			in.close();
			Cert_EditorPane.setText(CertText);
			Cert_EditorPane.disable();
			JScrollPane edit_scroll = new JScrollPane(Cert_EditorPane);
			panel.add(edit_scroll);
		} catch (Exception exception) {
			exception.printStackTrace();
			return -1;
		}
		return 0;
	}

	public static void readP12() {
		try {
			String home = System.getProperty("user.home");
			FileInputStream file_inputstream = new FileInputStream(
					"c:\\hl_527@yeah.net.p12");
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(file_inputstream, null);
			System.out.println((new StringBuilder("Keystore size ")).append(
					keyStore.size()).toString());
			for (Enumeration aliases = keyStore.aliases(); aliases
					.hasMoreElements(); System.out.println(aliases
					.nextElement()))
				;
			Key key = keyStore.getKey("hl_527@yeah.net", "password"
					.toCharArray());
			System.out.println((new StringBuilder("Key information ")).append(
					key.getAlgorithm()).append(" ").append(key.getFormat())
					.toString());
			java.security.cert.Certificate certChain[] = keyStore
					.getCertificateChain("hl_527@yeah.net");
			System.out.println(certChain.length);
		} catch (Exception e) {
			System.err.println((new StringBuilder("Exception:- ")).append(e)
					.toString());
		}
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("CARead");
		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});
		frame.getContentPane().add(new CARead("c:\\Ling9514@126.com.p12"),
				"Center");
		frame.setSize(700, 425);
		frame.setVisible(true);
	}
}
