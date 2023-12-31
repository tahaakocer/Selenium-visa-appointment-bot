package example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeMessage;



public class MailSender {
	private static String username;
	private static String recipient;
	private static String password;
	public static String Text = "";
	private static Properties mailProperties;
	private static InputStream input = null;
	private static LocalDate date = LocalDate.now();
	
	
	public static void sendMail(String day) {
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port","587");
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		
		
		try {
			
			mailProperties = new Properties();
		//	input = ClassLoader.getSystemClassLoader().getResourceAsStream("mail.properties");		
			input = new FileInputStream("./config/mail.properties");
			mailProperties.load(input);
			recipient = mailProperties.getProperty("recipient");
			username = mailProperties.getProperty("username");
			password = mailProperties.getProperty("password");
			input.close();		
		} catch (IOException e) {
			e.printStackTrace();
		}
        Session session = Session.getInstance(properties,
                      new javax.mail.Authenticator() {
               protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                      return new javax.mail.PasswordAuthentication(username, password);
               }
        }); 
        
        try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(
					Message.RecipientType.TO,
					InternetAddress.parse(recipient)
			);
			message.setSubject("Vize Randevu Botu");
			Text = day + "-" + date.getMonth() + "-" + date.getYear() + " tarihine " +Selenium.numOfSpan + " adet vize randevusu açılmıştır.\n https://portal.russia-visacentre.com/#/appointment/personal-info/tur ";
			message.setText(Text);
			Transport.send(message);
			System.out.println("Email sent");
	
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
