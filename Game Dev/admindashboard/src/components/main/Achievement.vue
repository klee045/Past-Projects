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
              Manage Achievement
            </v-card-title>
            <v-card-text>
              <v-row wrap class="mt-2">
                <v-flex xs6>
                  <v-text-field
                    label="Search Achievement"
                    v-model="search"
                    append-icon="mdi-magnify"
                    single-line
                    hide-details
                  ></v-text-field>
                </v-flex>
                <v-flex xs6 class="text-right">
                  <v-btn large color="black" @click="addAchievement" depressed>
                    <span class="white--text"> New Achievement </span>
                  </v-btn>
                </v-flex>
              </v-row>
            </v-card-text>

            <!-- Start of Datatable -->
            <v-data-table
              :headers="headers"
              :search="search"
              :items="achievement"
              :options.sync="pagination"
              :loading="loading"
              :server-items-length="pagination.totalItems"
              :footer-props="footerProps"
              class="elevation-0 pa-2"
            >
              <template v-slot:item="{ item }">
                <tr>
                  <td>{{ item.achievement_desc }}</td>
                  <td>
                    <v-row class="justify-center">
                      <v-btn
                        class="mr-2"
                        color="#E4E6EB"
                        depressed
                        @click="editAchievement(item)"
                      >
                        <v-icon size="20" depressed color="black"
                          >mdi-pencil</v-icon
                        >
                      </v-btn>
                      <v-btn
                        color="#E4E6EB"
                        depressed
                        @click="deleteAchievement(item._id)"
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
            <AddEditAchievement />
            <!-- End of Add Edit Modal -->
          </v-card>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";
import AddEditAchievement from "../form/AddEditAchievement";
export default {
  name: "AchievementPage",
  components: {
    AddEditAchievement,
  },
  data: () => ({
    loading: false,
    filesImage: require("../../assets/files.svg"),
    footerProps: { "items-per-page-options": [5, 15, 30] },
    headers: [
      { text: "Achievement Name", value: "achievement_desc", width: "80%" },
      { text: "Actions", width: "20%", align: "center" },
    ],
    pagination: {},
    achievementData: {},
    search: "",
    achievementTitle: "New Achievement",
  }),
  computed: {
    ...mapGetters({
      achievement: "achievement/getAchievements",
    }),
    getAchievements() {
      return this.$store.getters["achievement/getAchievements"];
    },
  },
  watch: {
    pagination: {
      handler() {
        this.getAchievementList();
      },
      deep: true,
    },
  },
  mounted() {
    this.getAchievementList();
  },
  methods: {
    async getAchievementList() {
      this.loading = true;
      await this.$store.dispatch("achievement/GETALLACHIEVEMENTS");
      this.loading = false;
    },

    addAchievement() {
      this.$store.dispatch(
        "achievement/SETACHIEVEMENTTITLE",
        "New Achievement"
      );
      this.$store.dispatch("achievement/OPENACHIEVEMENTDIALOG");
      this.$store.dispatch("achievement/CLEARACHIEVEMENTITEM");
    },

    editAchievement(achievementData) {
      this.$store.dispatch(
        "achievement/SETACHIEVEMENTTITLE",
        "Edit Achievement"
      );
      this.$store.dispatch("achievement/OPENACHIEVEMENTDIALOG");
      this.$store.dispatch("achievement/SETACHIEVEMENTITEM", achievementData);
      this.achievementData = achievementData;
    },

    deleteAchievement(achievementId) {
      this.$store.dispatch("achievement/DELETEACHIEVEMENT", achievementId);
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