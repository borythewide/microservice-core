package kr.co.sys4u.msa.core.api;

import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import kr.co.sys4u.msa.core.annotation.ApiClient;
import kr.co.sys4u.msa.core.api.proxy.ApiClientProxyFactoryBean;

public class ApiClientScanner extends ClassPathBeanDefinitionScanner {
	private static final String KEY_FOR_API_CLIENT_BASE_PACKAGES = "api.client.basepackages";
	
	private Environment environment;

	public ApiClientScanner(BeanDefinitionRegistry registry, Environment environment) {
		super(registry);
		this.environment = environment;
		
		initialize();
	}
	
	private void initialize() {
		resetFilters(false);
		addIncludeFilter(new AnnotationTypeFilter(ApiClient.class));
	}
	
	@Override
	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
	}
	
	public void scanAndUpdateBeanDefinition() {
		String strBeanPackages = environment.getProperty(KEY_FOR_API_CLIENT_BASE_PACKAGES);
		if(strBeanPackages == null) {
			return;
		}
		
		String[] beanPackages = strBeanPackages.split(",");
		
		Set<BeanDefinitionHolder> beanDefinitionHolders = doScan(beanPackages);
		for(BeanDefinitionHolder holder : beanDefinitionHolders) {
			updateBeanDefinition(holder.getBeanDefinition());
		}
	}
	
	/**
	 * @param beanDefinition
	 */
	private void updateBeanDefinition(BeanDefinition beanDefinition) {
		GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition) beanDefinition;
		genericBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
		genericBeanDefinition.setBeanClass(ApiClientProxyFactoryBean.class);
		genericBeanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
		genericBeanDefinition.setLazyInit(true);
	}
}
