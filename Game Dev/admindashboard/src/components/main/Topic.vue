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
              Manage Topic
            </v-card-title>
            <v-card-text>
              <v-row wrap class="mt-2">
                <v-flex xs6>
                  <v-text-field
                    label="Search Topic"
                    v-model="search"
                    append-icon="mdi-magnify"
                    single-line
                    hide-details
                  ></v-text-field>
                </v-flex>
                <v-flex xs6 class="text-right">
                  <v-btn large color="black" @click="addTopic" depressed>
                    <span class="white--text"> New Topic </span>
                  </v-btn>
                </v-flex>
              </v-row>
            </v-card-text>

            <!-- Start of Datatable -->
            <v-data-table
              :headers="headers"
              :search="search"
              :items="topic"
              :options.sync="pagination"
              :loading="loading"
              :server-items-length="pagination.totalItems"
              :footer-props="footerProps"
              class="elevation-0 pa-2"
            >
              <template v-slot:item="{ item }">
                <tr>
                  <td>{{ item.topic_name }}</td>
                  <td>{{ item.topic_desc }}</td>
                  <td>
                    <v-row class="justify-center">
                      <v-btn
                        class="mr-2"
                        color="#E4E6EB"
                        depressed
                        @click="editTopic(item)"
                      >
                        <v-icon size="20" depressed color="black"
                          >mdi-pencil</v-icon
                        >
                      </v-btn>
                      <v-btn
                        color="#E4E6EB"
                        depressed
                        @click="deleteTopic(item._id)"
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
            <AddEditTopic />
            <!-- End of Add Edit Modal -->
          </v-card>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";
import AddEditTopic from "../form/AddEditTopic";
export default {
  name: "TopicPage",
  components: {
    AddEditTopic,
  },
  data: () => ({
    loading: false,
    filesImage: require("../../assets/files.svg"),
    footerProps: { "items-per-page-options": [5, 15, 30] },
    headers: [
      { text: "Topic Name", value: "topic_name", width: "25%" },
      { text: "Topic Description", value: "topic_desc", width: "25%" },
      { text: "Actions", width: "25%", align: "center" },
    ],
    pagination: {},
    topicData: {},
    search: "",
    topicTitle: "New Topic",
  }),
  computed: {
    ...mapGetters({
      topic: "topic/getTopic",
    }),
    getTopic() {
      return this.$store.getters["topic/getTopic"];
    },
  },
  watch: {
    pagination: {
      handler() {
        this.getTopicList();
      },
      deep: true,
    },
  },
  mounted() {
    this.getTopicList();
  },
  methods: {
    async getTopicList() {
      this.loading = true;
      await this.$store.dispatch("topic/GETALLTOPICS");
      this.loading = false;
    },

    addTopic() {
      this.$store.dispatch("topic/SETTOPICTITLE", "New Topic");
      this.$store.dispatch("topic/OPENTOPICDIALOG");
      this.$store.dispatch("topic/CLEARTOPICITEM");
    },

    editTopic(topicData) {
      this.$store.dispatch("topic/SETTOPICTITLE", "Edit Topic");
      this.$store.dispatch("topic/OPENTOPICDIALOG");
      this.$store.dispatch("topic/SETTOPICITEM", topicData);
      this.topicData = topicData;
    },

    deleteTopic(topicId) {
      this.$store.dispatch("topic/DELETETOPIC", topicId);
    },
  },
};
</script>