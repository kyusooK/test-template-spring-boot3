forEach: Event
fileName: {{namePascalCase}}.java
path: {{boundedContext.name}}/{{{options.packagePath}}}/domain/event
---
package {{options.package}}.domain.event;

import {{options.package}}.domain.annotation.DomainEvent;
import {{options.package}}.domain.{{boundedContext.name}}.{{aggregate.namePascalCase}};
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * {{namePascalCase}} 도메인 이벤트
 * 발생 상황: {{trigger}}
 * {{description}}
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@DomainEvent(name = "{{namePascalCase}}", 
            description = "{{description}}",
            aggregateRoot = {{aggregate.namePascalCase}}.class)
public class {{namePascalCase}} extends AbstractEvent {

    private {{#aggregate.aggregateRoot.fieldDescriptors}}{{#isKey}}{{className}}{{/isKey}}{{/aggregate.aggregateRoot.fieldDescriptors}} {{aggregate.nameCamelCase}}Id;
    {{#fieldDescriptors}}
    private {{className}} {{nameCamelCase}};
    {{/fieldDescriptors}}

    public {{namePascalCase}}({{aggregate.namePascalCase}} aggregate) {
        super(aggregate);
    }
}
