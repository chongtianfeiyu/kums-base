package com.kurui.kums.base.mail;

import java.io.File;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.kurui.kums.base.util.RegularUtil;

public class MailSender {

	private JavaMailSenderImpl jmailSender;
	private String to;
	private String from;
	private String subject;
	private String body;

	public MailSender() {
		to = "";
		from = "";
		subject = "";
		body = "";
	}

	public void setJmailSender(JavaMailSenderImpl jmailSender) {
		this.jmailSender = jmailSender;
	}

	public int send() {
		try {
			javax.mail.internet.MimeMessage mailMessage = jmailSender
					.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true,
					"UTF-8");
			if (to.equals("") || !RegularUtil.isEMail(to))
				return 0;

			helper.setTo(to);
			helper.setFrom(from);
			helper.setSubject(subject);
			helper.setText(body, true);
			File file = null;
			if (file != null) {
				FileSystemResource fileSource = new FileSystemResource(
						file.getPath());
				helper.addAttachment(file.getName(), file);
			}
			jmailSender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public static void main(String args1[]) {
	}
}
