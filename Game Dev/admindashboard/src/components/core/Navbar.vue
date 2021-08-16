<template>
  <v-app-bar clipped-left app color="white" height="100px">
    <router-link class="text-decoration-none" to="/">
      <v-row align="center">
        <v-img
          class="mx-auto ml-5"
          src="../../assets/logo.svg"
          width="50"
          height="50"
          contain
        ></v-img>
        <h2
          class="pa-2 ma-0 overline font-weight-normal black--text"
          style="font-size: 22px !important"
        >
          SSAD-Admin
        </h2>
      </v-row>
    </router-link>
  </v-app-bar>
</template>

<script>
export default {
  name: "NavBar",
};
</script>
