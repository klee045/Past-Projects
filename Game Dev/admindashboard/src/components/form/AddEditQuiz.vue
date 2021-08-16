<template>
  <v-container fluid class="pa-0">
    <v-dialog v-model="getQuizDialog" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline">{{ getQuizTitle }}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  label="Quiz Name"
                  v-model="quizItem.quiz_name"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  label="Quiz Description"
                  v-model="quizItem.quiz_desc"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-autocomplete
                  class="searchList"
                  v-model="inputQuestion"
                  :items="items"
                  :loading="isLoading"
                  :search-input.sync="search"
                  hide-no-data
                  hide-selected
                  item-value="_id"
                  item-text="question_desc"
                  label="Add Questions"
                  placeholder="Start typing to Search"
                  prepend-icon="mdi-database-search"
                  @change="getSelectedValue"
                  return-object
                ></v-autocomplete>
                <v-expand-transition>
                  <v-flex class="pa-2 ma-0">
                    <v-list shaped class="ma-0 pa-0">
                      <v-list-item
                        v-for="(item, i) in selectedQuestionIds"
                        :key="i"
                      >
                        <v-list-item-icon>
                          <v-icon>mdi-file</v-icon>
                        </v-list-item-icon>
                        <v-list-item-content>
                          <v-list-item-title>{{
                            getQuestionDescription(item)
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
          <v-btn color="blue darken-1" text @click="closeQuizDialog">
            Close
          </v-btn>
          <v-btn color="blue darken-1" text @click="saveQuiz"> Save </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import axios from "axios";
export default {
  data: () => ({
    quizItem: {},
    isLoading: false,
    inputQuestion: null,
    search: null,
    questions: [],
    selectedQuestionIds: [],
  }),
  computed: {
    items() {
      return this.questions;
    },
    getQuizDialog() {
      return this.$store.getters["quiz/getQuizDialog"];
    },
    getQuizTitle() {
      return this.$store.getters["quiz/getQuizTitle"];
    },
  },
  created: function () {
    this.fetchQuestionList();
  },
  watch: {
    search() {
      this.fetchQuestionList();
    },

    getQuizDialog: function () {
      //Display quiz data if it's edit quiz
      if (this.getQuizDialog == true && this.getQuizTitle == "Edit Quiz") {
        this.quizItem = this.$store.getters["quiz/getQuizItem"];
        this.selectedQuestionIds = this.quizItem.question_list;
      }
    },

    deep: true,
  },
  methods: {
    fetchQuestionList() {
      // Items have already been loaded
      if (this.items.length > 0) return;
      // Items have already been requested
      if (this.isLoading) return;
      this.isLoading = true;
      // Lazily load input items
      axios
        .get("https://ssad-api.herokuapp.com/api/v1/question")
        .then((res) => {
          const { count, questions } = res.data;
          this.count = count;
          this.questions = questions;
        })
        .catch((err) => {
          console.log(err);
        })
        .finally(() => (this.isLoading = false));
    },
    getSelectedValue() {
      const selectedObj = { ...this.inputQuestion };
      if (!this.selectedQuestionIds.includes(selectedObj._id)) {
        this.selectedQuestionIds.push(selectedObj._id);
      }
      // Workaround using $nexttick
      this.$nextTick(() => {
        this.inputQuestion = "";
      });
    },
    removeSelectedValue(index) {
      this.selectedQuestionIds.splice(index, 1);
    },
    getQuestionDescription(questionId) {
      var qnsObj = this.questions.find((x) => x._id === questionId);
      if (qnsObj != undefined) {
        return qnsObj.question_desc;
      }
    },
    saveQuiz() {
      this.quizItem.question_list = this.selectedQuestionIds;
      if (this.getQuizTitle == "New Quiz") {
        this.$store.dispatch("quiz/ADDQUIZ", this.quizItem);
      } else {
        this.$store.dispatch("quiz/EDITQUIZ", this.quizItem);
      }
      this.resetQuizForm();
      this.closeQuizDialog();
    },
    closeQuizDialog() {
      this.resetQuizForm();
      this.$store.dispatch("quiz/CLOSEQUIZDIALOG");
    },
    resetQuizForm() {
      this.quizItem = {};
      this.selectedQuestionIds = [];
    },
  },
};
</script>