package com.lotuas.starter.spring.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



public class SystemContext
{
	private final static Log log4j = LogFactory.getLog(SystemContext.class);
	
	private static ApplicationContext context;
	
	private static ServletContext servletContext;
	
	static Map<String, HttpSession> sessions=new HashMap<String, HttpSession>();
	
	private static Map<String, String> systemInfo=new HashMap<String, String>();

	private static Properties VMProperties = System.getProperties();

	static{
		try
		{
			init();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			log4j.error("系统初始化失败！");
		}
	}
	
	private static void init() throws IOException{
		//系统配置文件读取
		Properties sysProps=new Properties();
		InputStream sysIn=Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
		if(sysIn!=null){
			sysProps.load(sysIn);
			for(Map.Entry<Object, Object> en: sysProps.entrySet()){
				systemInfo.put(String.valueOf(en.getKey()), String.valueOf(en.getValue()));
			}
		}

		
	}

	public static ApplicationContext getContext() {
		return context;
	}

	public static <T> T getBean(String name, Class<? extends T> clazz){
		return context.getBean(name, clazz);
	}
	
	public static <T> T getBean(Class<? extends T> clazz){
		return context.getBean(clazz);
	}
	
	public static String getSystemInfo(String key){
		return systemInfo.get(key);
	}
	
	public static ServletContext getServletContext(){
		return servletContext;
	}

	public static void setServletContext(ServletContext ctx){
		servletContext=ctx;
	}

	public static Map<String, HttpSession> getSessions() {
		return new HashMap<String, HttpSession>(sessions);
	}


}
