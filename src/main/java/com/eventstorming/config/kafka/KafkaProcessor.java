path: {{name}}/{{{options.packagePath}}}/config/kafka
fileName: KafkaProcessor.java
---
package {{options.package}}.config.kafka;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;

/**
 * Kafka 메시징을 위한 Spring Cloud Stream 설정
 * Spring Boot 3.x의 함수형 프로그래밍 모델 사용
 */
@Configuration
public class KafkaProcessor {

    public static final String INPUT = "event-in";
    public static final String OUTPUT = "event-out";
    
    @Autowired
    private StreamBridge streamBridge;
    
    /**
     * 출력 토픽으로 메시지 전송
     * @param message 전송할 메시지
     * @return 메시지 전송 성공 여부
     */
    public boolean outboundTopic(Message<?> message) {
        return streamBridge.send(OUTPUT, message);
    }
    
    /**
     * 메시지를 구성하기 위한 MessageBuilder 가져오기
     * @return 메시지 빌더
     */
    public <T> MessageBuilder<T> outboundTopic() {
        return MessageBuilder.withPayload((T)null);
    }
    
    /**
     * 입력 토픽에 대한 Consumer 정의
     * Spring Cloud Stream에서 자동으로 사용됨
     * @return 입력 토픽에 대한 Consumer
     */
    @Bean
    public Consumer<Message<String>> eventConsumer() {
        return message -> {
            // 이벤트 핸들러에서 처리됨
        };
    }
}
