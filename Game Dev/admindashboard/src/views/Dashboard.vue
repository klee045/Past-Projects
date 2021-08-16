<template>
  <!-- add dark mode here -->
  <v-app id="dashboard-view" class="dashboard-background">
    <v-fade-transition mode="out-in">
      <v-container fluid>
        <Sidebar />
        <Navbar />
        <v-main>
          <router-view />
        </v-main>
      </v-container>
    </v-fade-transition>
  </v-app>
</template>



<script>
import Sidebar from "../components/core/Sidebar";
import Navbar from "../components/core/Navbar";
export default {
  name: "Dashboard",
  components: {
    Sidebar,
    Navbar,
  },
};
</script>