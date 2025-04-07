path: {{name}}/{{{options.packagePath}}}/domain
fileName: AbstractEvent.java
---
package {{options.package}}.domain.event;

import {{options.package}}.{{namePascalCase}}Application;
import {{options.package}}.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.MimeTypeUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 모든 도메인 이벤트의 기본 클래스
 * DDD의 도메인 이벤트 패턴을 구현
 */
@Data
@Slf4j
public abstract class AbstractEvent {

    private String eventType;
    private Long timestamp;

    public AbstractEvent(Object aggregate) {
        this();
        BeanUtils.copyProperties(aggregate, this);
    }

    public AbstractEvent() {
        this.setEventType(this.getClass().getSimpleName());
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 이벤트를 Kafka 토픽에 발행
     */
    public void publish() {
        KafkaProcessor processor = {{namePascalCase}}Application.applicationContext.getBean(
            KafkaProcessor.class
        );
        
        Message<AbstractEvent> message = MessageBuilder
            .withPayload(this)
            .setHeader("type", getEventType())
            .build();
            
        boolean result = processor.outboundTopic(message);
        
        if (result) {
            log.info("Event published successfully: {}", this);
        } else {
            log.warn("Failed to publish event: {}", this);
        }
    }

    /**
     * 트랜잭션 커밋 후 이벤트를 발행
     */
    public void publishAfterCommit() {
        TransactionSynchronizationManager.registerSynchronization(new org.springframework.transaction.support.TransactionSynchronizationAdapter() {
            @Override
            public void afterCompletion(int status) {
                AbstractEvent.this.publish();
            }
        });
    }

    /**
     * 이벤트가 유효한지 검증
     */
    public boolean validate() {
        return getEventType().equals(getClass().getSimpleName());
    }
    
    /**
     * 이벤트를 JSON 문자열로 변환
     */
    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try {
            json = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert event to JSON", e);
            throw new RuntimeException("JSON format exception", e);
        }

        return json;
    }
}