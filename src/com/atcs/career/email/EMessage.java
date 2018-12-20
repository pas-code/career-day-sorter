
//Thomas Varano
//Dec 20, 2018

package com.atcs.career.email;

import java.time.LocalDateTime;

public class EMessage {
	public static final byte TO = 0, CC = 1, BCC = 2; 
	private static final String CONTENT_KEY = "0000000000007c6c5e057ce7ba11";
	
	private String subject, htmlBody, plainBody, from;
	
	// parallel array between recpients and their privacies
	private String[] recipients;
	private byte[] recipientPrivacies;
	
	
	public EMessage() {
		
	}

	public String getFormatData() {
		String totalData = 
				"MIME-Version: 1.0\n";
		LocalDateTime now = LocalDateTime.now();
		
		String dayOfWeek = lowercaseFirstThree(now.getDayOfWeek().name());
		// give date
		totalData += "Date: "+dayOfWeek + ", " + now.getDayOfMonth() + " " + lowercaseFirstThree(now.getMonth().name());
		totalData += " " + now.toLocalTime().toString().substring(0, now.toLocalTime().toString().lastIndexOf("."));
		totalData += " -5000\n";
		// give message id, it is set later in email jar
		totalData += "Message-ID: <CAG1ezLcvYPAtKN99YgA0M4+jqg6-2yDKOCk4Whw7F5j42tdJQQ@mail.gmail.com>\n";
		// subject
		totalData += "Subject: "+safeguardNull(subject) + "\n";
		
		// to and from set from email jar.
		// TODO careful, sending is weird now.
		totalData += "From: " + from + "\n" + 
				"To: placeholder@gmail.com";
		// content keys
		totalData += "Content-Type: multipart/alternative; boundary=\"" + CONTENT_KEY + "\"\n";
		totalData += "\n\n--" + CONTENT_KEY;
		
		// give plain message for non-html formatting
		totalData += "Content-Type: text/plain; charset=\"UTF-8\"";
		totalData += "\n\n";
		totalData += plainBody;
		totalData += "\n\n--" + CONTENT_KEY;
		
		// give html body
		totalData += "Content-Type: text/html; charset=\"UTF-8\"\n" + 
				"Content-Transfer-Encoding: quoted-printable\n\n";
		
		totalData += htmlBody;
		
		totalData += "\n\n--" + CONTENT_KEY + "--";
		return totalData;
	}
	
	private static String lowercaseFirstThree(String in) {
		return in.charAt(0) + in.substring(1, 3).toLowerCase();
	}
	
	private static String safeguardNull(String in) {
		return in == null ? "" : in;
	}

	public static void main(String[] args) {
		System.out.println(new EMessage().getFormatData());
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String[] getRecipients() {
		return recipients;
	}

	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}

	public byte[] getRecipientPrivacies() {
		return recipientPrivacies;
	}

	public void setRecipientPrivacies(byte[] recipientPrivacies) {
		this.recipientPrivacies = recipientPrivacies;
	}
}
