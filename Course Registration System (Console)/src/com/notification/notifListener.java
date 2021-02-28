package com.notification;

public interface notifListener {

	/**
	 * Send notification to the user
	 *
	 * @param recipient the recipient's email
	 * @param stringTitle the notification message title
	 * @param stringMessage the notification message
	 * @throws Exception the exception
	 */
	public void sendNotif(String recipient, String stringTitle, String stringMessage) throws Exception;
}

