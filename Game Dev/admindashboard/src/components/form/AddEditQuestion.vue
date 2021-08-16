<template>
  <v-container fluid class="pa-0">
    <v-dialog v-model="getQuestionDialog" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline">{{ getQuestionTitle }}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  label="Question Desc"
                  v-model="questionItem.question_desc"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  label="Difficulty"
                  v-model="questionItem.difficulty"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  v-model="optionName"
                  name="Option"
                  label="Enter option"
                  @keyup.enter="addOption(optionName)"
                />
              </v-col>
              <v-list dense min-width="550px">
                <v-subheader
                  class="subheading"
                  v-if="
                    questionItem.option != undefined &&
                    questionItem.option.length > 0
                  "
                  >Question Option</v-subheader
                >
                <v-list-item-group>
                  <v-list-item
                    v-for="(item, i) in questionItem.option"
                    :key="i"
                  >
                    <v-list-item-action>
                      <v-checkbox
                        v-model="item.is_correct"
                        @click="item.is_correct = true"
                      ></v-checkbox>
                    </v-list-item-action>

                    <v-list-item-content>
                      <v-list-item-title>{{
                        item.answer_desc
                      }}</v-list-item-title>
                    </v-list-item-content>
                    <v-list-item-action>
                      <v-btn
                        fab
                        ripple
                        x-small
                        color="red"
                        @click="removeOption(i)"
                      >
                        <v-icon class="white--text">mdi-close</v-icon>
                      </v-btn>
                    </v-list-item-action>
                  </v-list-item>
                </v-list-item-group>
              </v-list>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="closeQuestionDialog">
            Close
          </v-btn>
          <v-btn color="blue darken-1" text @click="saveQuestion"> Save </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    questionItem: {},
    optionName: "",
  }),
  computed: {
    getQuestionDialog() {
      return this.$store.getters["question/getQuestionDialog"];
    },
    getQuestionTitle() {
      return this.$store.getters["question/getQuestionTitle"];
    },
  },

  watch: {
    getQuestionDialog: function () {
      //Display question data if it's edit question
      if (
        this.getQuestionDialog == true &&
        this.getQuestionTitle == "Edit Question"
      ) {
        this.questionItem = this.$store.getters["question/getQuestionItem"];
      }
    },
    deep: true,
  },
  methods: {
    addOption(name) {
      if (this.questionItem.option == undefined) {
        this.questionItem.option = [];
      }
      this.questionItem.option.push({ answer_desc: name, is_correct: false });
      this.optionName = "";
    },
    removeOption(index) {
      this.questionItem.option.splice(index, 1);
      // Temp Fix - Force reactivity by adding empty space
      this.optionName = " ";
      this.optionName = "";
    },
    saveQuestion() {
      if (this.getQuestionTitle == "New Question") {
        this.$store.dispatch("question/ADDQUESTION", this.questionItem);
      } else {
        this.$store.dispatch("question/EDITQUESTION", this.questionItem);
      }
      this.resetQuestionForm();
      this.closeQuestionDialog();
    },
    closeQuestionDialog() {
      this.resetQuestionForm();
      this.$store.dispatch("question/CLOSEQUESTIONDIALOG");
    },
    resetQuestionForm() {
      this.questionItem = {};
    },
  },
};
</script>