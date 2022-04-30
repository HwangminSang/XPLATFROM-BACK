package kr.co.seoulit;

import javax.servlet.http.HttpSessionListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import kr.co.seoulit.system.common.interceptor.SessionListener;


@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Logistic4Application{

	public static void main(String[] args) {
		SpringApplication.run(Logistic4Application.class, args);
	}


	@Bean
	public ServletListenerRegistrationBean<HttpSessionListener> sessionListener() {
		return new ServletListenerRegistrationBean<HttpSessionListener>(new SessionListener());
	}
}
