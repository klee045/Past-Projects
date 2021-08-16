<template>
  <v-container fluid class="pa-0">
    <v-dialog v-model="getUserDialog" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline">{{ getUserTitle }}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  label="Username"
                  v-model="userItem.username"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  label="Email"
                  v-model="userItem.email"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  label="Role"
                  v-model="userItem.role"
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <multiselect
                  v-model="selectedCourses"
                  placeholder="Courses"
                  :options="
                    courseList.map((o) => {
                      return { name: o.course_name, _id: o._id };
                    })
                  "
                  :multiple="true"
                  :taggable="true"
                  label="name"
                  track-by="_id"
                ></multiselect>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="closeUserDialog">
            Close
          </v-btn>
          <v-btn color="blue darken-1" text @click="saveUser"> Save </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>
<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>

<script>
export default {
  data: () => ({
    userItem: {},
    courseList: [],
    selectedCourses: [],
  }),
  computed: {
    getUserDialog() {
      return this.$store.getters["user/getUserDialog"];
    },
    getUserTitle() {
      return this.$store.getters["user/getUserTitle"];
    },
  },

  watch: {
    getUserDialog: async function () {
      // Fetch Course List
      if (this.getUserDialog == true) {
        this.courseList = await this.$store.dispatch("course/GETCOURSELIST");
      }
      //Display question data if it's edit question
      if (this.getUserDialog == true && this.getUserTitle == "Edit User") {
        this.userItem = this.$store.getters["user/getUserItem"];
        var filteredCourses = this.courseList.filter((x) =>
          this.userItem.courses.includes(x._id)
        );
        this.selectedCourses = filteredCourses.map((o) => {
          return { name: o.course_name, _id: o._id };
        });
      }
    },
    deep: true,
  },
  methods: {
    saveUser() {
      //Map it back to array of id
      this.userItem.courses = this.selectedCourses.map((a) => a._id);
      if (this.getUserTitle == "New User") {
        //Set Default password
        this.userItem.password = "Password123!";
        this.$store.dispatch("user/ADDUSER", this.userItem);
      } else {
        this.$store.dispatch("user/EDITUSER", this.userItem);
      }
      this.resetUserForm();
      this.closeUserDialog();
    },
    closeUserDialog() {
      this.resetUserForm();
      this.$store.dispatch("user/CLOSEUSERDIALOG");
    },
    resetUserForm() {
      this.userItem = {};
      this.selectedCourses = [];
    },
  },
};
</script>