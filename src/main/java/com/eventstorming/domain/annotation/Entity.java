path: path: {{boundedContext.name}}/{{{options.packagePath}}}/domain/{{boundedContext.name}}
---
package {{options.package}}.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 도메인 주도 설계(DDD)의 엔티티를 표시하는 어노테이션
 * 엔티티는 고유한 식별자를 가지며 시간과 상태 변화에 따라 연속성을 가짐
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
    
    /**
     * 엔티티의 이름
     */
    String name() default "";
    
    /**
     * 엔티티에 대한 설명
     */
    String description() default "";
    
    /**
     * 엔티티가 속한 애그리게이트 루트
     */
    Class<?> aggregateRoot() default void.class;
} 