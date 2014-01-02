package com.hesong.preload;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Preload {
    public static ApplicationContext APP_CONTEXT;
    
    static{
        APP_CONTEXT = new ClassPathXmlApplicationContext(
                "spring/config/BeanLocations.xml");
    }
}
