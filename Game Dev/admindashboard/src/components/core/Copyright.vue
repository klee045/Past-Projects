<template>
  <span>
    <v-col>
      <v-list-item @click="logout">
        <v-list-item-avatar class="nav-drawer-icon">
          <v-icon>mdi-logout</v-icon>
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title>Log Out</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-col>
    <v-divider />
    <v-container fluid class="pb-0">
      <v-col class="ml-2 pb-0">
        <p class="drawer-copyright">Contact us at <u>admin@gmail.com</u></p>
        <p class="overline">Copyright 2021 SSAD-Admin</p>
      </v-col>
    </v-container>
  </span>
</template>

<script>
export default {
  methods: {
    logout() {
      localStorage.clear();
      this.$router.push("/");
    },
  },
};
</script>
