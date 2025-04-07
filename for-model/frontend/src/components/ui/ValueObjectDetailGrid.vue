forEach: ValueObject
fileName: {{namePascalCase}}DetailGrid.vue
except: {{#checkDefaultVO namePascalCase referenceClass}}{{/checkDefaultVO}}
---
<template>
    <div class="panel">
        <div class="label-title">\{{label}}</div>
        <div class="table-responsive">
            <v-btn v-if="editMode" @click="addDetailRow()">추가</v-btn>
            <v-btn v-if="editMode" style="margin-left: 3px;" @click="detailDeleteRow()">삭제</v-btn>
            <v-table v-if="!editMode">
                <thead>
                    <tr>
                        <th>Id</th>
                        {{#fieldDescriptors}}
                        {{#if (isNotId nameCamelCase)}}
                        {{#if (isPrimitive className)}}
                        {{#if (isStringType (getPrimitiveType className))}}
                        {{else}}
                        <th>{{nameCamelCase}}</th>
                        {{/if}}
                        {{else}}
                        <th>{{namePascalCase}}</th>
                        {{/if}}
                        {{/if}}
                        {{/fieldDescriptors}}
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(detailVal, idx) in selectedRow.{{nameCamelCase}}s" :key="detailVal" >
                        <td class="font-semibold">\{{ idx + 1 }}</td>
                        {{#fieldDescriptors}}
                        {{#if (isNotId nameCamelCase)}}
                        {{#if (isPrimitive className)}}
                        {{#if (isStringType (getPrimitiveType className))}}
                        {{else}}
                        <td class="whitespace-nowrap">{{#getNameCamelCase nameCamelCase}}{{/getNameCamelCase}}</td>
                        {{/if}}
                        {{else}}
                        <td class="whitespace-nowrap">
                            <{{namePascalCase}} v-model="detailVal.{{nameCamelCase}}" :editMode="editMode"/>
                        </td>
                        {{/if}}
                        {{/if}}
                        {{/fieldDescriptors}}
                        <td class="whitespace-nowrap">
                            <Icon icon="mi:delete" @click="deleteRow(detailVal)" />
                        </td>
                    </tr>
                </tbody>
            </v-table>
            <v-table v-else>
                <thead>
                    <tr>
                        <th>Id</th>
                        {{#fieldDescriptors}}
                        {{#if (isNotId nameCamelCase)}}
                        {{#if (isPrimitive className)}}
                        {{#if (isStringType (getPrimitiveType className))}}
                        {{else}}
                        <th>{{nameCamelCase}}</th>
                        {{/if}}
                        {{else}}
                        <th>{{namePascalCase}}</th>
                        {{/if}}
                        {{/if}}
                        {{/fieldDescriptors}}
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(addVal, idx) in newValue" :key="addVal" >
                        <td class="font-semibold">\{{ idx + 1 }}</td>
                        {{#fieldDescriptors}}
                        {{#if (isNotId nameCamelCase)}}
                        {{#if (isPrimitive className)}}
                        {{#if (isStringType (getPrimitiveType className))}}
                        {{else}}
                        <td class="whitespace-nowrap">
                            <{{getPrimitiveType className}} style="margin-left: -5px; width: 150px;" :editMode="editMode" v-model="addVal.{{nameCamelCase}}"/>
                        </td>
                        {{/if}}
                        {{else}}
                        <td class="whitespace-nowrap">
                            <{{namePascalCase}} v-model="addVal.{{nameCamelCase}}" :editMode="editMode"/>
                        </td>
                        {{/if}}
                        {{/if}}
                        {{/fieldDescriptors}}
                    </tr>
                </tbody>
            </v-table>
        </div>
    </div>
</template>

<script>
import BaseDetailGrid from '../base-ui/BaseDetailGrid.vue';

export default {
    name: '{{namePascalCase}}',
    mixins: [BaseDetailGrid],
    components: {
    },
    props: {
        label: String,
        editMode: Boolean,
        selectedRow: Object,
    },
    data: ()=>({
    }),
    created(){
    },
    methods: {
    }
}
</script>

<function>
    var importList = []
    var componentList = []

    window.$HandleBars.registerHelper('checkDefaultVO', function (name, isReference) {
        if(name == 'Address' || name == 'Photo' || name == 'Email' || name == 'EventViewer' || name == 'File' || name == 'Money' || name == 'Payment' || name == 'Rating' || name == 'User' || name == 'Weather' || isReference) {
            return true;
        } else {
            return false;
        }
    })

    window.$HandleBars.registerHelper('ifNotNull', function (displayName, name) {
        if(displayName){
	        return displayName;
        }else{
	        return name;
        }
    })
    window.$HandleBars.registerHelper('isNotName', function (className) {
        return (className != 'name')
    })
    window.$HandleBars.registerHelper('isNotId', function (className) {
        return (className != 'id')
    })
    window.$HandleBars.registerHelper('isStringType', function (type) {
        return (type === "String");
    })
    window.$HandleBars.registerHelper('getPrimitiveType', function (className, options) {
        if(className.includes("String")) {
            if(this.isLob) {
                return "LargeObject";
            } else {
                return "String";
            }
        } else if(className.includes("Integer") || className.includes("Long") || className.includes("Double") || className.includes("Float") || className.includes("int") || className.includes("BigDecimal")) {
            if(this.isLob) {
                return "LargeObject";
            } else {
                return "Number";
            }
        } else if(className.includes("Boolean")) {
            return "Boolean";
        } else if(className.includes("Date")) {
            return "Date";
        }
    })

    window.$HandleBars.registerHelper('isPrimitive', function (className) {
        if(className.includes("String") || className.includes("Integer") || className.includes("Long") || className.includes("Double") || className.includes("Float")
                || className.includes("Boolean") || className.includes("Date")){
            return true;
        } else {
            return false;
        }
    })
    window.$HandleBars.registerHelper('isPrimitiveImport', function (className) {
        if(!importList.includes(className)){
            importList.push(className)
            if(className.includes("String") || className.includes("Integer") || className.includes("Long") || className.includes("Double") || className.includes("Float")
                || className.includes("Boolean") || className.includes("Date")){
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    })
    window.$HandleBars.registerHelper('isPrimitiveComponent', function (className) {
        if(!componentList.includes(className)){
            componentList.push(className)
            if(className.includes("String") || className.includes("Integer") || className.includes("Long") || className.includes("Double") || className.includes("Float")
                || className.includes("Boolean") || className.includes("Date")){
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    })
    window.$HandleBars.registerHelper('getNameCamelCase', function (nameCamelCase) {
        if(nameCamelCase){
            var tdVal = '{{ detailVal.' + nameCamelCase + ' }}'
            return tdVal
        }
    })
</function>