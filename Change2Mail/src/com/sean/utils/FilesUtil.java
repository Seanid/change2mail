package com.sean.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
/**
 * 文件操作工具类
 * 实现功能：1、获取文件文件路劲列表
 * @author Sean
 * @blog http://www.cnblogs.com/Seanit/
 * @email hxy9104@126.com
 * 2015-6-6
 */
public class FilesUtil {
	/**
	 * 获取文件夹目录下的列表,目录为空返回空列表
	 * @param dir 传入对应文件夹目录
	 * @return	返回list<File>文件列表
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
	 * 获取指定目录下，指定日期修改后的文件
	 * @param dir	传入指定检测目录
	 * @param date	传入指定修改日期
	 * @return		返回获取的文件列表，若目录或日期为空，则返回null
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
	 * 获取某一日期之前创建的文件
	 * @author Sean
	 * 2015-6-9
	 * @param dir	文件路径
	 * @param date	日期
	 * @return		返回列表
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
