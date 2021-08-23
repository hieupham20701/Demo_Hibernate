package com.hibernate.demo;

import com.hibernate.pojo.Category;
import com.hibernate.pojo.Manufacturer;
import com.hibernate.pojo.Product;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.util.Properties;


public class HibernateUtil {
	private final static SessionFactory FACTORY;
	
	static {
		Configuration conf = new Configuration();
		Properties pros = new Properties();
		pros.put(Environment.DIALECT,"org.hibernate.dialect.MySQLDialect");
		pros.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
		pros.put(Environment.URL,"jdbc:mysql://localhost:3306/sale");
		pros.put(Environment.USER,"root");
		pros.put(Environment.PASS,"sapassword");
                
		conf.setProperties(pros);
		conf.addAnnotatedClass(Category.class);
                conf.addAnnotatedClass(Product.class);
                conf.addAnnotatedClass(Manufacturer.class);
                
		ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
		
		FACTORY = conf.buildSessionFactory(registry);
	}
	public static SessionFactory getSessionFactory() {
		return FACTORY;
	}
}
