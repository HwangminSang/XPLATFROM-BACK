package kr.co.seoulit.system.common.configuration;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ContainerConfiguration {
	
	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.addAdditionalTomcatConnectors(createAjpConnector());
		return tomcat;
	}

	private Connector createAjpConnector() {
		Connector ajpConnector = new Connector("AJP/1.3");
		ajpConnector.setPort(8009);
		ajpConnector.setSecure(false);
		ajpConnector.setAllowTrace(false);
		ajpConnector.setScheme("http");
		ajpConnector.setEnableLookups(true);
		((AbstractAjpProtocol<?>) ajpConnector.getProtocolHandler()).setSecretRequired(false);
		return ajpConnector;
	}

	@Bean
    public MappingJackson2JsonView jsonView(){
		ObjectMapper om = new ObjectMapper();
        return new MappingJackson2JsonView(om);
    }
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		commonsMultipartResolver.setMaxUploadSizePerFile(10*1024*1024);
		return commonsMultipartResolver;		
	}
	
}
