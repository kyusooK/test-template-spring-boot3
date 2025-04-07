forEach: Enumeration
fileName: {{pascalCase name}}.vue
---
<template>
    <div style="margin: -10px -15px 0 -15px;">
        <v-card-text>
            <div class="label-title">{{label}}</div>
            <div v-if="editMode">
                <v-select
                    v-model="value"
                    :label="label"
                    :items="selections"
                    solo
                ></v-select>
            </div>
            <div v-else>
                {{pascalCase name}} : \{{ value }}
            </div>
        </v-card-text>
    </div>
</template>

<script>
import BaseEntity from './base-ui/BaseEntity.vue';

export default {
    name: '{{pascalCase name}}',
    mixins:[BaseEntity],
    components:{},
    props: {
        modelValue: Object,
        label: String
    },
    data: () => ({
        selections : [{{#items}}"{{value}}",{{/items}}],
    }),
    async created() {
        this.value = this.modelValue
        if(!this.value) {
            this.value = [];
        }
    },
    watch: {
        value:{
            handler: function(){
                this.$emit("update:modelValue", this.value)
            }
        }
    },
    methods: {
    }
}
</script>