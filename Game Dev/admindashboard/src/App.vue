<template>
  <v-app>
    <main>
      <transition mode="out-in">
        <router-view />
      </transition>
    </main>
  </v-app>
</template>

<script>
export default {
  name: "App",
};
</script>

<style scoped>
@import "./styles/styles.css";
</style>