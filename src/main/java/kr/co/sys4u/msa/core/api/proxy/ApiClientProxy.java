package kr.co.sys4u.msa.core.api.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.springframework.core.env.Environment;

import kr.co.sys4u.msa.core.annotation.ApiRequest;
import kr.co.sys4u.msa.core.api.proxy.resttemplate.RestTemplateHelper;

public class ApiClientProxy implements InvocationHandler {
	private Environment environment;
	private String serverUrl;
	
	public ApiClientProxy(Environment environment) {
		this.environment = environment;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		ApiRequest apiRequest = method.getAnnotation(ApiRequest.class);
		if(apiRequest == null) {
			throw new IllegalStateException("ApiClient method shoud be annotated by @ApiRequest");
		}
		
		return requestWithRestTemplate(method, args);
	}

	private Object requestWithRestTemplate(Method method, Object[] args) {
		ApiRequest apiRequest = method.getAnnotation(ApiRequest.class);
		
		RestTemplateHelper helper = RestTemplateHelper.create()
				.environment(environment)
				.url(createUrl(apiRequest))
				.args(args)
				.httpMethod(apiRequest.method())
				.returnType(method.getReturnType());
		
		return helper.invoke();
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	private String createUrl(ApiRequest apiRequest) {
		return serverUrl + apiRequest.uri();
	}
	
}
