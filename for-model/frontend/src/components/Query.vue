forEach: View
fileName: {{namePascalCase}}.vue
except: {{#ifEquals dataProjection "query-for-aggregate"}}false{{else}}true{{/ifEquals}}
---
<template>
    <v-card outlined style="border-radius: 0; margin-top: 10px; padding-bottom: 10px; background-color: #F4F5FA;">
        <v-card-title>
            {{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}
        </v-card-title>
    
        <v-card-text style="margin-top:-10px;">
            <v-row no-gutters style="margin: 15px 0px -10px -15px;">
                {{#queryParameters}}
                {{#if (isPrimitive className)}}
                <v-col style="max-width:140px;">
                    <{{getPrimitiveType className}} class="attributes-list" label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}" v-model="value.parameters.{{nameCamelCase}}" :editMode="editMode"/>
                </v-col>
                {{else}}
                {{#checkVO className}}
                <v-col style="max-width:140px;">
                    <{{className}} offline label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}" v-model="value.parameters.{{nameCamelCase}}" :editMode="editMode" @change="change"/>
                </v-col>
                {{/checkVO}}
                {{#checkEntityMember className}}
                <v-col style="max-width:150px;">
                    <{{className}} style="margin-top: 0px; margin-left: 10px;" offline label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}" v-model="value.parameters.{{nameCamelCase}}" :editMode="editMode" @change="change"/>
                </v-col>
                {{/checkEntityMember}}
                {{#checkListOfEntityMember className}}
                <v-col style="max-width:140px;">
                    <{{getEntityClassNameOfList className}}List offline label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}" v-model="value.parameters.{{nameCamelCase}}" :editMode="editMode" @change="change"/>
                </v-col>
                {{/checkListOfEntityMember}}
                {{/if}}
                {{/queryParameters}}
                <v-col>
                    <v-btn class="gs-query-search-btn contrast-primary-text"
                        @click="search"
                        small
                        style="margin-left: 35px; margin-top: 30px;"
                        color="primary"
                    >
                        <v-icon small>mdi-magnify</v-icon>검색
                    </v-btn>
                </v-col>
            </v-row>
        </v-card-text>
    </v-card>
</template>

<script>
    export default {
        name: '{{namePascalCase}}',
        components:{
        },
        props: {},
        data: () => ({
            editMode: true,
            value: {
                apiPath: '{{#attached 'Aggregate' this}}{{namePlural}}{{/attached}}/search/{{nameCamelCase}}',
                parameters: {}
            },
        }),
        created() {
        },
        watch: {
        },
        methods: {
            search() {
                let search = null;
                search = this.value;
                this.$emit('search', search);
            },
            change() {
                this.$emit("update:modelValue", this.value);
            },
        }
    }
</script>
<style>
.attributes-list{
    margin-left: 15px;
}
</style>


<function>
    var importList = []
    var componentList = []

//if(this.queryParameters!=null) alert(this.queryParameters)
    window.$HandleBars.registerHelper('ifNotNull', function (displayName, name) {
        if(displayName){
            return displayName;
        }else{
            return name;
        }
    })
    window.$HandleBars.registerHelper('print', function (value) {
        console.log(value)
    });
    window.$HandleBars.registerHelper('classType', function (type, options) {
        if(type.endsWith('Class')){
            return true;
        } else {
            return false;
        }
    })
    window.$HandleBars.registerHelper('checkCommandPut', function (info, options) {
        if(info.endsWith('PUT')){
            return options.fn(this);
        } else {
            options.inverse(this);
        }
    })
    window.$HandleBars.registerHelper('checkCommandDelete', function (info, options) {
        if(info.endsWith('DELETE')){
            return options.fn(this);
        } else {
            options.inverse(this);
        }
    })
    window.$HandleBars.registerHelper('checkCommandPost', function (info, options) {
        if(info.endsWith('POST')) {
            return options.fn(this);
        } else {
            options.inverse(this);
        }
    })
    window.$HandleBars.registerHelper('isNotId', function (className) {
        return (className != 'id')
    })

    window.$HandleBars.registerHelper('isPrimitive', function (className) {
        if(className == "String" || className == "Integer" || className == "Long" || className == "Double" || className == "Float"
                || className == "Boolean" || className == "Date"){
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

    window.$HandleBars.registerHelper('getPrimitiveType', function (className) {
        if(className == "String") {
            return "String";
        } else if(className == "Integer" || className == "Long" || className == "Double" || className == "Float" || className == "int") {
            return "Number";
        } else if(className == "Boolean") {
            return "Boolean";
        } else if(className == "Date") {
            return "Date";
        }
    })

    window.$HandleBars.registerHelper('checkVO', function (className, options) {
        if(className.endsWith("Address") || className.endsWith("Photo") || className.endsWith("User") || className.endsWith("Email") 
                || className.endsWith("Payment") || className.endsWith("Money") || className.endsWith("Weather") || className.endsWith("Rating") ){
            return options.fn(this);
        }
    })

    window.$HandleBars.registerHelper('checkEntityMember', function (className, options) {
        if(!(className.endsWith("Address") || className.endsWith("Photo") || className.endsWith("User") || className.endsWith("Email") 
                || className.endsWith("Payment") || className.endsWith("Money") || className.endsWith("Weather") || className.endsWith("String")|| className.endsWith("Rating")) && className.indexOf("java.") == -1 && className.indexOf("List") == -1){
            return options.fn(this);
        } else {
            return options.inverse(this);
        }
    })

    window.$HandleBars.registerHelper('checkListOfEntityMember', function (className, options) {
        if(className.indexOf("List") == 0) {
            return options.fn(this);
        } else {
            return options.inverse(this);
        }
    })

    window.$HandleBars.registerHelper('getEntityClassNameOfList', function (listClassName) {
        var regex = /\<(.*?)\>/g;
        var match = regex.exec(listClassName);
        if (match) {
            return (match[1]);
        }
        return "NO-CLASS";
    })
    
    window.$HandleBars.registerHelper('setDefaultValue', function (className) {
        if(className.endsWith("String")) {
            return "''";
        } else if(className.endsWith("Integer") || className.endsWith("Long") || className.endsWith("Double") || className.endsWith("Float")) {
            return 0;
        } else if(className.endsWith("Boolean")) {
            return false;
        } else if(className.endsWith("Date")) {
            return "(new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)";
        } else if(className == "List") {
            return "[]"
        } else {
            return "{}"
        }
    })

</function>