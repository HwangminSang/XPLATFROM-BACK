package kr.co.seoulit;

import java.nio.charset.Charset;

import javax.servlet.http.HttpSessionListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;

import kr.co.seoulit.system.common.interceptor.SessionListener;
import kr.co.seoulit.system.common.interceptor.SiteMeshFilter;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Logistic4Application{

	public static void main(String[] args) {
		SpringApplication.run(Logistic4Application.class, args);
	}
	// SiteMeshFilter web.xml 에 적용해주는거랑 같음
	@Bean
	public FilterRegistrationBean<SiteMeshFilter> siteMeshFilter() {
		FilterRegistrationBean<SiteMeshFilter> filter = new FilterRegistrationBean<SiteMeshFilter>();
		filter.setFilter(new SiteMeshFilter());
		return filter;

	}

	@Bean
	public ServletListenerRegistrationBean<HttpSessionListener> sessionListener() {
		return new ServletListenerRegistrationBean<HttpSessionListener>(new SessionListener());
	}
}
