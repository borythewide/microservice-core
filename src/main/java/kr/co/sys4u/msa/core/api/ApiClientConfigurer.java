package kr.co.sys4u.msa.core.api;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiClientConfigurer implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {
	private Environment environment;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		new ApiClientScanner(registry, environment).scanAndUpdateBeanDefinition();
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();

		HttpClient httpClient = HttpClientBuilder.create()
				.setMaxConnTotal(Integer.valueOf(environment.getProperty("api.client.conn.maxConnTotal", "100")))
				.setMaxConnPerRoute(Integer.valueOf(environment.getProperty("api.client.conn.maxConnPerRoute", "100")))
				.build();

		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

		return restTemplate;
	}
}
