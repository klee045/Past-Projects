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
              Manage Quiz
            </v-card-title>
            <v-card-text>
              <v-row wrap class="mt-2">
                <v-flex xs6>
                  <v-text-field
                    label="Search Quiz"
                    v-model="search"
                    append-icon="mdi-magnify"
                    single-line
                    hide-details
                  ></v-text-field>
                </v-flex>
                <v-flex xs6 class="text-right">
                  <v-btn large color="black" @click="addQuiz" depressed>
                    <span class="white--text"> New Quiz </span>
                  </v-btn>
                </v-flex>
              </v-row>
            </v-card-text>

            <!-- Start of Datatable -->
            <v-data-table
              :headers="headers"
              :search="search"
              :items="quiz"
              :options.sync="pagination"
              :loading="loading"
              :server-items-length="pagination.totalItems"
              :footer-props="footerProps"
              class="elevation-0 pa-2"
            >
              <template v-slot:item="{ item }">
                <tr>
                  <td>{{ item.quiz_name }}</td>
                  <td>{{ item.quiz_desc }}</td>
                  <td class="text-center">
                    <ShareNetwork
                      class="px-2"
                      v-for="network in networks"
                      :network="network.network"
                      :key="network.network"
                      :style="{ backgroundColor: network.color }"
                      :url="sharing.url"
                      :title="sharing.title"
                      :description="sharing.description"
                      :quote="sharing.quote"
                      :hashtags="sharing.hashtags"
                      :twitterUser="sharing.twitterUser"
                      @open="openshare(item)"
                    >
                      <i :class="network.icon"></i>
                      <span>{{ network.name }}</span>
                    </ShareNetwork>
                  </td>
                  <td>
                    <v-row class="justify-center">
                      <v-btn
                        class="mr-2"
                        color="#E4E6EB"
                        depressed
                        @click="editQuiz(item)"
                      >
                        <v-icon size="20" depressed color="black"
                          >mdi-pencil</v-icon
                        >
                      </v-btn>
                      <v-btn
                        color="#E4E6EB"
                        depressed
                        @click="deleteQuiz(item._id)"
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
            <AddEditQuiz />
            <!-- End of Add Edit Modal -->
          </v-card>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";
import AddEditQuiz from "../form/AddEditQuiz";
export default {
  name: "QuizPage",
  components: {
    AddEditQuiz,
  },
  data: () => ({
    loading: false,
    filesImage: require("../../assets/files.svg"),
    footerProps: { "items-per-page-options": [5, 15, 30] },
    headers: [
      { text: "Quiz Name", value: "quiz_name", width: "25%" },
      { text: "Quiz Description", value: "quiz_desc", width: "25%" },
      { text: "Quiz Share", width: "25%", align: "center" },
      { text: "Actions", width: "25%", align: "center" },
    ],
    sharing: {
      url: "https://sites.google.com/view/ssad/home",
      title: "",
      description: "",
      twitterUser: "ssad-admin",
    },
    networks: [
      {
        network: "facebook",
        name: "Facebook",
        icon: "fab fah fa-lg fa-facebook-f",
      },
      {
        network: "twitter",
        name: "Twitter",
        icon: "ma-1 fab fa-twitter",
      },
      {
        network: "email",
        name: "Email",
        icon: "ma-1 fas fa-lg fa-envelope",
      },
    ],
    pagination: {},
    quizData: {},
    search: "",
    quizTitle: "New Quiz",
  }),
  computed: {
    ...mapGetters({
      quiz: "quiz/getQuiz",
    }),
    getQuiz() {
      return this.$store.getters["quiz/getQuiz"];
    },
  },
  watch: {
    pagination: {
      handler() {
        this.getQuizList();
      },
      deep: true,
    },
  },
  mounted() {
    this.getQuizList();
  },
  methods: {
    async getQuizList() {
      this.loading = true;
      await this.$store.dispatch("quiz/GETALLQUIZ");
      this.loading = false;
    },

    addQuiz() {
      this.$store.dispatch("quiz/SETQUIZTITLE", "New Quiz");
      this.$store.dispatch("quiz/OPENQUIZDIALOG");
      this.$store.dispatch("quiz/CLEARQUIZITEM");
    },

    editQuiz(quizData) {
      this.$store.dispatch("quiz/SETQUIZTITLE", "Edit Quiz");
      this.$store.dispatch("quiz/OPENQUIZDIALOG");
      this.$store.dispatch("quiz/SETQUIZITEM", quizData);
      this.quizData = quizData;
    },

    deleteQuiz(quizId) {
      this.$store.dispatch("quiz/DELETEQUIZ", quizId);
    },
    openshare(item) {
      this.sharing.title =
        "[Notice] " + item.quiz_name + " (" + item.quiz_desc + ")";
      this.sharing.description =
        "Dear students, quiz have been released. Please complete the quiz on the app. Thank you.";
    },
  },
};
</script>