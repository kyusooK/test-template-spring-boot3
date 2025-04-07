forEach: Model
fileName: Index.vue
---
<template>
  <v-container fluid>
    <v-row>
      <v-card
          v-for="(card, index) in cards"
          :key="index"
          class="mx-auto"
          style="height:300px; width:300px; margin-bottom:20px;"
          outlined
      >
        <v-list-item>
          <v-icon style="margin-top: 30%; margin-left: 37.5%;" color="primary" size="64">mdi-apps</v-icon>
        </v-list-item>
        <v-card-actions>
          <v-btn
              class="mx-auto"
              outlined
              rounded
              :to="card.link"
              style="font-weight:500; font-size:20px; border:solid 2px; max-width:250px; overflow:hidden; margin-top: 10%;"
          >
              \{{ card.text }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'Index',
  data: () => ({
    cards: [
{{#boundedContexts}}
  {{#aggregates}}
      {
          text: "{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}",
          link: "/{{namePlural}}",
      },
  {{/aggregates}}
  {{#views}}
      {
          text: "{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}",
          link: "/{{namePlural}}",
      },
  {{/views}}
{{/boundedContexts}}
    ],
  }),
};
</script>

<function>
window.$HandleBars.registerHelper('ifNotNull', function (displayName, name) {
    if(displayName){
        return displayName;
    }else{
        return name;
    }
})
</function>