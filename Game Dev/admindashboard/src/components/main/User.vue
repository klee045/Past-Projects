<template>
  <v-container fluid>
    <v-row justify="center">
      <v-col class="py-0">
        <v-card class="pa-5 rounded-lg" elevation="1">
          <v-card class="pa-5 rounded-lg" elevation="0">
            <v-card-title
              style="font-size: 16px !important"
              class="overline pa-0"
            >
              Manage User
            </v-card-title>
            <v-card-text>
              <v-row wrap class="mt-2">
                <v-flex xs6>
                  <v-text-field
                    label="Search User"
                    v-model="search"
                    append-icon="mdi-magnify"
                    single-line
                    hide-details
                  ></v-text-field>
                </v-flex>
                <v-flex xs6 class="text-right">
                  <v-btn large color="black" @click="addUser" depressed>
                    <span class="white--text"> New User </span>
                  </v-btn>
                </v-flex>
              </v-row>
            </v-card-text>

            <!-- Start of Datatable -->
            <v-data-table
              :headers="headers"
              :search="search"
              :items="user"
              :options.sync="pagination"
              :loading="loading"
              :server-items-length="pagination.totalItems"
              :footer-props="footerProps"
              class="elevation-0 pa-2"
            >
              <template v-slot:item="{ item }">
                <tr>
                  <td>{{ item.username }}</td>
                  <td>{{ item.email }}</td>
                  <td>{{ item.role }}</td>
                  <!--<td>
                    <v-flex v-if="item.courses">
                      <v-chip
                        class="ml-2"
                        outlined
                        color="secondary"
                        v-for="tag in item.courses"
                        :key="tag"
                      >
                        {{ tag }}
                      </v-chip>
                    </v-flex>
                  </td>-->
                  <td>
                    <v-row class="justify-center">
                      <v-btn
                        class="mr-2"
                        color="#E4E6EB"
                        depressed
                        @click="editUser(item)"
                      >
                        <v-icon size="20" depressed color="black"
                          >mdi-pencil</v-icon
                        >
                      </v-btn>
                      <v-btn
                        color="#E4E6EB"
                        depressed
                        @click="deleteUser(item._id)"
                      >
                        <v-icon size="20" depressed color="black"
                          >mdi-trash-can</v-icon
                        >
                      </v-btn>
                    </v-row>
                  </td>
                </tr>
              </template>

              <template slot="no-data">
                <v-card-actions>
                  <v-col>
                    <v-row class="justify-center">
                      <v-img
                        class="my-5"
                        max-width="350"
                        :src="filesImage"
                      ></v-img>
                    </v-row>
                    <v-row class="justify-center">
                      <p>No data available...</p>
                    </v-row>
                  </v-col>
                </v-card-actions>
              </template>
            </v-data-table>
            <!-- End of Datatable -->

            <!-- Add Edit Modal -->
            <AddEditUser />
            <!-- End of Add Edit Modal -->
          </v-card>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";
import AddEditUser from "../form/AddEditUser";
export default {
  name: "UserPage",
  components: {
    AddEditUser,
  },
  data: () => ({
    loading: false,
    filesImage: require("../../assets/files.svg"),
    footerProps: { "items-per-page-options": [5, 15, 30] },
    headers: [
      { text: "Username", value: "username", width: "15%" },
      { text: "Email", value: "email", width: "20%" },
      { text: "Role", value: "role", width: "20%" },
      //{ text: "Courses", value: "courses", width: "25%" },
      { text: "Actions", width: "20%", align: "center" },
    ],
    pagination: {},
    userData: {},
    courseList: [],
    search: "",
    userTitle: "New User",
  }),
  computed: {
    ...mapGetters({
      user: "user/getUsers",
    }),
    getUsers() {
      return this.$store.getters["user/getUsers"];
    },
  },
  watch: {
    pagination: {
      handler() {
        this.getUserList();
      },
      deep: true,
    },
  },
  mounted() {
    this.getUserList();
  },
  methods: {
    async getUserList() {
      this.loading = true;
      await this.$store.dispatch("user/GETALLUSERS");
      this.loading = false;
    },

    addUser() {
      this.$store.dispatch("user/SETUSERTITLE", "New User");
      this.$store.dispatch("user/OPENUSERDIALOG");
      this.$store.dispatch("user/CLEARUSERITEM");
    },

    editUser(userData) {
      this.$store.dispatch("user/SETUSERTITLE", "Edit User");
      this.$store.dispatch("user/OPENUSERDIALOG");
      this.$store.dispatch("user/SETUSERITEM", userData);
      this.userData = userData;
    },

    deleteUser(userId) {
      this.$store.dispatch("user/DELETEUSER", userId);
    },
  },
};
</script>
<style scoped>
.truncate {
  max-width: 1px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>