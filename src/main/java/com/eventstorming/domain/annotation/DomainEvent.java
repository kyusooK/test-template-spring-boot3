path: {{boundedContext.name}}/{{{options.packagePath}}}/domain
---
package {{options.package}}.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 도메인 주도 설계(DDD)의 도메인 이벤트를 표시하는 어노테이션
 * 도메인 이벤트는 도메인 내에서 발생한 중요한 사건을 나타냄
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DomainEvent {
    
    /**
     * 도메인 이벤트의 이름
     */
    String name() default "";
    
    /**
     * 도메인 이벤트에 대한 설명
     */
    String description() default "";
    
    /**
     * 도메인 이벤트를 발생시킨 애그리게이트 루트
     */
    Class<?> aggregateRoot() default void.class;
} 