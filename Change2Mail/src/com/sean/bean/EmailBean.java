package com.sean.bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 * �ʼ���
 * @author Sean
 * @blog http://www.cnblogs.com/Seanit/
 * @email hxy9104@126.com
 * 2015-6-9
 */
public class EmailBean {
	private Session session;
	private MimeMessage message;
	private Properties properties;
	private MimeMultipart body;
	
	/**
	 * Ĭ�Ϲ��캯��
	 */
	public EmailBean(){
	}
	
	
	public EmailBean(Properties properties){
		this.properties=properties;
		createMail(properties);
	}
	
	public EmailBean(String src){
		this.properties=new Properties();
		try {
			properties.load(new InputStreamReader(ClassLoader.getSystemResourceAsStream(src),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createMail(properties);
	}
	
	/**
	 * ��������
	 * @author Sean
	 * 2015-6-8
	 * @param properties �����������
	 */
	public  void createMail(Properties properties) {
		this.properties=properties;
		try {
			session=Session.getDefaultInstance(properties,null);
			message=new MimeMessage(session);
			body=new MimeMultipart();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("�ʼ���ʼ��ʧ��");
		}
	}
	
	/**
	 * ���÷�����
	 * @author Sean
	 * 2015-6-8
	 * @param from	�����˵�ַ
	 * @return		�ɹ�����true��ʧ�ܷ���false
	 */
	public boolean setFrom(String from){
		try {
			message.setFrom(new InternetAddress(from));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	/**
	 * �����ռ���
	 * @author Sean
	 * 2015-6-8
	 * @param toSend	�ռ��������ַ
	 * @return			�ɹ�����true��ʧ�ܷ���false
	 */
	public boolean setTOSend(String toSend){
		try {
			message.setRecipients(RecipientType.TO, toSend);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * ���ó�����
	 * @author Sean
	 * 2015-6-8
	 * @param copyTo	�����˵�ַ
	 * @return			�ɹ�����true��ʧ�ܷ���false
	 */
	public boolean setCopyTO(String copyTo){
		try {
			message.setRecipients(RecipientType.CC, (Address[])InternetAddress.parse(copyTo));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	
	/**
	 * ��������
	 * @author Sean
	 * 2015-6-8
	 * @param subject	�ʼ�����
	 * @return			�ɹ�����true��ʧ�ܷ���false
	 */
	public boolean setSubject(String subject){
		try {
			message.setSubject(subject);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * �����ʼ�����
	 * @author Sean
	 * 2015-6-8
	 * @param content	�ʼ�����
	 * @return			�ɹ�����true��ʧ�ܷ���false
	 */
	public boolean setContent(String content){
		try {
			BodyPart bodyPart=new MimeBodyPart();
			bodyPart.setContent(""+content, "text/html;charset=GBK");
			body.addBodyPart(bodyPart);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * ��Ӹ���
	 * @author Sean
	 * 2015-6-8
	 * @param src	������ַ
	 * @return
	 */
	public boolean addFiles(String src){
		try {
			BodyPart bodyPart=new MimeBodyPart();
			FileDataSource file =new FileDataSource(src);
			bodyPart.setDataHandler(new DataHandler(file));
			bodyPart.setFileName(file.getName());
			body.addBodyPart(bodyPart);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * �����ʼ�
	 * @author Sean
	 * 2015-6-8
	 * @return
	 */
	public boolean send(){
		Transport transport=null;
		try {
			message.setContent(body);
			message.saveChanges();
			transport=session.getTransport();
			transport.connect(properties.getProperty("mail.smtp.host"), properties.getProperty("username"), properties.getProperty("password"));
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			if(message.getRecipients(Message.RecipientType.CC)!=null){
				transport.sendMessage(message, message.getRecipients(Message.RecipientType.CC));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				transport.close();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
