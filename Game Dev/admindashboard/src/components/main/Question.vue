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
              Manage Question
            </v-card-title>
            <v-card-text>
              <v-row wrap class="mt-2">
                <v-flex xs6>
                  <v-text-field
                    label="Search Question"
                    v-model="search"
                    append-icon="mdi-magnify"
                    single-line
                    hide-details
                  ></v-text-field>
                </v-flex>
                <v-flex xs6 class="text-right">
                  <v-btn large color="black" @click="addQuestion" depressed>
                    <span class="white--text"> New Question </span>
                  </v-btn>
                </v-flex>
              </v-row>
            </v-card-text>

            <!-- Start of Datatable -->
            <v-data-table
              :headers="headers"
              :search="search"
              :items="question"
              :options.sync="pagination"
              :loading="loading"
              :server-items-length="pagination.totalItems"
              :footer-props="footerProps"
              class="elevation-0 pa-2"
            >
              <template v-slot:item="{ item }">
                <tr>
                  <td class="truncate">{{ item.question_desc }}</td>
                  <td>{{ item.difficulty }}</td>
                  <td>
                    <v-row class="justify-center">
                      <v-btn
                        class="mr-2"
                        color="#E4E6EB"
                        depressed
                        @click="editQuestion(item)"
                      >
                        <v-icon size="20" depressed color="black"
                          >mdi-pencil</v-icon
                        >
                      </v-btn>
                      <v-btn
                        color="#E4E6EB"
                        depressed
                        @click="deleteQuestion(item._id)"
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
            <AddEditQuestion />
            <!-- End of Add Edit Modal -->
          </v-card>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";
import AddEditQuestion from "../form/AddEditQuestion";
export default {
  name: "QuestionPage",
  components: {
    AddEditQuestion,
  },
  data: () => ({
    loading: false,
    filesImage: require("../../assets/files.svg"),
    footerProps: { "items-per-page-options": [5, 15, 30] },
    headers: [
      { text: "Question", value: "question_desc" },
      { text: "Difficulty", value: "difficulty" },
      { text: "Actions", width: "20%", align: "center" },
    ],
    pagination: {},
    questionData: {},
    search: "",
    questionTitle: "New Question",
  }),
  computed: {
    ...mapGetters({
      question: "question/getQuestions",
    }),
    getQuestions() {
      return this.$store.getters["question/getQuestions"];
    },
  },
  watch: {
    pagination: {
      handler() {
        this.getQuestionList();
      },
      deep: true,
    },
  },
  mounted() {
    this.getQuestionList();
  },
  methods: {
    async getQuestionList() {
      this.loading = true;
      await this.$store.dispatch("question/GETALLQUESTIONS");
      this.loading = false;
    },

    addQuestion() {
      this.$store.dispatch("question/SETQUESTIONTITLE", "New Question");
      this.$store.dispatch("question/OPENQUESTIONDIALOG");
      this.$store.dispatch("question/CLEARQUESTIONITEM");
    },

    editQuestion(questionData) {
      this.$store.dispatch("question/SETQUESTIONTITLE", "Edit Question");
      this.$store.dispatch("question/OPENQUESTIONDIALOG");
      this.$store.dispatch("question/SETQUESTIONITEM", questionData);
      this.questionData = questionData;
    },

    deleteQuestion(questionId) {
      this.$store.dispatch("question/DELETEQUESTION", questionId);
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