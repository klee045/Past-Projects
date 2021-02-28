package com.notification;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public abstract class mailNotif implements notifListener{

	/** The account email. Stored as "xxxxx@xxxx.com"*/
	private String accountEmail;
	/** The password. */
	private String password;

	/**
	 * Instantiates a new mail notif.
	 *
	 * @param accountEmail the email address of source email
	 * @param password the password of source email
	 */
	public mailNotif(String accountEmail, String password){
		//Email for notification system(sender)
		this.accountEmail = accountEmail;
		//Password for notification system(sender)
		this.password = password;
	}

	/**
	 * Gets accountEmail
	 * @return accountEmail
	 */
	protected String getAccountEmail() {
		return accountEmail;
	}

	/**
	 * Gets password
	 * @return password
	 */
	protected String getPassword() {
		return password;
	}

	/**
	 * Creates an email message to be sent(with title and body text)
	 * @param session the session of the email
	 * @param recipient the recipient's email
	 * @param stringTitle the title of the email
	 * @param stringMessage the email message body
	 * @return Message message
	 */
	public Message createMessage(Session session, String recipient, String stringTitle, String stringMessage) {
		try {
			//Create new message using session created
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(accountEmail));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(recipient)); // to be added an email addr
			message.setSubject(stringTitle);
			message.setText(stringMessage);
			return message;


		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
