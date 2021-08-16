<template>
  <v-container fluid class="pa-0">
    <v-dialog v-model="getAchievementDialog" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline">{{ getAchievementTitle }}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  label="Achievement Name"
                  v-model="achievementItem.achievement_desc"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="closeAchievementDialog">
            Close
          </v-btn>
          <v-btn color="blue darken-1" text @click="saveAchievement">
            Save
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    achievementItem: {},
  }),
  computed: {
    getAchievementDialog() {
      return this.$store.getters["achievement/getAchievementDialog"];
    },
    getAchievementTitle() {
      return this.$store.getters["achievement/getAchievementTitle"];
    },
  },

  watch: {
    getAchievementDialog: function () {
      //Display question data if it's edit question
      if (
        this.getAchievementDialog == true &&
        this.getAchievementTitle == "Edit Achievement"
      ) {
        this.achievementItem = this.$store.getters[
          "achievement/getAchievementItem"
        ];
      }
    },
    deep: true,
  },
  methods: {
    saveAchievement() {
      if (this.getAchievementTitle == "New Achievement") {
        this.$store.dispatch(
          "achievement/ADDACHIEVEMENT",
          this.achievementItem
        );
      } else {
        this.$store.dispatch(
          "achievement/EDITACHIEVEMENT",
          this.achievementItem
        );
      }
      this.resetAchievementForm();
      this.closeAchievementDialog();
    },
    closeAchievementDialog() {
      this.resetAchievementForm();
      this.$store.dispatch("achievement/CLOSEACHIEVEMENTDIALOG");
    },
    resetAchievementForm() {
      this.achievementItem = {};
    },
  },
};
</script>