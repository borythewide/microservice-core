package kr.co.sys4u.msa.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.data.mongodb.core.mapping.Document;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Document
public @interface RedirectTo {
    String message() default "";
    String url() default "";
}
