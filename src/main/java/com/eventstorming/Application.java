path: {{name}}/{{{options.packagePath}}}
fileName: {{namePascalCase}}Application.java
---
package {{options.package}};
import {{options.package}}.config.kafka.KafkaProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Spring Boot 3.x 메인 애플리케이션 클래스
 * Clean Architecture 원칙을 따르는 구조로 설계됨
 */
@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing
public class {{namePascalCase}}Application {
    public static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run({{namePascalCase}}Application.class, args);
    }
}