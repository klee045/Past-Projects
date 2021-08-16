<template>
  <v-container fluid>
    <v-row justify="center">
      <v-col class="py-0">
        <v-card class="pa-5 rounded-lg" elevation="1">
          <v-card
            class="pa-5 mt-5 rounded-lg align-center"
            height="700px"
            elevation="0"
          >
            <v-card-actions>
              <v-col>
                <v-row class="justify-center">
                  <v-img class="my-5" max-width="450" :src="filesImage"></v-img>
                </v-row>
                <v-row class="justify-center">
                  <p>Analytics will be here shortly...</p>
                </v-row>
              </v-col>
            </v-card-actions>
          </v-card>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
export default {
  name: "HomePage",
  data: () => ({
    filesImage: require("../../assets/files.svg"),
  }),
};
</script>