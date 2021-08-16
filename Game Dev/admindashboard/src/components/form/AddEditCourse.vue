<template>
  <v-container fluid class="pa-0">
    <v-dialog v-model="getCourseDialog" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline">{{ getCourseTitle }}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  label="Course Name"
                  v-model="courseItem.course_name"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  label="Course Desc"
                  v-model="courseItem.course_desc"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-autocomplete
                  class="searchList"
                  v-model="inputTopic"
                  :items="items"
                  :loading="isLoading"
                  :search-input.sync="search"
                  hide-no-data
                  hide-selected
                  item-value="_id"
                  item-text="topic_name"
                  label="Add Topic"
                  placeholder="Start typing to Search"
                  prepend-icon="mdi-database-search"
                  @change="getSelectedValue"
                  return-object
                ></v-autocomplete>
                <v-expand-transition>
                  <v-flex class="pa-2 ma-0">
                    <v-list shaped class="ma-0 pa-0">
                      <v-list-item
                        v-for="(item, i) in selectedTopicIds"
                        :key="i"
                      >
                        <v-list-item-icon>
                          <v-icon>mdi-file</v-icon>
                        </v-list-item-icon>
                        <v-list-item-content>
                          <v-list-item-title>{{
                            getTopicName(item)
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
          <v-btn color="blue darken-1" text @click="closeCourseDialog">
            Close
          </v-btn>
          <v-btn color="blue darken-1" text @click="saveCourse"> Save </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import axios from "axios";
export default {
  data: () => ({
    courseItem: {},
    isLoading: false,
    inputTopic: null,
    search: null,
    topics: [],
    selectedTopicIds: [],
  }),
  computed: {
    items() {
      return this.topics;
    },
    getCourseDialog() {
      return this.$store.getters["course/getCourseDialog"];
    },
    getCourseTitle() {
      return this.$store.getters["course/getCourseTitle"];
    },
  },
  created: function () {
    this.fetchTopicList();
  },
  watch: {
    search() {
      this.fetchTopicList();
    },
    getCourseDialog: function () {
      //Display course data if it's edit course
      if (
        this.getCourseDialog == true &&
        this.getCourseTitle == "Edit Course"
      ) {
        this.courseItem = this.$store.getters["course/getCourseItem"];
        this.selectedTopicIds = this.courseItem.topic_list;
      }
    },
    deep: true,
  },
  methods: {
    fetchTopicList() {
      // Items have already been loaded
      if (this.items.length > 0) return;
      // Items have already been requested
      if (this.isLoading) return;
      this.isLoading = true;
      // Lazily load input items
      axios
        .get("https://ssad-api.herokuapp.com/api/v1/topic_all")
        .then((res) => {
          const { count, topics } = res.data;
          this.count = count;
          this.topics = topics;
        })
        .catch((err) => {
          console.log(err);
        })
        .finally(() => (this.isLoading = false));
    },
    getSelectedValue() {
      const selectedObj = { ...this.inputTopic };
      if (!this.selectedTopicIds.includes(selectedObj._id)) {
        this.selectedTopicIds.push(selectedObj._id);
      }
      // Workaround using $nexttick
      this.$nextTick(() => {
        this.inputTopic = "";
      });
    },
    removeSelectedValue(index) {
      this.selectedTopicIds.splice(index, 1);
    },
    getTopicName(topicId) {
      var topicObj = this.topics.find((x) => x._id === topicId);
      if (topicObj != undefined) {
        return topicObj.topic_name;
      }
    },
    saveCourse() {
      this.courseItem.topic_list = this.selectedTopicIds;
      if (this.getCourseTitle == "New Course") {
        this.$store.dispatch("course/ADDCOURSE", this.courseItem);
      } else {
        this.$store.dispatch("course/EDITCOURSE", this.courseItem);
      }
      this.resetCourseForm();
      this.closeCourseDialog();
    },
    closeCourseDialog() {
      this.resetCourseForm();
      this.$store.dispatch("course/CLOSECOURSEDIALOG");
    },
    resetCourseForm() {
      this.courseItem = {};
      this.selectedTopicIds = [];
    },
  },
};
</script>