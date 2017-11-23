package kr.co.sys4u.msa.core.api.proxy;

import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import kr.co.sys4u.msa.core.annotation.ApiClient;

public class ApiClientProxyFactoryBean<T> implements FactoryBean<T> {
	@Autowired
	private ApplicationContext applicationContext;
	
	private Class<T> apiClientInterface;

	public ApiClientProxyFactoryBean(Class<T> apiClientInterface) {
		this.apiClientInterface = apiClientInterface;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getObject() throws Exception {
		ApiClient apiRequest = apiClientInterface.getAnnotation(ApiClient.class);
		
		ApiClientProxy apiClientProxy = new ApiClientProxy(applicationContext);
		apiClientProxy.setServerUrl(getServerUrl(apiRequest.serverUrl()));
		
		return (T) Proxy.newProxyInstance(
				apiClientInterface.getClassLoader(), 
				new Class<?>[] { apiClientInterface },
				apiClientProxy
		);
	}

	private String getServerUrl(String key) {
		return applicationContext.getEnvironment().getProperty(key) != null ? 
				applicationContext.getEnvironment().getProperty(key) : 
				key;
	}

	@Override
	public Class<?> getObjectType() {
		return this.apiClientInterface;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
