package kr.co.sys4u.msa.core.api.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.springframework.context.ApplicationContext;

import kr.co.sys4u.msa.core.annotation.ApiRequest;
import kr.co.sys4u.msa.core.api.proxy.resttemplate.RestTemplateHelper;

public class ApiClientProxy implements InvocationHandler {
	private ApplicationContext applicationContext;
	private String serverUrl;
	
	public ApiClientProxy(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
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
		
		return RestTemplateHelper.create()
				.environment(applicationContext)
				.url(createUrl(apiRequest))
				.args(args)
				.httpMethod(apiRequest.method())
				.returnType(method.getReturnType())
				.invoke();
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	private String createUrl(ApiRequest apiRequest) {
		return serverUrl + apiRequest.uri();
	}
	
}
