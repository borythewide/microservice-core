package kr.co.sys4u.msa.core.api.proxy.resttemplate;

import java.util.Arrays;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateHelper {
	private Environment environment;
	private RestTemplateContext restTemplateContext;
	
	public static RestTemplateHelper create() {
		RestTemplateHelper helper = new RestTemplateHelper();
		helper.restTemplateContext = new RestTemplateContext();
		
		return helper;
	}
	
	public RestTemplateHelper environment(Environment environment) {
		this.environment = environment;
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
	
	@SuppressWarnings("unchecked")
	public Object get() {
		boolean isMapUriVariable = restTemplateContext.args.length > 0 && restTemplateContext.args[0].getClass().isAssignableFrom(Map.class);
		
		return createRestTemplate().getForObject(
				restTemplateContext.url, 
				restTemplateContext.returnType, 
				isMapUriVariable ? (Map<String, ?>)restTemplateContext.args[0] : restTemplateContext.args);
	}
	
	@SuppressWarnings("unchecked")
	public Object post() {
		boolean isMapUriVariable = restTemplateContext.args.length > 1 && restTemplateContext.args[1].getClass().isAssignableFrom(Map.class);
		
		return createRestTemplate().postForObject(
				restTemplateContext.url,
				restTemplateContext.args[0], 
				restTemplateContext.returnType, 
				isMapUriVariable ? (Map<String, ?>)restTemplateContext.args[1] : Arrays.copyOfRange(restTemplateContext.args, 1, restTemplateContext.args.length));
	}
	
	@SuppressWarnings("unchecked")
	public void put() {
		boolean isMapUriVariable = restTemplateContext.args.length > 1 && restTemplateContext.args[1].getClass().isAssignableFrom(Map.class);
		
		createRestTemplate().put(
				restTemplateContext.url,
				restTemplateContext.args[0], 
				isMapUriVariable ? (Map<String, ?>)restTemplateContext.args[1] : Arrays.copyOfRange(restTemplateContext.args, 1, restTemplateContext.args.length));
	}
	
	@SuppressWarnings("unchecked")
	public void delete() {
		boolean isMapUriVariable = restTemplateContext.args.length > 0 && restTemplateContext.args[0].getClass().isAssignableFrom(Map.class);
		createRestTemplate().delete(
				restTemplateContext.url,
				isMapUriVariable ? (Map<String, ?>)restTemplateContext.args[0] : restTemplateContext.args);
	}
	
	public RestTemplate createRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		
		HttpClient httpClient = HttpClientBuilder.create()
				.setMaxConnTotal(Integer.valueOf(environment.getProperty("api.client.conn.maxConnTotal", "100")))
				.setMaxConnPerRoute(Integer.valueOf(environment.getProperty("api.client.conn.maxConnPerRoute", "100")))
				.build();
		
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

		return restTemplate;
	}
	
	private static class RestTemplateContext{
		String url;
		Class<?> returnType;
		HttpMethod httpMethod;
		Object[] args;
	}
}
