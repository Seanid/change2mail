package com.sean.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
/**
 * �ļ�����������
 * ʵ�ֹ��ܣ�1����ȡ�ļ��ļ�·���б�
 * @author Sean
 * @blog http://www.cnblogs.com/Seanit/
 * @email hxy9104@126.com
 * 2015-6-6
 */
public class FilesUtil {
	/**
	 * ��ȡ�ļ���Ŀ¼�µ��б�,Ŀ¼Ϊ�շ��ؿ��б�
	 * @param dir �����Ӧ�ļ���Ŀ¼
	 * @return	����list<File>�ļ��б�
	 */
	public static List<File> listFiles(String dir){
		List<File> list=new ArrayList<File>();
		
		if (dir==null||dir=="") {
			return null;
		}
		for (File f : FileUtils.getFile(dir + "\\").listFiles()) {
			
			if (f.isDirectory()) {
				listFiles(f.getAbsolutePath());
			} else {
				list.add(f);
			}
		}
		return list;
	}
	
	/**
	 * ��ȡָ��Ŀ¼�£�ָ�������޸ĺ���ļ�
	 * @param dir	����ָ�����Ŀ¼
	 * @param date	����ָ���޸�����
	 * @return		���ػ�ȡ���ļ��б���Ŀ¼������Ϊ�գ��򷵻�null
	 */
	public static List<File> getModifiedFiles(String dir,Date date){
		if (dir==null||dir==""||date==null) {
			return null;
		}
		List<File> list=new ArrayList<File>();

		for (File f : FileUtils.getFile(dir + "\\").listFiles()) {
			if (f.isDirectory()) {
				getModifiedFiles(f.getAbsolutePath(),date);
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
	 * @param date	����
	 * @return		�����б�
	 */
	public static List<File> getOlderFiles(String dir,Date date){
		if (dir==null||dir==""||date==null) {
			return null;
		}
		List<File> list=new ArrayList<File>();

		for (File f : FileUtils.getFile(dir + "\\").listFiles()) {
			if (f.isDirectory()) {
				getOlderFiles(f.getAbsolutePath(),date);
			} else {
				if (FileUtils.isFileOlder(f, date)) {
					list.add(f);
				}
			}
		}
		return list;
	}
	
	
	public static List<File> getModifiedFiles(String dir,Date date,String filter){
		if (dir==null||dir==""||date==null) {
			return null;
		}
		List<File> list=new ArrayList<File>();

		for (File f : FileUtils.getFile(dir + "\\").listFiles()) {
			if (f.isDirectory()) {
				getModifiedFiles(f.getAbsolutePath(),date);
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
