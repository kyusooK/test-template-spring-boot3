path: path: {{boundedContext.name}}/{{{options.packagePath}}}/domain/{{boundedContext.name}}
---
package {{options.package}}.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 도메인 주도 설계(DDD)의 값 객체를 표시하는 어노테이션
 * 값 객체는 식별자가 없으며 값에 의해서만 정의되는 불변 객체
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueObject {
    
    /**
     * 값 객체의 이름
     */
    String name() default "";
    
    /**
     * 값 객체에 대한 설명
     */
    String description() default "";
} 