package cn.stronglink.collection.guis.core.annotation;

import java.lang.annotation.ElementType;  
import java.lang.annotation.Retention;  
import java.lang.annotation.RetentionPolicy;  
import java.lang.annotation.Target;

/**
 * 在打包时不混淆指定类、方法的注解
 * @author yuzhantao
 *
 */
@Retention(RetentionPolicy.CLASS)  
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})  
public @interface NotProguard {

}
