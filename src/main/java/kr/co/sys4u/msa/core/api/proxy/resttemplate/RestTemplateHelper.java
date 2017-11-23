package kr.co.sys4u.msa.core.api.proxy.resttemplate;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class RestTemplateHelper {
	private ApplicationContext applicationContext;
	private RestTemplateContext restTemplateContext;
	
	public static RestTemplateHelper create() {
		RestTemplateHelper helper = new RestTemplateHelper();
		helper.restTemplateContext = new RestTemplateContext();
		
		return helper;
	}
	
	public RestTemplateHelper environment(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		return this;
	}
	
	public RestTemplateHelper url(String url) {
		restTemplateContext.url = url;
		return this;
	}
	
	public <T> RestTemplateHelper returnType(Class<T> returnType) {
		restTemplateContext.returnType = returnType;
		return this;
	}
	
	public RestTemplateHelper httpMethod(HttpMethod httpMethod) {
		restTemplateContext.httpMethod = httpMethod;
		return this;
	}
	
	public RestTemplateHelper args(Object[] args) {
		restTemplateContext.args = args;
		return this;
	}
	
	public Object invoke() {
		Object returnValue = null;
		
		switch(restTemplateContext.httpMethod.name()) {
		case "GET":
			returnValue = get();
			break;
		case "POST":
			returnValue = post();
			break;
		case "PUT":
			put();
			break;
		case "DELETE":
			delete();
			break;
		default:
			throw new IllegalArgumentException("HttpMethod should be either GET, POST, PUT, DELETE.");
		}
		
		return returnValue;
	}
	
	public Object get() {
		return applicationContext.getBean(RestTemplate.class).getForObject(
				restTemplateContext.url, 
				restTemplateContext.returnType, 
				restTemplateContext.args);
	}
	
	public Object post() {
		return applicationContext.getBean(RestTemplate.class).postForObject(
				restTemplateContext.url,
				restTemplateContext.args.length > 0 ? restTemplateContext.args[0] : null, 
				restTemplateContext.returnType, 
				restTemplateContext.args.length > 1 ? Arrays.copyOfRange(restTemplateContext.args, 1, restTemplateContext.args.length) : new Object[] {});
	}
	
	public void put() {
		applicationContext.getBean(RestTemplate.class).put(
				restTemplateContext.url,
				restTemplateContext.args.length > 0 ? restTemplateContext.args[0] : null,
				restTemplateContext.args.length > 1 ? Arrays.copyOfRange(restTemplateContext.args, 1, restTemplateContext.args.length) : new Object[] {});
	}
	
	public void delete() {
		applicationContext.getBean(RestTemplate.class).delete(
				restTemplateContext.url,
				restTemplateContext.args);
	}
	
	private static class RestTemplateContext{
		String url;
		Class<?> returnType;
		HttpMethod httpMethod;
		Object[] args;
	}
}
