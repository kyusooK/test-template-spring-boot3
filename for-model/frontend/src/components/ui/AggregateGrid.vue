forEach: Aggregate
fileName: {{namePascalCase}}Grid.vue
---
<template>
    <v-container>
        <v-snackbar
            v-model="snackbar.status"
            :timeout="snackbar.timeout"
            :color="snackbar.color"
        >
            {{ snackbar.text }}
            <v-btn style="margin-left: 80px;" text @click="snackbar.status = false">
                Close
            </v-btn>
        </v-snackbar>
        <div class="panel">
            <div class="gs-bundle-of-buttons" style="max-height:10vh;">
                <v-btn @click="addNewRow" @class="contrast-primary-text" small color="primary">
                    <v-icon small style="margin-left: -5px;">mdi-plus</v-icon>등록
                </v-btn>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="openEditDialog()" class="contrast-primary-text" small color="primary">
                    <v-icon small>mdi-pencil</v-icon>수정
                </v-btn>
                {{#commands}}
                {{^isRestRepository}}
                {{#if (isCommandMethod controllerInfo "POST")}}
                <v-btn style="margin-left: 5px;" {{#if fieldDescriptors}}@click="{{nameCamelCase}}Dialog = true"{{else}}@click="{{nameCamelCase}}"{{/if}} class="contrast-primary-text" small color="primary" {{#if (attachedActorName actorName)}}:disabled="!hasRole('{{actorName}}')"{{/if}}>
                    <v-icon small>mdi-minus-circle-outline</v-icon>{{#ifNotNull displayName name}}{{/ifNotNull}}
                </v-btn>
                {{ else }}
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" {{#if fieldDescriptors}}@click="{{nameCamelCase}}Dialog = true"{{else}}@click="{{nameCamelCase}}"{{/if}} class="contrast-primary-text" small color="primary" {{#if (attachedActorName actorName)}}:disabled="!hasRole('{{actorName}}')"{{/if}}>
                    <v-icon small>mdi-minus-circle-outline</v-icon>{{#ifNotNull displayName name}}{{/ifNotNull}}
                </v-btn>
                {{/if}}
                {{#if fieldDescriptors}}
                <v-dialog v-model="{{nameCamelCase}}Dialog" width="500">
                    <{{namePascalCase}}
                        @closeDialog="{{nameCamelCase}}Dialog = false"
                        @{{nameCamelCase}}="{{nameCamelCase}}"
                    ></{{namePascalCase}}>
                </v-dialog>
                {{/if}}
                {{/isRestRepository}}
                {{/commands}}
            </div>
            {{#attached 'View' this}}
            <{{namePascalCase}} @search="search" style="margin-bottom: 10px; background-color: #ffffff;"></{{namePascalCase}}>
            {{/attached}}
            <div class="mb-5 text-lg font-bold"></div>
            <div class="table-responsive">
                <v-table>
                    <thead>
                        <tr>
                        <th>Id</th>
                        {{#aggregateRoot.fieldDescriptors}}
                        {{#unless isKey}}
                        {{#if (isNotId nameCamelCase)}}
                        <th>{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}</th>
                        {{/if}}
                        {{/unless}}
                        {{/aggregateRoot.fieldDescriptors}}
                        {{#outgoingRelations}}
                        {{#target}}
                        <th>{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}</th>
                        {{/target}}
                        {{/outgoingRelations}}
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(val, idx) in value" 
                            @click="changeSelectedRow(val)"
                            :key="val"  
                            :style="val === selectedRow ? 'background-color: rgb(var(--v-theme-primary), 0.2) !important;':''"
                        >
                            <td class="font-semibold">\{{ idx + 1 }}</td>
                            {{#aggregateRoot.fieldDescriptors}}
                            {{#unless isKey}}
                            {{#if (isNotId nameCamelCase)}}
                            {{#if isVO}}
                            {{#checkVO className}}
                            <td class="whitespace-nowrap" label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}">
                                <{{className}} :editMode="false" :inList="true" v-model="val.{{nameCamelCase}}"></{{className}}>
                            </td>
                            {{/checkVO}}
                            {{else}}
                            {{#if isList}}
                            <td class="whitespace-nowrap" label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}">
                                <span v-for="(name, index) in val.{{nameCamelCase}}" :key="index">
                                    \{{ name }}<br>
                                </span>
                            </td>
                            {{ else }}
                            <td class="whitespace-nowrap" label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}">{{#getTableData nameCamelCase}}{{/getTableData}}</td>
                            {{/if}}
                            {{/if}}
                            {{/if}}
                            {{/unless}}
                            {{/aggregateRoot.fieldDescriptors}}
                            {{#outgoingRelations}}
                            {{#target}}
                            <td class="whitespace-nowrap" label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}">
                                <{{namePascalCase}}Id :editMode="editMode" v-model="val.{{nameCamelCase}}Id"></{{namePascalCase}}Id>
                            </td>
                            {{/target}}
                            {{/outgoingRelations}}
                            <v-row class="ma-0 pa-4 align-center">
                                <v-spacer></v-spacer>
                                <Icon style="cursor: pointer;" icon="mi:delete" @click="deleteRow(val)" />
                            </v-row>
                        </tr>
                    </tbody>
                </v-table>
            </div>
            {{#aggregateRoot.fieldDescriptors}}
            {{#if isList}}
            <{{getEntityFromList className}}DetailGrid style="margin-top: 20px;" label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}" offline v-if="selectedRow" v-model="selectedRow.{{nameCamelCase}}" :selectedRow="selectedRow"/>
            {{/if}}
            {{/aggregateRoot.fieldDescriptors}}
        </div>
        <v-col>
            <v-dialog
                v-model="openDialog"
                transition="dialog-bottom-transition"
                width="35%"
            >
                <v-card>
                    <v-toolbar
                        color="primary"
                        class="elevation-0 pa-4"
                        height="50px"
                    >
                        <div style="color:white; font-size:17px; font-weight:700;">{{namePascalCase}} 등록</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <{{namePascalCase}} :offline="offline"
                            :isNew="!value.idx"
                            :editMode="true"
                            :inList="false"
                            v-model="newValue"
                            @add="append"
                        />
                    </v-card-text>
                </v-card>
            </v-dialog>
            <v-dialog
                v-model="editDialog"
                transition="dialog-bottom-transition"
                width="35%"
            >
                <v-card>
                    <v-toolbar
                        color="primary"
                        class="elevation-0 pa-4"
                        height="50px"
                    >
                        <div style="color:white; font-size:17px; font-weight:700;">{{namePascalCase}} 수정</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <div>
                            {{#aggregateRoot.fieldDescriptors}}
                            {{#if (isNotId nameCamelCase)}}
                            {{#if (isPrimitive className)}}
                            <{{getPrimitiveType className}} label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}" v-model="selectedRow.{{nameCamelCase}}" :editMode="true"/>
                            {{else}}
                            {{/if}}
                            {{/if}}
                            {{/aggregateRoot.fieldDescriptors}}
                            {{#aggregateRoot.fieldDescriptors}}
                            {{#unless isKey}}
                            {{#if (isNotId nameCamelCase)}}
                            {{#if isVO}}
                            {{#checkVO className}}
                            <{{className}} offline label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}" v-model="selectedRow.{{nameCamelCase}}" :editMode="true"/>
                            {{/checkVO}}
                            {{/if}}
                            {{/if}}
                            {{/unless}}
                            {{#if isList}}
                            {{else}}
                            {{#if (isNotId nameCamelCase)}}
                            {{#if (isPrimitive className)}}
                            {{else}}
                            {{#checkEntityMember className}}
                            {{#if (getPrimitiveType className)}}
                            <{{getPrimitiveType className}} offline label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}" v-model="selectedRow.{{nameCamelCase}}" :editMode="true"/>
                            {{else}}
                            <{{className}} offline label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}" v-model="selectedRow.{{nameCamelCase}}" :editMode="true"/>
                            {{/if}}
                            {{/checkEntityMember}}
                            {{/if}}
                            {{/if}}
                            {{/if}}
                            {{/aggregateRoot.fieldDescriptors}}
                            {{#aggregateRoot.fieldDescriptors}}
                            {{#if isList}}
                            <{{getEntityFromList className}}DetailGrid label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}" offline v-model="selectedRow.{{nameCamelCase}}" :editMode="true"/>
                            {{/if}}
                            {{/aggregateRoot.fieldDescriptors}}
                            <v-divider class="border-opacity-100 my-divider"></v-divider>
                            <v-layout row justify-end>
                                <v-btn
                                    width="64px"
                                    color="primary"
                                    @click="save"
                                >
                                    수정
                                </v-btn>
                            </v-layout>
                        </div>
                    </v-card-text>
                </v-card>
            </v-dialog>
        </v-col>
    </v-container>
</template>

<script>
import { ref } from 'vue';
import { useTheme } from 'vuetify';
import BaseGrid from '../base-ui/BaseGrid.vue'


export default {
    name: '{{nameCamelCase}}Grid',
    mixins:[BaseGrid],
    components:{
    },
    data: () => ({
        path: '{{namePlural}}',
        {{#commands}}
        {{^isRestRepository}}
        {{#if fieldDescriptors}}
        {{nameCamelCase}}Dialog: false,
        {{/if}}
        {{/isRestRepository}}
        {{/commands}}
    }),
    watch: {
    },
    methods:{
        {{#commands}}
        {{^isRestRepository}}
        async {{nameCamelCase}}({{#if fieldDescriptors}}params{{/if}}){
            try{
                var path = "{{nameCamelCase}}".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, {{#if fieldDescriptors}}params{{else}}null{{/if}})
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','{{#ifNotNull displayname name}}{{/ifNotNull}} 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                {{#if fieldDescriptors}}
                this.{{nameCamelCase}}Dialog = false
                {{/if}}
            }catch(e){
                console.log(e)
            }
        },
        {{/isRestRepository}}
        {{/commands}}
    }
}

</script>
<function>

var me = this;
var importList = []
var componentList = []

if (me && me.attached) {
    var postCmd = me.attached.find(ele => ele._type.includes("Command") && (ele.isExtendedVerb==true && ele.controllerInfo.method=="POST") || (ele.isRestRepository==true && ele.restRepositoryInfo.method=="POST"));
    var putCmd = me.attached.find(ele => ele._type.includes("Command") && (ele.isExtendedVerb==true && ele.controllerInfo.method=="PUT") || (ele.isRestRepository==true && ele.restRepositoryInfo.method=="PUT"));
    var deleteCmd = me.attached.find(ele => ele._type.includes("Command") && (ele.isExtendedVerb==true && ele.controllerInfo.method=="DELETE") || (ele.isRestRepository==true && ele.restRepositoryInfo.method=="DELETE"));

    me.contexts.actors = {};

    if (postCmd && postCmd.attached) {
        var postActor = postCmd.attached.find(ele => ele._type.includes("Actor"));
        if (postActor) {
            me.contexts.actors.postActor = postActor;
        }
    }
    
    if (putCmd && putCmd.attached) {
        var putActor = putCmd.attached.find(ele => ele._type.includes("Actor"));
        if (putActor) {
            me.contexts.actors.putActor = putActor;
        }
    }
    
    if (deleteCmd && deleteCmd.attached) {
        var deleteActor = deleteCmd.attached.find(ele => ele._type.includes("Actor"));
        if (deleteActor) {
            me.contexts.actors.deleteActor = deleteActor;
        }
    }
}

window.$HandleBars.registerHelper('isCommandMethod', function(controllerInfo, ...methods) {
    if (!controllerInfo || !controllerInfo.method) {
        return false;
    }
    return methods.includes(controllerInfo.method);
});

window.$HandleBars.registerHelper('getPickerName', function (fieldDescriptors) {
    for(var i = 0; i < fieldDescriptors.length; i ++ ){
        if(fieldDescriptors[i] && fieldDescriptors[i].isName == true){
            return fieldDescriptors[i].nameCamelCase
        }else{
            return fieldDescriptors[0].nameCamelCase
        }
    }
})
window.$HandleBars.registerHelper('getNameField', function (fieldDescriptors) {
    for(var i = 0; i < fieldDescriptors.length; i ++ ){
        if(fieldDescriptors[i] && fieldDescriptors[i].isName == true){
            return fieldDescriptors[i].nameCamelCase
        }else if(fieldDescriptors[i].isName == false && fieldDescriptors[i].isKey == false){
            return fieldDescriptors[i].nameCamelCase
        }
    }
});
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
window.$HandleBars.registerHelper('checkVO', function (className, options) {
    if(className.endsWith("Address") || className.endsWith("Photo") || className.endsWith("User") || className.endsWith("Email") 
            || className.endsWith("Payment") || className.endsWith("Money") || className.endsWith("Weather") || className.endsWith("Rating") ){
        return options.fn(this);
    }
})
window.$HandleBars.registerHelper('getEntityFromList', function (className) {
    if(className.includes("List<") && className.includes(">")) {
        return className.replace("List<", "").replace(">", "");
    }
    return className;
})

window.$HandleBars.registerHelper('ifNotNull', function (displayName, name) {
    if(displayName){
        return displayName;
    }else{
        return name;
    }
})

window.$HandleBars.registerHelper('isNotId', function (className) {
    return (className != 'id')
})
window.$HandleBars.registerHelper('attachedActorName', function (actorName, options) {
    try {
        if(actorName) {
            return true;
        } else {
            return false;
        }
    } catch(e) {
        console.log(e)
    }
})
window.$HandleBars.registerHelper('getTableData', function (nameCamelCase) {
    if(nameCamelCase){
        var tdVal = '{{ val.' + nameCamelCase + ' }}'
        return tdVal
    }
})
</function>
