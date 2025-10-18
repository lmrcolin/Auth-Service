package com.unam.dwb.auth.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.unam.dwb.auth.cripto.CriptoUtils;

import lombok.extern.slf4j.Slf4j;
import javax.net.ssl.SSLContext;

import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.apache.tomcat.util.net.SSLHostConfigCertificate;
import org.apache.tomcat.util.net.SSLHostConfigCertificate.Type;


@Slf4j
@Configuration
public class WebserviceConfig {
	
	@SuppressWarnings("unused")
	private SSLContext sslContext;
	
	@Value("${system.p12FilePath}")
	private String p12FilePath;
	
	@Value("${system.p12PrivkPwd}")
	private String p12PrivkPwd;
	
	@Value("${system.p12PrivkAlias}")
	private String p12PrivkAlias;
	
	@Value("${system.hostname}")
	private String hostname;
	
	@Value("${system.p12FilePwd}")
	private String p12FilePwd;
	
	@Value("${server.port}")
	private int port;

	@Bean
	WebServerFactoryCustomizer<TomcatServletWebServerFactory> customTomcatConnector() {
	    return factory -> 
	    factory.addConnectorCustomizers(connector ->{
	        try {
	        	Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
	            connector.setPort(port);
	            connector.setScheme("https");
	            connector.setSecure(true);
	            protocol.setSSLEnabled(true);
	            SSLHostConfig sslHostConfig = new SSLHostConfig();
	            sslHostConfig.setHostName(hostname);
	            sslHostConfig.setProtocols("TLSv1.2+TLSv1.3");
	            sslHostConfig.setCiphers("TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384");
	            SSLHostConfigCertificate certificate = new SSLHostConfigCertificate(sslHostConfig, Type.RSA);
	            certificate.setCertificateKeystoreFile("classpath:" + p12FilePath);
	            certificate.setCertificateKeystoreType("PKCS12");
	            certificate.setCertificateKeystorePassword(CriptoUtils.decryptString(p12PrivkPwd));
	            certificate.setCertificateKeyAlias(CriptoUtils.decryptString(p12PrivkAlias));
	            sslHostConfig.addCertificate(certificate);
	            protocol.addSslHostConfig(sslHostConfig);
	            protocol.setDefaultSSLHostConfigName(hostname);
	        } catch (Exception ex) {
	        	log.error("Excepción atrapada al configurar connector", ex);
	            System.exit(-1);
	        }
	    });
	} 
	

}
