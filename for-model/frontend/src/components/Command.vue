forEach: Command
fileName: {{pascalCase name}}.vue
except: {{#checkCommand isRestRepository}}{{/checkCommand}}
---
<template>

    <v-card outlined>
        <v-card-title>
            {{namePascalCase}}
        </v-card-title>

        <v-card-text>
        {{#fieldDescriptors}}
        {{#if (isPrimitive className)}}
            <{{getPrimitiveType className}} label="{{namePascalCase}}" v-model="value.{{nameCamelCase}}" :editMode="editMode"/>
        {{else}}
        {{#checkVO className}}
            <{{className}} offline label="{{namePascalCase}}" v-model="value.{{nameCamelCase}}" :editMode="editMode" @change="change"/>
        {{/checkVO}}
        {{#checkEntityMember className}}
            <{{className}} offline label="{{namePascalCase}}" v-model="value.{{nameCamelCase}}" :editMode="editMode" @change="change"/>
        {{/checkEntityMember}}
        {{#checkListOfEntityMember className}}
            <{{getEntityClassNameOfList className}}List offline label="{{namePascalCase}}" v-model="value.{{nameCamelCase}}" :editMode="editMode" @change="change"/>
        {{/checkListOfEntityMember}}
        {{/if}}
        {{/fieldDescriptors}}
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="{{nameCamelCase}}"
            >
                {{namePascalCase}}
            </v-btn>
            
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>

export default {
    name: '{{namePascalCase}}Command',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
    {{#fieldDescriptors}}
        this.value.{{nameCamelCase}} = {{#setDefaultValue className}}{{/setDefaultValue}};
    {{/fieldDescriptors}}
    },
    watch: {
    },
    methods: {
        {{nameCamelCase}}() {
            this.$emit('{{nameCamelCase}}', this.value);
        },
        close() {
            this.$emit('closeDialog');
        },
        change() {
            this.$emit("update:modelValue", this.value);
        },
    }
}
</script>


<function>
    var importList = []
    var componentList = []

    window.$HandleBars.registerHelper('checkCommand', function (isRestRepository) {
        if(isRestRepository){
            return true;
        } else {
            return false;
        }
    });

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
        } else if(className == "Integer" || className == "Long" || className == "Double" || className == "Float" || className == "int" || className == "BigDecimal") {
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
                || className.endsWith("Payment") || className.endsWith("Money") || className.endsWith("Weather") || className.endsWith("Rating")) && className.indexOf("java.") == -1 && className.indexOf("List") == -1){
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
        var date = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10);
        if(className.endsWith("String")) {
            return "''";
        } else if(className.endsWith("Integer") || className.endsWith("Long") || className.endsWith("Double") || className.endsWith("Float")) {
            return 0;
        } else if(className.endsWith("Boolean")) {
            return false;
        } else if(className.endsWith("Date")) {
            return "'" + date + "'";
        } else if(className.includes("List")) {
            return "[]"
        } else {
            return "{}"
        }
    })

</function>