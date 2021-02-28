package com.notificationFactory;
import com.notification.*;

import java.io.*;
import java.util.*;

import java.util.ArrayList;

public class NotificationFactory {

    /** The notifListener class that NotificationFactory will return*/
    private static notifListener mail;

    /**
     * This function creates the concrete listener object and sends the notification to the recipient
     *
     * @param type the type of notification to be sent to the user
     * @param recipient the recipient
     * @param messageFile the message file
     * @param courseCode the course code
     * @throws Exception an exception is thrown if there is an error with the sending of notification
     */
    public static void sendMessage(String type, String recipient, String messageFile, String courseCode) throws Exception {
        if (type.equals("mail")){
            List<String> data = read("./src/com/notification/mailDetails.txt");
            String mailType = data.get(0);
            if(mailType.equals("gmail")) {
                mail = new gmailNotif(data.get(1), data.get(2));
            }
            List<String> message = read(messageFile);
            String title = message.get(0).replaceAll("<<COURSE>>", courseCode);
            String body = message.get(1).replaceAll("<<COURSE>>", courseCode);
            mail.sendNotif(recipient, title, body);
        }
    }

    /**
     * Read the contents of the .txt file
     *
     * @param filename the .txt file containing the content
     * @return a ArrayList of the String sentences of the .txt file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static List<String> read(String filename) throws IOException {
        List<String> data = new ArrayList<>();
        Scanner scanner = new Scanner(new FileInputStream(filename));
        try {
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        } finally {
            scanner.close();
        }
        return data;
    }
//    public static void main(String[] args) {
//        File file = new File("./src/com/notification/");
//        for(String fileNames : file.list()) System.out.println(fileNames);
//        try {
//            NotificationFactory.sendMessage("mail", "soox0001@e.ntu.edu.sg", "./src/com/notification/outOfWaitlist.txt", "CZ1001");
//        }
//        catch(Exception e){
//            System.out.println("Exception:" + e);
//        }
//    }
}




