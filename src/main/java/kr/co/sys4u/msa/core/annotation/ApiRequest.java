package kr.co.sys4u.msa.core.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.http.HttpMethod;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface ApiRequest {
	String uri() default "/";
	HttpMethod method() default HttpMethod.GET;
}
