package com.sean.bean;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
/**
 * 配置文件操作类
 * 实现功能：对properties 文件进行读写操作
 * @author Sean
 * 2015-6-6
 */
public class PropertiesBean {
	private String src="";
	private PropertiesConfiguration pc=null;
	/**
	 * 默认构造函数
	 */
	public PropertiesBean() {}
	public PropertiesBean(String src) {
		this.src=src;
		init();
	}
	
	/**
	 * 初始化函数
	 * @author Sean
	 * 2015-6-6
	 */
	public void init(){
		if(src.trim().equals("")){
			throw new RuntimeException("The path is null");
		}
		try {
			pc=new PropertiesConfiguration(src);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取值函数，根据对应的关键字获取对应的值
	 * @author Sean
	 * 2015-6-6
	 * @param key	关键字
	 * @return		返回关键字对应的值，若无该关键字，抛出异常
	 */
	public String getValue(String key){
		if(!pc.containsKey(key)){
			throw new RuntimeException("not such a key");
		}
		return pc.getString(key);
	}
	
	/**
	 * 设置对应的值，传入键值对，根据关键字修改对应的值
	 * @author Sean
	 * 2015-6-6
	 * @param key	关键字
	 * @param value	值
	 */
	public void setValue(String key,String value){
		if(!pc.containsKey(key)){
			throw new RuntimeException("not such a key");
		}
		pc.setProperty(key, value);
		try {
			pc.save();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置配置文件的地址
	 * @author Sean
	 * 2015-6-6
	 * @param src	配置文件的路径
	 */
	public void setSrc(String src) {
		this.src = src;
	}
}
