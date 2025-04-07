path: {{boundedContext.name}}/{{{options.packagePath}}}/domain
---
package {{options.package}}.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 도메인 주도 설계(DDD)의 이벤트 리스너를 표시하는 어노테이션
 * 이벤트 리스너는 도메인 이벤트를 수신하고 처리하는 메서드
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener {
    
    /**
     * 리스닝할 이벤트 타입
     */
    String eventType() default "";
} 