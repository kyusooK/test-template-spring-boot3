forEach: Aggregate
fileName: {{namePascalCase}}Repository.java
path: {{boundedContext.name}}/{{{options.packagePath}}}/domain
---
package {{options.package}}.domain.{{boundedContext.name}};

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import {{options.package}}.domain.annotation.Repository;

import java.util.List;
import java.util.Optional;

/**
 * {{namePascalCase}} 애그리게이트의 저장소
 * 도메인 레이어의 영속성 인터페이스 정의
 */
@Component
@Repository(name = "{{namePascalCase}}Repository", 
           description = "{{namePascalCase}} 애그리게이트를 관리하는 저장소",
           aggregateRoot = {{namePascalCase}}.class)
public interface {{namePascalCase}}Repository extends JpaRepository<{{namePascalCase}}, Long> {
    
    /**
     * ID로 {{namePascalCase}} 애그리게이트 루트 조회
     * @param id 조회할 ID
     * @return 애그리게이트 루트 (Optional)
     */
    @Override
    Optional<{{namePascalCase}}> findById(Long id);
    
    /**
     * 모든 {{namePascalCase}} 애그리게이트 루트 조회
     * @return 모든 애그리게이트 루트 목록
     */
    @Override
    List<{{namePascalCase}}> findAll();
    
    // 추가적인 쿼리 메서드는 필요에 따라 정의
    
}
