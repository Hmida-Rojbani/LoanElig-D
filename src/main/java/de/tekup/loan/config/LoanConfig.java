package de.tekup.loan.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import de.tekup.loan.endpoints.LoanEndPoint;

@EnableWs
@Configuration
public class LoanConfig {
	
	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherBean(ApplicationContext context){
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(context);
		servlet.setTransformWsdlLocations(true);
		
		return new ServletRegistrationBean<MessageDispatcherServlet>(servlet);
	}
	
	@Bean
	public XsdSchema schema() {
		return new SimpleXsdSchema(new ClassPathResource("loanEligebilty.xsd"));
	}
	
	@Bean("loan")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) {
		DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
		defaultWsdl11Definition.setPortTypeName("LoanEligebilityIndicator");
		defaultWsdl11Definition.setLocationUri("/ws");
		
		defaultWsdl11Definition.setSchema(schema);
		defaultWsdl11Definition.setTargetNamespace(LoanEndPoint.nameSpace);
		
		return defaultWsdl11Definition;
	}

}
