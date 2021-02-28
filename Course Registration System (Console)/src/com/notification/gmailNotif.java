package com.notification;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;


public class gmailNotif extends mailNotif{

	/**
	 * Instantiates a new gmail notif.
	 * @param accountEmail the source account email
	 * @param password the password of the source account email
	 */
	public gmailNotif(String accountEmail, String password) {
		super(accountEmail, password);
	}

	/**
	 * Send notif (Transport message created to the email address of the user).
	 *
	 * @param recipient the recipient's email
	 * @param stringTitle the email title
	 * @param stringMessage the email body
	 * @throws Exception an exception is thrown if there is an error with the sending of notification
	 */
	public void sendNotif(String recipient, String stringTitle, String stringMessage) throws Exception{
		Properties props = new Properties();

		//auth set to true for gmail as require login
		props.put("mail.smtp.auth", "true");
		//ttls encryption set to true for gmail
		props.put("mail.smtp.starttls.enable", "true");
		//smtp host is gmail
		props.put("mail.smtp.host", "smtp.gmail.com");
		//port for gmail is 587
		props.put("mail.smtp.port", "587");

		//Login to notification email
		Session session = Session.getInstance(props,
		  new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getAccountEmail(), getPassword());
			}
		  });

		System.out.println("Sending mail now...");
		Message message = this.createMessage(session, recipient, stringTitle, stringMessage);
		Transport.send(message);
		System.out.println("Message sent!");

	}
}
