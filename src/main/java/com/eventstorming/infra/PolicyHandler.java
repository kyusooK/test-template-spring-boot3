representativeFor: Policy
path: {{name}}/{{{options.packagePath}}}/infra
fileName: PolicyHandler.java
---
package {{options.package}}.infrastructure;

import {{options.package}}.domain.event.*;
import {{options.package}}.domain.annotation.EventListener;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 이벤트 정책 핸들러
 * Kafka 토픽으로 수신된 이벤트를 처리하는 인프라스트럭처 레이어 구성요소
 */
@Component
@Slf4j
@Transactional
public class PolicyHandler implements Consumer<Message<String>> {

    private final ObjectMapper objectMapper;
    private final ApplicationContext applicationContext;

    @Autowired
    public PolicyHandler(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void accept(Message<String> message) {
        String eventType = (String) message.getHeaders().get("type");
        if (eventType == null) {
            return;
        }

        log.info("이벤트 수신: {}", eventType);
        String eventString = message.getPayload();

        try {
            // @EventListener 어노테이션이 있는 모든 메서드 찾기
            String finalEventType = eventType;
            Arrays.stream(applicationContext.getBeanDefinitionNames())
                .map(applicationContext::getBean)
                .forEach(bean -> {
                    Arrays.stream(bean.getClass().getMethods())
                        .filter(method -> method.isAnnotationPresent(EventListener.class))
                        .forEach(method -> {
                            EventListener annotation = method.getAnnotation(EventListener.class);
                            String handlerEventType = annotation.eventType();
                            
                            if (handlerEventType.isEmpty()) {
                                // 특정 이벤트 타입이 지정되지 않은 경우 파라미터 타입 사용
                                if (method.getParameterCount() > 0) {
                                    handlerEventType = method.getParameterTypes()[0].getSimpleName();
                                }
                            }
                            
                            if (finalEventType.equals(handlerEventType)) {
                                try {
                                    invokeEventHandler(bean, method, eventString);
                                } catch (Exception e) {
                                    log.error("이벤트 핸들러 호출 중 오류 발생", e);
                                }
                            }
                        });
                });
        } catch (Exception e) {
            log.error("이벤트 처리 중 오류 발생", e);
        }
    }

    private void invokeEventHandler(Object bean, Method method, String eventString) throws Exception {
        if (method.getParameterCount() > 0) {
            Class<?> parameterType = method.getParameterTypes()[0];
            Object event = objectMapper.readValue(eventString, parameterType);
            method.invoke(bean, event);
        } else {
            method.invoke(bean);
        }
    }
    
    {{#policies}}
    {{#relationEventInfo}}
    /**
     * {{../description}}
     */
    @EventListener(eventType = "{{eventValue.namePascalCase}}")
    public void whenever{{eventValue.namePascalCase}}_{{../namePascalCase}}({{eventValue.namePascalCase}} event) {
        log.info("\n\n##### 리스너 {{../namePascalCase}} : {}\n\n", event);
        
        {{#todo ../description}}{{/todo}}
        
        // 비즈니스 로직 처리
        {{#../aggregateList}}
        {{namePascalCase}}.{{../../nameCamelCase}}(event);
        {{/../aggregateList}}
    }
    {{/relationEventInfo}}
    {{/policies}}
}

<function>
window.$HandleBars.registerHelper('todo', function (description) {

    if(description){
        description = description.replaceAll('\n','\n\t\t// ')
        return description = '// Comments // \n\t\t//' + description;
    }
     return null;
});
</function>
