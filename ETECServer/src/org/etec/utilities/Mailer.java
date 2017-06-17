package org.etec.utilities;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.etec.management.Package;

public class Mailer {
	
	private static final String SENDER = "some@gmail.com";
	private static final String PASSWORD = "*******";
	private static final String HOST = "smtp.gmail.com";
	private static final String PORT = "587";
	
	/**
	 * Envia la información del paquete al usuario
	 * @param receiver el correo destino.
	 * @param name el nombre del paciente.
	 */
	public static void send_arrived_msg(String receiver, Package p){
		
		final String SUBJECT = "TU PAQUETE HA LLEGADO";
		final String MESSAGE = "Tu paquete " + p.toString() + "\n Ha llegado al centro de distribución " + p.messenger().destination().name();
		
		Properties properties = new Properties();
		properties.put("mail.smtp.port", PORT);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		
		Session session = Session.getDefaultInstance(properties);
		Message msg = new MimeMessage(session);
		
		try {
			msg.setFrom(new InternetAddress(SENDER));
			InternetAddress address = new InternetAddress(receiver);
			msg.setRecipient(Message.RecipientType.TO, address);
			msg.setSubject(SUBJECT);
			
			MimeBodyPart msg_part = new MimeBodyPart();
			msg_part.setText(MESSAGE);
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(msg_part);

			msg.setContent(multipart);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		try {
			Transport transport = session.getTransport("smtp");
			
			transport.connect(HOST, SENDER, PASSWORD);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Envia la información del paquete al usuario
	 * @param receiver el correo destino.
	 * @param name el nombre del paciente.
	 */
	public static void send_purchase_msg(String receiver, Package p){
		
		final String SUBJECT = "Informacion de la compra";
		final String MESSAGE = "La compra se ha realizado exitosamente." + "\n El tiempo estimado de entrega es: " + p.date();
		
		Properties properties = new Properties();
		properties.put("mail.smtp.port", PORT);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		
		Session session = Session.getDefaultInstance(properties);
		Message msg = new MimeMessage(session);
		
		try {
			msg.setFrom(new InternetAddress(SENDER));
			InternetAddress address = new InternetAddress(receiver);
			msg.setRecipient(Message.RecipientType.TO, address);
			msg.setSubject(SUBJECT);
			
			MimeBodyPart msg_part = new MimeBodyPart();
			msg_part.setText(MESSAGE);
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(msg_part);

			msg.setContent(multipart);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		try {
			Transport transport = session.getTransport("smtp");
			
			transport.connect(HOST, SENDER, PASSWORD);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}	
}
