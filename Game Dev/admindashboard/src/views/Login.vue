<template>
  <v-app id="inspire">
    <v-main>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4>
            <v-card class="elevation-12">
              <v-toolbar dark color="white">
                <v-toolbar-title>
                  <v-row style="height: 70px" align="center">
                    <v-img
                      class="mx-auto ml-5"
                      src="../assets/logo.svg"
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
                </v-toolbar-title>
              </v-toolbar>
              <v-card-text>
                <v-form>
                  <v-text-field
                    v-model="user.email"
                    prepend-icon="mdi-account"
                    name="login"
                    label="Email"
                    type="text"
                  ></v-text-field>
                  <v-text-field
                    v-model="user.password"
                    prepend-icon="mdi-lock"
                    name="password"
                    label="Password"
                    type="password"
                    @keyup.enter="login"
                  ></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" @keyup.enter="login" @click="login"
                  >Login</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      user: {},
    };
  },
  methods: {
    login() {
      this.$store.dispatch("user/LOGIN", this.user).then(() => {
        this.$router.push("/dashboard");
      });
    },
  },
};
</script>
