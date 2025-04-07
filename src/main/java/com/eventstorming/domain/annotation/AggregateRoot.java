path: path: {{boundedContext.name}}/{{{options.packagePath}}}/domain/{{boundedContext.name}}
---
package {{options.package}}.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 도메인 주도 설계(DDD)의 애그리게이트 루트를 표시하는 어노테이션
 * 애그리게이트 루트는 애그리게이트 내의 모든 엔티티에 대한 일관성과 트랜잭션 경계를 보장하는 주 엔티티
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AggregateRoot {
    
    /**
     * 애그리게이트 루트의 이름
     */
    String name() default "";
    
    /**
     * 애그리게이트 루트에 대한 설명
     */
    String description() default "";
} 