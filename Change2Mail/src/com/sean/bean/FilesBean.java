package com.sean.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
/**
 * �ļ�����������
 * @author Sean
 * @blog http://www.cnblogs.com/Seanit/
 * @email hxy9104@126.com
 * 2015-6-6
 */
public class FilesBean {
	private List<File> list=null;
	public FilesBean(){}
	
	
	/**
	 * ��ȡ�ļ���Ŀ¼�µ��б�,Ŀ¼Ϊ�շ��ؿ��б�
	 * @param dir �����Ӧ�ļ���Ŀ¼
	 * @param ifFirst ���Ƶݹ�ʱ���ѭ����ʾ
	 * @return	����list<File>�ļ��б�
	 */
	public  List<File> listFiles(String dir,boolean ifFirst){
		/**
		 * �ݹ��жϣ����ڵ�һ�ε��ú�����ʱ���½��б�Ϊ��ֹ�ظ������б�
		 */
		if(ifFirst){
			list=new ArrayList<File>();
		}
		
		if (dir==null||dir=="") {
			return null;
		}
		for (File f : FileUtils.getFile(dir + "\\").listFiles()) {
			
			if (f.isDirectory()) {
				listFiles(f.getAbsolutePath(),false);
			} else {
				list.add(f);
			}
		}
		return list;
	}
	
	/**
	 * ���ݹ���������ȡ�б�
	 * @param dir	Ŀ¼
	 * @param filter	��������
	 * @param ifFirst	�ݹ��ʶ
	 * @return			�����ļ��б�
	 */
	public  List<File> listFiles(String dir,String filter,boolean ifFirst){
		/**
		 * �ݹ��жϣ����ڵ�һ�ε��ú�����ʱ���½��б�Ϊ��ֹ�ظ������б�
		 */
		if(ifFirst){
			list=new ArrayList<File>();
		}
		
		if (dir==null||dir=="") {
			return null;
		}
		for (File f : FileUtils.getFile(dir + "\\").listFiles()) {
			
			if (f.isDirectory()) {
				listFiles(f.getAbsolutePath(),filter,false);
			} else {
				if(!f.getName().endsWith(filter)){
					list.add(f);
				}
				
			}
		}
		return list;
	}
	
	/**
	 * ��ȡָ��Ŀ¼�£�ָ�������޸ĺ���ļ�
	 * @param dir	����ָ�����Ŀ¼
	 * @param date	����ָ���޸�����
 	 * @param ifFirst ���Ƶݹ�ʱ���ѭ����ʾ
	 * @return		���ػ�ȡ���ļ��б���Ŀ¼������Ϊ�գ��򷵻�null
	 */
	public  List<File> getModifiedFiles(String dir,Date date,boolean ifFirst){
		/**
		 * �ݹ��жϣ����ڵ�һ�ε��ú�����ʱ���½��б�Ϊ��ֹ�ظ������б�
		 */
		if(ifFirst){
			list=new ArrayList<File>();
		}
		
		if (dir==null||dir==""||date==null) {
			return null;
		}

		for (File f : FileUtils.getFile(dir + "\\").listFiles()) {
			if (f.isDirectory()) {
				getModifiedFiles(f.getAbsolutePath(),date,false);
			} else {
				if (FileUtils.isFileNewer(f, date.getTime())) {
					list.add(f);
				}
			}
		}
		return list;
	}
	
	
	/**
	 * ��ȡĳһ����֮ǰ�������ļ�
	 * @author Sean
	 * 2015-6-9
	 * @param dir	�ļ�·��	 
	 * @param ifFirst ���Ƶݹ�ʱ���ѭ����ʾ
	 * @param date	����
	 * @return		�����б�
	 */
	public  List<File> getOlderFiles(String dir,Date date,boolean ifFirst){
		/**
		 * �ݹ��жϣ����ڵ�һ�ε��ú�����ʱ���½��б�Ϊ��ֹ�ظ������б�
		 */
		if(ifFirst){
			list=new ArrayList<File>();
		}
		
		if (dir==null||dir==""||date==null) {
			return null;
		}

		for (File f : FileUtils.getFile(dir + "\\").listFiles()) {
			if (f.isDirectory()) {
				getOlderFiles(f.getAbsolutePath(),date,false);
			} else {
				if (FileUtils.isFileOlder(f, date)) {
					list.add(f);
				}
			}
		}
		return list;
	}
	
	/**
	 * ��ȡ��������������޸��ļ��б�
	 * @param dir
	 * @param date
	 * @param filter
	 * @param ifFirst
	 * @return
	 */
	public  List<File> getModifiedFiles(String dir,Date date,String filter,boolean ifFirst){
		/**
		 * �ݹ��жϣ����ڵ�һ�ε��ú�����ʱ���½��б�Ϊ��ֹ�ظ������б�
		 */
		if(ifFirst){
			list=new ArrayList<File>();
		}
		
		if (dir==null||dir==""||date==null) {
			return null;
		}

		for (File f : FileUtils.getFile(dir + "\\").listFiles()) {
			if (f.isDirectory()) {
				getModifiedFiles(f.getAbsolutePath(),date,filter,false);
			} else {
				if (FileUtils.isFileNewer(f, date.getTime())) {
					if(!f.getName().endsWith(filter)){
						list.add(f);
					}
					
				}
			}
		}
		return list;
	}
	
	
}
