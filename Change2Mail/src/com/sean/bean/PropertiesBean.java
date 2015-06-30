package com.sean.bean;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
/**
 * �����ļ�������
 * ʵ�ֹ��ܣ���properties �ļ����ж�д����
 * @author Sean
 * 2015-6-6
 */
public class PropertiesBean {
	private String src="";
	private PropertiesConfiguration pc=null;
	/**
	 * Ĭ�Ϲ��캯��
	 */
	public PropertiesBean() {}
	public PropertiesBean(String src) {
		this.src=src;
		init();
	}
	
	/**
	 * ��ʼ������
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
	 * ȡֵ���������ݶ�Ӧ�Ĺؼ��ֻ�ȡ��Ӧ��ֵ
	 * @author Sean
	 * 2015-6-6
	 * @param key	�ؼ���
	 * @return		���عؼ��ֶ�Ӧ��ֵ�����޸ùؼ��֣��׳��쳣
	 */
	public String getValue(String key){
		if(!pc.containsKey(key)){
			throw new RuntimeException("not such a key");
		}
		return pc.getString(key);
	}
	
	/**
	 * ���ö�Ӧ��ֵ�������ֵ�ԣ����ݹؼ����޸Ķ�Ӧ��ֵ
	 * @author Sean
	 * 2015-6-6
	 * @param key	�ؼ���
	 * @param value	ֵ
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
	 * ���������ļ��ĵ�ַ
	 * @author Sean
	 * 2015-6-6
	 * @param src	�����ļ���·��
	 */
	public void setSrc(String src) {
		this.src = src;
	}
}
