package com.example.springboot.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 */
public class OneListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("恭喜恭喜，祝你今生平平安安！！！");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("兄弟走好！！！");
    }
}
