package com.sean.main;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.sean.bean.EmailBean;
import com.sean.bean.FilesBean;
import com.sean.bean.PropertiesBean;
import com.sean.utils.ZipUtil;

/**
 * change2Mail ������
 * ����ļ��޸���������͸�ָ������
 * @version 1.0
 * @author sean
 * 2015-06-09
 *
 */
public class Change2Mail {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException{
		//��ȡproperties������
		PropertiesBean propertiesBean = new PropertiesBean("cfg.properties");
		FilesBean filesBean=new FilesBean();
		//�ʼ�����
		EmailBean email=new EmailBean("cfg.properties");
		String first = propertiesBean.getValue("first");
		//��ǰ����
		String today=new SimpleDateFormat("yyyyMMdd").format(new Date());
		//�ʼ�����
		String content="";
		//�״�ʹ���ж�
		if (first.trim().equals("true")) {
			System.out.println("�״�����");
			content+="��ӭ�״�ʹ��change2Mail��<br/>��������ĸ����ļ�����Ŀ¼<br/>";
			//��ȡ����ļ�Ŀ¼
			String cheakDir = propertiesBean.getValue("cheakDir");
			if(!StringUtils.isEmpty(cheakDir)){
				System.out.println("���ڽ��и����ļ�Ŀ¼���ݡ�����������");
				//Ŀ¼�ָ�
				String[] dirs = cheakDir.split(";");
				for (int i = 0; i < dirs.length; i++) {
					File tmp=new File(dirs[i]);
					List<File> list=filesBean.listFiles(dirs[i],true);
					//������Ӧ�ļ��洢Ŀ¼�б�
					File file=new File(ClassLoader.getSystemResource("").getPath()+tmp.getName());
					if(!file.exists()){
						try {
							file.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println("����Ŀ¼�б��¼�ļ�ʧ�ܡ���������");
							return ;
						}
						}
					//���б�д���ļ�
					logFiles(file,list);
					//ѹ���ļ��������ʼ�
					try {
						ZipUtil.zipFolder(dirs[i], "tmp\\"+tmp.getName()+today+".zip");
						content+=tmp.getName()+"<br/>";
						email.addFiles("tmp\\"+tmp.getName()+today+".zip");
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("ѹ������");
						return ;
					}
					
				}
			}
			content+="��������Ĺ̶�����Ŀ¼<br/>";
			String zipDir = propertiesBean.getValue("zipDir");
			if(!StringUtils.isEmpty(zipDir)){
				System.out.println("���ڽ��й̶�����Ŀ¼���ݡ�����������");
				String[] zipDirs = zipDir.split(";");
				for (int i = 0; i < zipDirs.length; i++) {
					try {
						File tmp=new File(zipDirs[i]);
						ZipUtil.zipFolder(zipDirs[i], "tmp\\"+tmp.getName()+today+"bak.zip");
						content+=tmp.getName()+"<br/>";
						email.addFiles("tmp\\"+tmp.getName()+today+"bak.zip");
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("ѹ������");
						return ;
					}
				}
			}
			System.out.println("���������ļ�����������");
			propertiesBean.setValue("first", "false");
			propertiesBean.setValue("lastData", today);
			System.out.println("�����ļ�����������");
			email.setFrom(propertiesBean.getValue("username"));
			email.setTOSend(propertiesBean.getValue("toSend"));
			email.setContent(content);
			email.setSubject("change2Mail�����ļ�"+today);
			if(email.send()){
				System.out.println("���ͳɹ�");
			}else{
				System.out.println("����ʧ��");
			}
			
		} else {
				System.out.println("���ݿ�ʼ����������");
				String data=propertiesBean.getValue("lastData");
				String filter=propertiesBean.getValue("filter");
				String cheakDir = propertiesBean.getValue("cheakDir");
				content+="��ã���"+data+"����ļ��䶯���£�<br/>";
				if(!StringUtils.isEmpty(cheakDir)){
					System.out.println("���ڽ��и����ļ�Ŀ¼���ݡ�����������");
					String[] dirs = cheakDir.split(";");
					for (int i = 0; i < dirs.length; i++) {
						File tmp=new File(dirs[i]);
						File file=new File(ClassLoader.getSystemResource("").getPath()+tmp.getName());
						if(!file.exists()){
							try {
								file.createNewFile();
							} catch (IOException e) {
								System.out.println("�����ĵ�ʧ��");
								e.printStackTrace();
							}
						}
						content+="�ļ���"+tmp.getName()+"��<br/>";
						List<File> list=filesBean.getModifiedFiles(dirs[i],new SimpleDateFormat("yyyyMMdd").parse(data),filter,true);
						List<File> now =filesBean.listFiles(dirs[i],filter,true);
						List<File> news=new ArrayList<File>();
						List<File> del=new ArrayList<File>();
						List<File> modefy=new ArrayList<File>();
						List<String> lines=null;
						try {
							lines=FileUtils.readLines(file);
						} catch (IOException e) {
							e.printStackTrace();
						}
						System.out.println("����½����޸ĵ��ļ�������������");
						for(File f:list){
							if(!lines.contains(f.getAbsolutePath())){
								news.add(f);
							}else{
								modefy.add(f);
							}
						}
						
						System.out.println("���ɾ�����ļ�������������");
						for(String f:lines){
							File old=new File(f);
							if(!now.contains(old)){
								del.add(old);
							}
						}
						logFiles(file,now);
						content+="ɾ���ļ���<br/>";
						for(File f:del){
							content+="&#9;"+f.getName()+"<br/>";
						}
						content+="�����ļ���<br/>";
						for(File f:news){
							content+="&#9;"+f.getName()+"<br/>";
						}
						content+="�޸��ļ���<br/>";
						for(File f:modefy){
							content+="&#9;"+f.getName()+"<br/>";
						}
						try {
							if(list.size()!=0){
								list.add(file);
								ZipUtil.zipFiles("tmp\\"+tmp.getName()+today+".zip", (ArrayList<File>)list);
								
								email.addFiles("tmp\\"+tmp.getName()+today+".zip");
							}
							
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("ѹ��ʧ��");
							return;
						}
						
						
					}
					
					
					String zipDir = propertiesBean.getValue("zipDir");
					if(!StringUtils.isEmpty(zipDir)){
						System.out.println("���ڽ��й̶�����Ŀ¼���ݡ�����������");
						String[] zipDirs = zipDir.split(";");
						for (int i = 0; i < zipDirs.length; i++) {
							try {
								File tmp=new File(zipDirs[i]);
								ZipUtil.zipFolder(zipDirs[i], "tmp\\"+tmp.getName()+today+"bak.zip");
								email.addFiles("tmp\\"+tmp.getName()+today+"bak.zip");
							} catch (Exception e) {
								e.printStackTrace();
								System.out.println("ѹ������");
								return ;
							}
						}
					}
					
					
					System.out.println("���������ļ�����������");
					propertiesBean.setValue("lastData", today);
					System.out.println("�����ļ�����������");
					email.setFrom(propertiesBean.getValue("username"));
					email.setTOSend(propertiesBean.getValue("toSend"));
					email.setContent(content);
					email.setSubject("change2Mail�����ļ�"+today);
					if(email.send()){
						System.out.println("���ͳɹ�");
					}else{
						System.out.println("����ʧ��");
					}
					
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
					 long to = df.parse(today).getTime();
					 long todel=Integer.parseInt(propertiesBean.getValue("time"))*(1000 * 60 * 60 * 24);
					 Date lastBak=new Date(to-todel) ;
					 System.out.println("ɾ��"+df.format(lastBak)+"֮ǰ�������ʷ�ļ�����������");
					 List<File> fileToDel=filesBean.getOlderFiles("tmp\\", lastBak,true);
					 for(File f:fileToDel){
						 try {
							 FileUtils.deleteQuietly(f);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							System.out.println("ɾ��"+f.getName()+"�ļ�ʧ��");
						}
						
					 }
					System.out.println(new Date());
				}
		}
	}
	/**
	 * ���ļ��б�ľ��Ե�ַ�����һ���ļ���
	 * @param file	����ļ�
	 * @param list	�ļ��б�
	 * @return		�ɹ�����true��ʧ�ܷ���false
	 */
	public static boolean logFiles(File file, List<File> list) {
		List<String> lines=new ArrayList<String>();
		try {
				for (File f : list) {
					lines.add(f.getAbsolutePath());
				}
				FileUtils.writeLines(file, lines,false);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
	}

}
