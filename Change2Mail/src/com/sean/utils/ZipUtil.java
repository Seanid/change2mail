package com.sean.utils;

import java.io.File;
import java.util.ArrayList;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipUtil {
	/**
	 * ѹ���ļ��б���Ŀ¼�ṹ
	 * @author Sean
	 * 2015-6-6
	 * @param zipSrc zip�ļ���ַ
	 * @param list	�ļ��б�
	 * @return		���ɹ��������ļ���ַ�����ɹ�����null
	 */
	public static String zipFiles(String zipSrc,ArrayList<File> list){
		ZipParameters zipParameters=new ZipParameters();
		zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_MAXIMUM);
		ZipFile zipFile=null;
		try {
			zipFile=new ZipFile(zipSrc);
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		try {
			zipFile.addFiles(list, zipParameters);
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return zipSrc;
	}
	
	/**
	 * ѹ���ļ���
	 * @author Sean
	 * 2015-6-6
	 * @param src	�ļ��е�ַ
	 * @param zipSrc ѹ���ļ���ŵ�ַ
	 * @return		���ɹ��������ļ��е�ַ�������ɹ�������null
	 */
	public static String zipFolder(String src,String zipSrc){
		ZipParameters zipParameters=new ZipParameters();
		zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_MAXIMUM);
		ZipFile zipFile=null;
		try {
			zipFile=new ZipFile(zipSrc);
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		try {
			zipFile.addFolder(src, zipParameters);
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return zipSrc;
	}
	
	/**
	 * ��zip�ļ�������ļ��б�
	 * @author Sean
	 * 2015-6-7
	 * @param zipSrc	zip�ļ�·��
	 * @param list		Ҫ��ӵ��ļ��б�
	 * @param src		Ҫ��ӵ�zip�ļ��е��ĸ�·��
	 */
	public static void addFileToZip(String zipSrc,ArrayList<File> list,String src){
		ZipFile zipFile=null;
		try {
			zipFile=new ZipFile(zipSrc);
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ZipParameters zipParameters=new ZipParameters();
		zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		zipParameters.setRootFolderInZip(src);
		try {
			zipFile.addFiles(list, zipParameters);
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ����ļ��б�zip��Ŀ¼��
	 * @author Sean
	 * 2015-6-7
	 * @param zipSrc	zip�ļ���ַ
	 * @param list		��ӵ��ļ��б�
	 */
	public static void addFileToZip(String zipSrc,ArrayList<File> list){
		addFileToZip(zipSrc,list,"");
	}
	
}
