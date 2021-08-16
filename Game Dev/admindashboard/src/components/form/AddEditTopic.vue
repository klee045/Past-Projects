<template>
  <v-container fluid class="pa-0">
    <v-dialog v-model="getTopicDialog" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline">{{ getTopicTitle }}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  label="Topic Name"
                  v-model="topicItem.topic_name"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  label="Topic Description"
                  v-model="topicItem.topic_desc"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-autocomplete
                  class="searchList"
                  v-model="inputQuiz"
                  :items="items"
                  :loading="isLoading"
                  :search-input.sync="search"
                  hide-no-data
                  hide-selected
                  item-value="_id"
                  item-text="quiz_name"
                  label="Add Quiz"
                  placeholder="Start typing to Search"
                  prepend-icon="mdi-database-search"
                  @change="getSelectedValue"
                  return-object
                ></v-autocomplete>
                <v-expand-transition>
                  <v-flex class="pa-2 ma-0">
                    <v-list shaped class="ma-0 pa-0">
                      <v-list-item
                        v-for="(item, i) in selectedQuizIds"
                        :key="i"
                      >
                        <v-list-item-icon>
                          <v-icon>mdi-file</v-icon>
                        </v-list-item-icon>
                        <v-list-item-content>
                          <v-list-item-title>{{
                            getQuizName(item)
                          }}</v-list-item-title>
                        </v-list-item-content>
                        <v-list-item-action>
                          <v-btn
                            fab
                            x-small
                            color="pink"
                            @click="removeSelectedValue(i)"
                          >
                            <v-icon class="white--text">mdi-close</v-icon>
                          </v-btn>
                        </v-list-item-action>
                      </v-list-item>
                    </v-list>
                  </v-flex>
                </v-expand-transition>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="closeTopicDialog">
            Close
          </v-btn>
          <v-btn color="blue darken-1" text @click="saveTopic"> Save </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import axios from "axios";
export default {
  data: () => ({
    topicItem: {},
    isLoading: false,
    inputQuiz: null,
    search: null,
    quizzes: [],
    selectedQuizIds: [],
  }),
  computed: {
    items() {
      return this.quizzes;
    },
    getTopicDialog() {
      return this.$store.getters["topic/getTopicDialog"];
    },
    getTopicTitle() {
      return this.$store.getters["topic/getTopicTitle"];
    },
  },
  created: function () {
    this.fetchQuizList();
  },
  watch: {
    search() {
      this.fetchQuizList();
    },

    getTopicDialog: function () {
      //Display topic data if it's edit topic
      if (this.getTopicDialog == true && this.getTopicTitle == "Edit Topic") {
        this.topicItem = this.$store.getters["topic/getTopicItem"];
        this.selectedQuizIds = this.topicItem.quiz_list;
      }
    },

    deep: true,
  },
  methods: {
    fetchQuizList() {
      // Items have already been loaded
      if (this.items.length > 0) return;
      // Items have already been requested
      if (this.isLoading) return;
      this.isLoading = true;
      // Lazily load input items
      axios
        .get("https://ssad-api.herokuapp.com/api/v1/quiz")
        .then((res) => {
          const { count, quizzes } = res.data;
          this.count = count;
          this.quizzes = quizzes;
        })
        .catch((err) => {
          console.log(err);
        })
        .finally(() => (this.isLoading = false));
    },
    getSelectedValue() {
      const selectedObj = { ...this.inputQuiz };
      if (!this.selectedQuizIds.includes(selectedObj._id)) {
        this.selectedQuizIds.push(selectedObj._id);
      }
      // Workaround using $nexttick
      this.$nextTick(() => {
        this.inputQuiz = "";
      });
    },
    removeSelectedValue(index) {
      this.selectedQuizIds.splice(index, 1);
    },
    getQuizName(quizId) {
      var quizObj = this.quizzes.find((x) => x._id === quizId);
      if (quizObj != undefined) {
        return quizObj.quiz_name;
      }
    },
    saveTopic() {
      this.topicItem.quiz_list = this.selectedQuizIds;
      if (this.getTopicTitle == "New Topic") {
        this.$store.dispatch("topic/ADDTOPIC", this.topicItem);
      } else {
        this.$store.dispatch("topic/EDITTOPIC", this.topicItem);
      }
      this.resetTopicForm();
      this.closeTopicDialog();
    },
    closeTopicDialog() {
      this.resetTopicForm();
      this.$store.dispatch("topic/CLOSETOPICDIALOG");
    },
    resetTopicForm() {
      this.topicItem = {};
      this.selectedQuizIds = [];
    },
  },
};
</script>