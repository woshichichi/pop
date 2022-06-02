package com.example.springboot.listener;
import javax.servlet.ServletContextEvent;
import java.util.EventListener;

public interface ServletContextListener extends EventListener {
	// ServletContext对象被创建后调用
    public void contextInitialized ( ServletContextEvent sce );

	// ServletContext对象被销毁前调用
    public void contextDestroyed ( ServletContextEvent sce );
}
