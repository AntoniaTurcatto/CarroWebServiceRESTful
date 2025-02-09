package test;

import org.springframework.beans.BeansException;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtil {

	private static SpringUtil instance;
	protected AbstractXmlApplicationContext ctx;
	
	private SpringUtil() {
		try {
			ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		} catch (BeansException e) {
			e.printStackTrace();
			System.out.println("ERRO AO CRIAR CONTEXT");
			throw e;
		}
	}
	
	public static SpringUtil getInstance() {
		if(instance == null) 
			instance = new SpringUtil();
		return instance;
	}
	
	@SuppressWarnings("rawtypes")
	public Object getBean(Class c) {
		if(ctx == null)
			return null;
		
		String[] beansNamesForType = ctx.getBeanNamesForType(c);
		if(beansNamesForType == null || beansNamesForType.length == 0)
			return null;
		
		String name = beansNamesForType[0];
		Object bean = getBean(name);
		
		if(bean != null)
			System.out.println("bean não nulo: "+bean.toString());
		else
			System.out.println("bean NULL");
		return bean;
		
	}
	

	public Object getBean(String name) {
		if(ctx == null)
			return null;
		Object bean = ctx.getBean(name);
		if(bean != null)
			System.out.println("bean não nulo: "+bean.toString());
		else
			System.out.println("bean NULL");
		return bean;
	}
}

