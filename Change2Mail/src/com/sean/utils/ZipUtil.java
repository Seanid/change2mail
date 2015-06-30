package com.sean.utils;

import java.io.File;
import java.util.ArrayList;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipUtil {
	/**
	 * 压缩文件列表，无目录结构
	 * @author Sean
	 * 2015-6-6
	 * @param zipSrc zip文件地址
	 * @param list	文件列表
	 * @return		若成功，返回文件地址，不成功返回null
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
	 * 压缩文件夹
	 * @author Sean
	 * 2015-6-6
	 * @param src	文件夹地址
	 * @param zipSrc 压缩文件存放地址
	 * @return		若成功，返回文件夹地址，若不成功，返回null
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
	 * 往zip文件中添加文件列表
	 * @author Sean
	 * 2015-6-7
	 * @param zipSrc	zip文件路径
	 * @param list		要添加的文件列表
	 * @param src		要添加到zip文件中的哪个路径
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
	 * 添加文件列表到zip根目录下
	 * @author Sean
	 * 2015-6-7
	 * @param zipSrc	zip文件地址
	 * @param list		添加的文件列表
	 */
	public static void addFileToZip(String zipSrc,ArrayList<File> list){
		addFileToZip(zipSrc,list,"");
	}
	
}
