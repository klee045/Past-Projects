<template>
  <div>
    <v-navigation-drawer
      clipped
      app
      :permanent="$vuetify.breakpoint.mdAndUp"
      width="320px"
      mobile-breakpoint="991"
    >
      <v-container fluid>
        <v-col class="pt-0 mt-4 ml-3">
          <h3>
            Welcome back, {{ user.replace(/\w/, (c) => c.toUpperCase()) }}! 🥱
          </h3>
        </v-col>

        <v-list>
          <v-list-item-group>
            <v-list-item
              v-for="(link, i) in links"
              :key="i"
              :to="link.to"
              color="#616161"
              class="mt-2"
            >
              <v-list-item-avatar class="nav-drawer-icon">
                <v-icon v-text="link.icon" />
              </v-list-item-avatar>

              <v-list-item-content>
                <v-list-item-title style="color: black" v-text="link.text" />
              </v-list-item-content>
            </v-list-item>
          </v-list-item-group>
          <v-spacer />
        </v-list>
      </v-container>

      <template v-slot:append>
        <Copyright />
      </template>
    </v-navigation-drawer>
  </div>
</template>

<script>
import Copyright from "./Copyright";
export default {
  name: "AppDrawer",
  components: {
    Copyright,
  },
  data() {
    return {
      user: localStorage.getItem("name"),
      links: [
        {
          to: "/dashboard/home",
          icon: "mdi-home",
          text: "Home",
          name: "Home",
        },
        {
          to: "/dashboard/course",
          icon: "mdi-file-document",
          text: "Manage Course",
          name: "Course",
        },
        {
          to: "/dashboard/topic",
          icon: "mdi-comment-processing",
          text: "Manage Topic",
          name: "Topic",
        },
        {
          to: "/dashboard/quiz",
          icon: "mdi-book-open",
          text: "Manage Quiz",
          name: "Quiz",
        },
        {
          to: "/dashboard/question",
          icon: "mdi-clipboard-text",
          text: "Manage Question",
          name: "Question",
        },
        {
          to: "/dashboard/user",
          icon: "mdi-account",
          text: "Manage User",
          name: "User",
        },
        {
          to: "/dashboard/achievement",
          icon: "mdi-account",
          text: "Manage Achievement",
          name: "Achievement",
        },
      ],
    };
  },
};
</script>
