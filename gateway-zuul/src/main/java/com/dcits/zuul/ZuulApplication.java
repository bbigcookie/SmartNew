package com.dcits.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {

/*	Spring Cloud默认为Zuul编写并启用了一些过滤器，例如DebugFilter,FromBodyWrapperFilter，PreDecorationFilter等，
	这些过滤器都存放在spring-cloud-netflix-core这个jar里的
	在某些场景下，希望禁掉一些过滤器，只需设置zuul.<SimpleClassName>.<filterType>.disable=true即可，
	比如zuul.AccessFilter.pre.disable=true
*/
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ZuulApplication.class, args);
	}
}
