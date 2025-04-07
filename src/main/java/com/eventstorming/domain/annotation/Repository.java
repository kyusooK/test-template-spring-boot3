path: {{boundedContext.name}}/{{{options.packagePath}}}/domain/annotation
except: {{#checkException this}}{{/checkException}}
---
package {{options.package}}.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 도메인 주도 설계(DDD)의 리포지토리를 표시하는 어노테이션
 * 리포지토리는 도메인 객체의 저장, 조회, 삭제 등의 영속성 관리를 담당
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Repository {
    
    /**
     * 리포지토리의 이름
     */
    String name() default "";
    
    /**
     * 리포지토리에 대한 설명
     */
    String description() default "";
    
    /**
     * 리포지토리가 관리하는 애그리게이트 루트 타입
     */
    Class<?> aggregateRoot() default void.class;
} 

<function>
    window.$HandleBars.registerHelper('checkException', function (boundedContext) {
        if(boundedContext.views.length > 0){
            return true;
        }else{
            return false;
        }
    });
</function>