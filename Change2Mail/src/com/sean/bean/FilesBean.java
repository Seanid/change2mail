package com.sean.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
/**
 * 文件操作工具类
 * @author Sean
 * @blog http://www.cnblogs.com/Seanit/
 * @email hxy9104@126.com
 * 2015-6-6
 */
public class FilesBean {
	private List<File> list=null;
	public FilesBean(){}
	
	
	/**
	 * 获取文件夹目录下的列表,目录为空返回空列表
	 * @param dir 传入对应文件夹目录
	 * @param ifFirst 控制递归时候的循环表示
	 * @return	返回list<File>文件列表
	 */
	public  List<File> listFiles(String dir,boolean ifFirst){
		/**
		 * 递归判断，仅在第一次调用函数的时候新建列表，为防止重复存入列表
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
	 * 根据过滤条件获取列表
	 * @param dir	目录
	 * @param filter	过滤条件
	 * @param ifFirst	递归标识
	 * @return			返回文件列表
	 */
	public  List<File> listFiles(String dir,String filter,boolean ifFirst){
		/**
		 * 递归判断，仅在第一次调用函数的时候新建列表，为防止重复存入列表
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
	 * 获取指定目录下，指定日期修改后的文件
	 * @param dir	传入指定检测目录
	 * @param date	传入指定修改日期
 	 * @param ifFirst 控制递归时候的循环表示
	 * @return		返回获取的文件列表，若目录或日期为空，则返回null
	 */
	public  List<File> getModifiedFiles(String dir,Date date,boolean ifFirst){
		/**
		 * 递归判断，仅在第一次调用函数的时候新建列表，为防止重复存入列表
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
	 * 获取某一日期之前创建的文件
	 * @author Sean
	 * 2015-6-9
	 * @param dir	文件路径	 
	 * @param ifFirst 控制递归时候的循环表示
	 * @param date	日期
	 * @return		返回列表
	 */
	public  List<File> getOlderFiles(String dir,Date date,boolean ifFirst){
		/**
		 * 递归判断，仅在第一次调用函数的时候新建列表，为防止重复存入列表
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
	 * 获取除过滤条件外的修改文件列表
	 * @param dir
	 * @param date
	 * @param filter
	 * @param ifFirst
	 * @return
	 */
	public  List<File> getModifiedFiles(String dir,Date date,String filter,boolean ifFirst){
		/**
		 * 递归判断，仅在第一次调用函数的时候新建列表，为防止重复存入列表
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
