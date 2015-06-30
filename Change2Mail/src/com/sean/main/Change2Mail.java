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
 * change2Mail 主程序
 * 检测文件修改情况并发送给指定邮箱
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
		//获取properties操作类
		PropertiesBean propertiesBean = new PropertiesBean("cfg.properties");
		FilesBean filesBean=new FilesBean();
		//邮件对象
		EmailBean email=new EmailBean("cfg.properties");
		String first = propertiesBean.getValue("first");
		//当前日期
		String today=new SimpleDateFormat("yyyyMMdd").format(new Date());
		//邮件正文
		String content="";
		//首次使用判断
		if (first.trim().equals("true")) {
			System.out.println("首次运行");
			content+="欢迎首次使用change2Mail，<br/>以下是你的更改文件备份目录<br/>";
			//获取检查文件目录
			String cheakDir = propertiesBean.getValue("cheakDir");
			if(!StringUtils.isEmpty(cheakDir)){
				System.out.println("正在进行更改文件目录备份。。。。。。");
				//目录分割
				String[] dirs = cheakDir.split(";");
				for (int i = 0; i < dirs.length; i++) {
					File tmp=new File(dirs[i]);
					List<File> list=filesBean.listFiles(dirs[i],true);
					//创建对应文件存储目录列表
					File file=new File(ClassLoader.getSystemResource("").getPath()+tmp.getName());
					if(!file.exists()){
						try {
							file.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println("创建目录列表记录文件失败。。。。。");
							return ;
						}
						}
					//将列表写入文件
					logFiles(file,list);
					//压缩文件并加入邮件
					try {
						ZipUtil.zipFolder(dirs[i], "tmp\\"+tmp.getName()+today+".zip");
						content+=tmp.getName()+"<br/>";
						email.addFiles("tmp\\"+tmp.getName()+today+".zip");
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("压缩出错");
						return ;
					}
					
				}
			}
			content+="以下是你的固定备份目录<br/>";
			String zipDir = propertiesBean.getValue("zipDir");
			if(!StringUtils.isEmpty(zipDir)){
				System.out.println("正在进行固定备份目录备份。。。。。。");
				String[] zipDirs = zipDir.split(";");
				for (int i = 0; i < zipDirs.length; i++) {
					try {
						File tmp=new File(zipDirs[i]);
						ZipUtil.zipFolder(zipDirs[i], "tmp\\"+tmp.getName()+today+"bak.zip");
						content+=tmp.getName()+"<br/>";
						email.addFiles("tmp\\"+tmp.getName()+today+"bak.zip");
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("压缩出错");
						return ;
					}
				}
			}
			System.out.println("更新配置文件。。。。。");
			propertiesBean.setValue("first", "false");
			propertiesBean.setValue("lastData", today);
			System.out.println("发送文件。。。。。");
			email.setFrom(propertiesBean.getValue("username"));
			email.setTOSend(propertiesBean.getValue("toSend"));
			email.setContent(content);
			email.setSubject("change2Mail备份文件"+today);
			if(email.send()){
				System.out.println("发送成功");
			}else{
				System.out.println("发送失败");
			}
			
		} else {
				System.out.println("备份开始。。。。。");
				String data=propertiesBean.getValue("lastData");
				String filter=propertiesBean.getValue("filter");
				String cheakDir = propertiesBean.getValue("cheakDir");
				content+="你好，自"+data+"起的文件变动如下：<br/>";
				if(!StringUtils.isEmpty(cheakDir)){
					System.out.println("正在进行更改文件目录备份。。。。。。");
					String[] dirs = cheakDir.split(";");
					for (int i = 0; i < dirs.length; i++) {
						File tmp=new File(dirs[i]);
						File file=new File(ClassLoader.getSystemResource("").getPath()+tmp.getName());
						if(!file.exists()){
							try {
								file.createNewFile();
							} catch (IOException e) {
								System.out.println("创建文档失败");
								e.printStackTrace();
							}
						}
						content+="文件夹"+tmp.getName()+"：<br/>";
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
						System.out.println("检查新建和修改的文件。。。。。。");
						for(File f:list){
							if(!lines.contains(f.getAbsolutePath())){
								news.add(f);
							}else{
								modefy.add(f);
							}
						}
						
						System.out.println("检查删除的文件。。。。。。");
						for(String f:lines){
							File old=new File(f);
							if(!now.contains(old)){
								del.add(old);
							}
						}
						logFiles(file,now);
						content+="删除文件：<br/>";
						for(File f:del){
							content+="&#9;"+f.getName()+"<br/>";
						}
						content+="新增文件：<br/>";
						for(File f:news){
							content+="&#9;"+f.getName()+"<br/>";
						}
						content+="修改文件：<br/>";
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
							System.out.println("压缩失败");
							return;
						}
						
						
					}
					
					
					String zipDir = propertiesBean.getValue("zipDir");
					if(!StringUtils.isEmpty(zipDir)){
						System.out.println("正在进行固定备份目录备份。。。。。。");
						String[] zipDirs = zipDir.split(";");
						for (int i = 0; i < zipDirs.length; i++) {
							try {
								File tmp=new File(zipDirs[i]);
								ZipUtil.zipFolder(zipDirs[i], "tmp\\"+tmp.getName()+today+"bak.zip");
								email.addFiles("tmp\\"+tmp.getName()+today+"bak.zip");
							} catch (Exception e) {
								e.printStackTrace();
								System.out.println("压缩出错");
								return ;
							}
						}
					}
					
					
					System.out.println("更改配置文件。。。。。");
					propertiesBean.setValue("lastData", today);
					System.out.println("发送文件。。。。。");
					email.setFrom(propertiesBean.getValue("username"));
					email.setTOSend(propertiesBean.getValue("toSend"));
					email.setContent(content);
					email.setSubject("change2Mail备份文件"+today);
					if(email.send()){
						System.out.println("发送成功");
					}else{
						System.out.println("发送失败");
					}
					
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
					 long to = df.parse(today).getTime();
					 long todel=Integer.parseInt(propertiesBean.getValue("time"))*(1000 * 60 * 60 * 24);
					 Date lastBak=new Date(to-todel) ;
					 System.out.println("删除"+df.format(lastBak)+"之前保存的历史文件。。。。。");
					 List<File> fileToDel=filesBean.getOlderFiles("tmp\\", lastBak,true);
					 for(File f:fileToDel){
						 try {
							 FileUtils.deleteQuietly(f);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							System.out.println("删除"+f.getName()+"文件失败");
						}
						
					 }
					System.out.println(new Date());
				}
		}
	}
	/**
	 * 将文件列表的绝对地址输出到一个文件中
	 * @param file	输出文件
	 * @param list	文件列表
	 * @return		成功返回true，失败返回false
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
