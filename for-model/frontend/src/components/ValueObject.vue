forEach: ValueObject
fileName: {{namePascalCase}}.vue
except: {{#checkDefaultVO namePascalCase}}{{/checkDefaultVO}}
---
<template>
    {{#referenceClass}}
    <div>
        <!-- namefield의 값은 참조하는 어그리거트의 속성값중 하나로 변경하면 변경한 속성에 맞는 필드명이 표시됩니다.  ex)nameField="속성값" <span>\{{ value && value.속성값 ? value.속성값 : '' }}</span> -->
        <div v-if="Array.isArray(value)">
            <div v-for="(item, index) in value" :key="index">
                <span>\{{ item.{{#../boundedContext.aggregates}}{{#outgoingRelations}}{{#target}}{{#attached 'BoundedContext' this}}{{#aggregates}}{{#if (compareName name ../../../../../../referenceClass)}}{{#getNameField aggregateRoot.fieldDescriptors}}{{/getNameField}}{{/if}}{{/aggregates}}{{/attached}}{{/target}}{{/outgoingRelations}}{{/../boundedContext.aggregates}} ? item.{{#../boundedContext.aggregates}}{{#outgoingRelations}}{{#target}}{{#attached 'BoundedContext' this}}{{#aggregates}}{{#if (compareName name ../../../../../../referenceClass)}}{{#getNameField aggregateRoot.fieldDescriptors}}{{/getNameField}}{{/if}}{{/aggregates}}{{/attached}}{{/target}}{{/outgoingRelations}}{{/../boundedContext.aggregates}} : '' }}</span>
            </div>
        </div>
        <div v-else>
            <BasePicker v-if="editMode" searchApiPath="{{#toURL ../referenceClass}}{{/toURL}}{{#../boundedContext.aggregates}}{{#outgoingRelations}}{{#target}}{{#attached 'View' this}}/search/findBy{{namePascalCase}}{{/attached}}{{/target}}{{/outgoingRelations}}{{/../boundedContext.aggregates}}" searchParameterName="{{#../boundedContext.aggregates}}{{#outgoingRelations}}{{#target}}{{#attached 'BoundedContext' this}}{{#aggregates}}{{#if (compareName name ../../../../../../referenceClass)}}{{#getNameField aggregateRoot.fieldDescriptors}}{{/getNameField}}{{/if}}{{/aggregates}}{{/attached}}{{/target}}{{/outgoingRelations}}{{/../boundedContext.aggregates}}"  idField="{{#../boundedContext.aggregates}}{{#outgoingRelations}}{{#target}}{{#attached 'BoundedContext' this}}{{#aggregates}}{{#if (compareName name ../../../../../../referenceClass)}}{{#getKey aggregateRoot.fieldDescriptors}}{{/getKey}}{{/if}}{{/aggregates}}{{/attached}}{{/target}}{{/outgoingRelations}}{{/../boundedContext.aggregates}}" nameField="{{#../boundedContext.aggregates}}{{#outgoingRelations}}{{#target}}{{#attached 'BoundedContext' this}}{{#aggregates}}{{#if (compareName name ../../../../../../referenceClass)}}{{#getNameField aggregateRoot.fieldDescriptors}}{{/getNameField}}{{/if}}{{/aggregates}}{{/attached}}{{/target}}{{/outgoingRelations}}{{/../boundedContext.aggregates}}" path="{{#toURL ../referenceClass}}{{/toURL}}" label="{{#ifNotNull displayName ../namePascalCase}}{{/ifNotNull}}" v-model="value" @selected="pick" :editMode="editMode" />
            <div v-else style="height:100%">
                <span>\{{ value && value.{{#../boundedContext.aggregates}}{{#outgoingRelations}}{{#target}}{{#attached 'BoundedContext' this}}{{#aggregates}}{{#if (compareName name ../../../../../../referenceClass)}}{{#getNameField aggregateRoot.fieldDescriptors}}{{/getNameField}}{{/if}}{{/aggregates}}{{/attached}}{{/target}}{{/outgoingRelations}}{{/../boundedContext.aggregates}} ? value.{{#../boundedContext.aggregates}}{{#outgoingRelations}}{{#target}}{{#attached 'BoundedContext' this}}{{#aggregates}}{{#if (compareName name ../../../../../../referenceClass)}}{{#getNameField aggregateRoot.fieldDescriptors}}{{/getNameField}}{{/if}}{{/aggregates}}{{/attached}}{{/target}}{{/outgoingRelations}}{{/../boundedContext.aggregates}} : '' }}</span>
            </div>
        </div>
    </div>
    {{/referenceClass}}

    {{^referenceClass}}
    <div>
        <div class="detail-title">
        {{namePascalCase}}
        </div>
        <v-col>
        {{#fieldDescriptors}}
        {{#if (isNotId nameCamelCase)}}
        {{#if (isPrimitive className)}}
            <{{getPrimitiveType className}} label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}" v-model="value.{{nameCamelCase}}" :editMode="editMode"/>
        {{else}}
        {{#checkEntityMember className}}
            <{{className}} offline label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}" v-model="value.{{nameCamelCase}}" :editMode="editMode" @change="change"/>
        {{/checkEntityMember}}
        {{#checkListOfEntityMember className}}
            <{{getEntityClassNameOfList className}}List offline label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}" v-model="value.{{nameCamelCase}}" :editMode="editMode" @change="change"/>
        {{/checkListOfEntityMember}}
        {{/if}}
        {{/if}}
        {{/fieldDescriptors}}
        </v-col>

        <v-card-actions v-if="inList">
            <slot name="actions"></slot>
        </v-card-actions>
    </div>
    {{/referenceClass}}
</template>

<script>
import BaseEntity from './base-ui/BaseEntity.vue'
import BasePicker from './base-ui/BasePicker.vue'

export default {
    name: '{{namePascalCase}}',
    mixins:[BaseEntity],
    components:{
        BasePicker
    },
    data: () => ({
        path: '{{#if referenceClass}}{{#toURL referenceClass}}{{/toURL}}{{else}}{{namePlural}}{{/if}}',
    }),
    props: {
    },
    watch: {
        value(val){
            this.value = val;
            this.change();
        },
    },
    async created() {
        if (Array.isArray(this.modelValue)) {
            this.value = await Promise.all(this.modelValue.map(async (item) => {
                if (item && item.id !== undefined) {
                    return await this.repository.findById(item.id);
                }
                return item;
            }));
        } else {
            this.value = this.modelValue;
            if (this.value && this.value.id !== undefined) {
                this.value = await this.repository.findById(this.value.id);
            }
        }
    },
    methods: {
        pick(val){
            this.value = val;
            this.change();
        },
    }
}
</script>


<function>
    var me = this;
    me.contexts.views = [];
    if(this.boundedContext.readModels)
    this.boundedContext.readModels.forEach(view=>{
        if(view.aggregate == me && view.dataProjection=="query-for-aggregate"){
            me.contexts.views.push(view);
        }
    })

    window.$HandleBars.registerHelper('checkDefaultVO', function (name) {
        if(name == 'Address' || name == 'Photo' || name == 'Email' || name == 'EventViewer' || name == 'File' || name == 'Money' || name == 'Payment' || name == 'Rating' || name == 'User' || name == 'Weather') {
            return true;
        } else {
            return false;
        }
    })

    window.$HandleBars.registerHelper('compareName', function (aggName, referenceName) {
        if(aggName == referenceName){
            return true;
        }else{
            return false;
        }
    });
    window.$HandleBars.registerHelper('getAggName', function (aggName, referenceName) {
        let filterAggName = String(aggName.replace("Query", ""));
        if(filterAggName == referenceName){
            return aggName
        }
    });
    window.$HandleBars.registerHelper('compareName', function (aggName, referenceName) {
        if(aggName == referenceName){
            return true
        }
    });
    window.$HandleBars.registerHelper('getKey', function (fieldDescriptors) {
        for(var i = 0; i < fieldDescriptors.length; i ++ ){
            if(fieldDescriptors[i] && fieldDescriptors[i].isKey == true){
                return fieldDescriptors[i].name
            }
        }
    });
    window.$HandleBars.registerHelper('getNameField', function (fieldDescriptors) {
        for(var i = 0; i < fieldDescriptors.length; i ++ ){
            if(fieldDescriptors[i] && fieldDescriptors[i].isName == true){
                return fieldDescriptors[i].nameCamelCase
            }else if(fieldDescriptors[i].isName == false && fieldDescriptors[i].isKey == false){
                return fieldDescriptors[i].nameCamelCase
            }
        }
    });
    window.$HandleBars.registerHelper('toURL', function (className) {

        var pluralize = function(value, revert){

            var plural = {
                '(quiz)$'               : "$1zes",
                '^(ox)$'                : "$1en",
                '([m|l])ouse$'          : "$1ice",
                '(matr|vert|ind)ix|ex$' : "$1ices",
                '(x|ch|ss|sh)$'         : "$1es",
                '([^aeiouy]|qu)y$'      : "$1ies",
                '(hive)$'               : "$1s",
                '(?:([^f])fe|([lr])f)$' : "$1$2ves",
                '(shea|lea|loa|thie)f$' : "$1ves",
                'sis$'                  : "ses",
                '([ti])um$'             : "$1a",
                '(tomat|potat|ech|her|vet)o$': "$1oes",
                '(bu)s$'                : "$1ses",
                '(alias)$'              : "$1es",
                '(octop)us$'            : "$1i",
                '(ax|test)is$'          : "$1es",
                '(us)$'                 : "$1es",
                '([^s]+)$'              : "$1s"
            };

            var singular = {
                '(quiz)zes$'             : "$1",
                '(matr)ices$'            : "$1ix",
                '(vert|ind)ices$'        : "$1ex",
                '^(ox)en$'               : "$1",
                '(alias)es$'             : "$1",
                '(octop|vir)i$'          : "$1us",
                '(cris|ax|test)es$'      : "$1is",
                '(shoe)s$'               : "$1",
                '(o)es$'                 : "$1",
                '(bus)es$'               : "$1",
                '([m|l])ice$'            : "$1ouse",
                '(x|ch|ss|sh)es$'        : "$1",
                '(m)ovies$'              : "$1ovie",
                '(s)eries$'              : "$1eries",
                '([^aeiouy]|qu)ies$'     : "$1y",
                '([lr])ves$'             : "$1f",
                '(tive)s$'               : "$1",
                '(hive)s$'               : "$1",
                '(li|wi|kni)ves$'        : "$1fe",
                '(shea|loa|lea|thie)ves$': "$1f",
                '(^analy)ses$'           : "$1sis",
                '((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)ses$': "$1$2sis",        
                '([ti])a$'               : "$1um",
                '(n)ews$'                : "$1ews",
                '(h|bl)ouses$'           : "$1ouse",
                '(corpse)s$'             : "$1",
                '(us)es$'                : "$1",
                's$'                     : ""
            };

            var irregular = {
                'move'   : 'moves',
                'foot'   : 'feet',
                'goose'  : 'geese',
                'sex'    : 'sexes',
                'child'  : 'children',
                'man'    : 'men',
                'tooth'  : 'teeth',
                'person' : 'people',
                'index'  : 'indexes'
            };

            var uncountable = [
                'sheep', 
                'fish',
                'deer',
                'moose',
                'series',
                'species',
                'money',
                'rice',
                'information',
                'equipment'
            ];

            // save some time in the case that singular and plural are the same
            //console.log("value = " + value)
            if(uncountable.indexOf(value.toLowerCase()) >= 0)
            return this;

            // check for irregular forms
            for(var word in irregular){

                if(revert){
                    var pattern = new RegExp(irregular[word]+'$', 'i');
                    var replace = word;
                } else{ var pattern = new RegExp(word+'$', 'i');
                    var replace = irregular[word];
                }
                if(pattern.test(value))
                    return value.replace(pattern, replace);
            }

            if(revert) var array = singular;
                else  var array = plural;

            // check for matches using regular expressions
            for(var reg in array){

                var pattern = new RegExp(reg, 'i');

                if(pattern.test(value))
                    return value.replace(pattern, array[reg]);
            }

            return value;
        }
        return pluralize(className.toLowerCase())
    })
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

    window.$HandleBars.registerHelper('isNotId', function (className) {
        return (className != 'id')
    })

    window.$HandleBars.registerHelper('isPrimitive', function (className) {
        if(className.includes("String") || className.includes("Integer") ||
                className.includes("Long") || className.includes("Double") || 
                className.includes("Float") || className.includes("Boolean") || 
                className.includes("Date") || className.includes("BigDecimal")
        ) {
            return true;
        } else {
            return false;
        }
    })

    window.$HandleBars.registerHelper('getPrimitiveType', function (className, options) {
        if(className.includes("String")) {
            if(this.isLob) {
                return "LargeObject";
            } else {
                return "String";
            }
        } else if(className.includes("Integer") || className.includes("Long") || 
                className.includes("Double") || className.includes("Float") || 
                className.includes("int") || className.includes("BigDecimal")
        ) {
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
        } else if(className.endsWith("Integer") || className.endsWith("Long") || 
                className.endsWith("Double") || className.endsWith("Float") || 
                className.includes("int") || className.includes("BigDecimal")
        ) {
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