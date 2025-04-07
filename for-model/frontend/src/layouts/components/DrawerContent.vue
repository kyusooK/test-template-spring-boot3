forEach: Model
fileName: DrawerContent.vue
---
<script setup>
import {
  VerticalNavLink,
  VerticalNavSectionTitle,
} from '@layouts'
</script>

<template>
    <ul>
{{#boundedContexts}}
        <VerticalNavSectionTitle :item="{ heading: '{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}' }" />
    {{#aggregates}}
        <VerticalNavLink
            :item="{
                title: '{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}',
                to: '/{{namePlural}}',
            }"
        />
    {{/aggregates}}
    {{#views}}
        <VerticalNavLink
            :item="{
                title: '{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}',
                to: '/{{namePlural}}',
            }"
        />
    {{/views}}
{{/boundedContexts}}
    </ul>
</template>

<function>
window.$HandleBars.registerHelper('ifNotNull', function (displayName, name) {
    if(displayName){
        return displayName;
    }else{
        return name;
    }
})
</function>