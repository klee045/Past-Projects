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
              Manage Course
            </v-card-title>
            <v-card-text>
              <v-row wrap class="mt-2">
                <v-flex xs6>
                  <v-text-field
                    label="Search Course"
                    v-model="search"
                    append-icon="mdi-magnify"
                    single-line
                    hide-details
                  ></v-text-field>
                </v-flex>
                <v-flex xs6 class="text-right">
                  <v-btn large color="black" @click="addCourse" depressed>
                    <span class="white--text"> New Course </span>
                  </v-btn>
                </v-flex>
              </v-row>
            </v-card-text>

            <!-- Start of Datatable -->
            <v-data-table
              :headers="headers"
              :search="search"
              :items="course"
              :options.sync="pagination"
              :loading="loading"
              :server-items-length="pagination.totalItems"
              :footer-props="footerProps"
              class="elevation-0 pa-2"
            >
              <template v-slot:item="{ item }">
                <tr>
                  <td>{{ item.course_name }}</td>
                  <td class="truncate">{{ item.course_desc }}</td>
                  <td>
                    <v-row class="justify-center">
                      <v-btn
                        class="mr-2"
                        color="#E4E6EB"
                        depressed
                        @click="editCourse(item)"
                      >
                        <v-icon size="20" depressed color="black"
                          >mdi-pencil</v-icon
                        >
                      </v-btn>
                      <v-btn
                        color="#E4E6EB"
                        depressed
                        @click="deleteCourse(item._id)"
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
            <AddEditCourse />
            <!-- End of Add Edit Modal -->
          </v-card>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";
import AddEditCourse from "../form/AddEditCourse";
export default {
  name: "CoursePage",
  components: {
    AddEditCourse,
  },
  data: () => ({
    loading: false,
    filesImage: require("../../assets/files.svg"),
    footerProps: { "items-per-page-options": [5, 15, 30] },
    headers: [
      { text: "Course Name", value: "course_name", width: "25%" },
      { text: "Course Description", value: "course_desc", width: "25%" },
      { text: "Actions", width: "25%", align: "center" },
    ],
    pagination: {},
    courseData: {},
    search: "",
    courseTitle: "New Course",
  }),
  computed: {
    ...mapGetters({
      course: "course/getCourses",
    }),
    getCourses() {
      return this.$store.getters["course/getCourses"];
    },
  },
  watch: {
    pagination: {
      handler() {
        this.getCourseList();
      },
      deep: true,
    },
  },
  mounted() {
    this.getCourseList();
  },
  methods: {
    async getCourseList() {
      this.loading = true;
      await this.$store.dispatch("course/GETALLCOURSES");
      this.loading = false;
    },

    addCourse() {
      this.$store.dispatch("course/SETCOURSETITLE", "New Course");
      this.$store.dispatch("course/OPENCOURSEDIALOG");
      this.$store.dispatch("course/CLEARCOURSEITEM");
    },

    editCourse(courseData) {
      this.$store.dispatch("course/SETCOURSETITLE", "Edit Course");
      this.$store.dispatch("course/OPENCOURSEDIALOG");
      this.$store.dispatch("course/SETCOURSEITEM", courseData);
      this.courseData = courseData;
    },

    deleteCourse(courseId) {
      this.$store.dispatch("course/DELETECOURSE", courseId);
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