package ru.nechay.practice.battlecode.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		//TODO: Сделать вьюху логин и регистрации
		registry.addViewController("/login").setViewName("battlecode/login");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
	    registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
	}

}
