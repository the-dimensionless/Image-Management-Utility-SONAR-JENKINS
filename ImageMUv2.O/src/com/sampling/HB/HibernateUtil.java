package com.sampling.HB;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	public static SessionFactory sessionFactoryInstance =  new Configuration().configure().buildSessionFactory(); 
}
