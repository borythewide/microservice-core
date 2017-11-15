package kr.co.sys4u.msa.core.api;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ApiClientConfigurer implements BeanDefinitionRegistryPostProcessor, EnvironmentAware{
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

}
