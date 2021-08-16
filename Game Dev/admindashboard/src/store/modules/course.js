import api from "@/service/api";

export const courseModule = {
    namespaced: true,
    state: () => ({
        course: [],
        courseTitle: "",
        courseItem: {},
        courseDialog: false
    }),

    getters: {
        getCourses: (state) => {
            return state.course;
        },
        getCourseTitle: (state) => {
            return state.courseTitle;
        },
        getCourseItem: (state) => {
            return state.courseItem;
        },
        getCourseDialog: (state) => {
            return state.courseDialog;
        }
    },

    mutations: {
        openCourseDialog(state) {
            state.courseDialog = true;
        },
        closeCourseDialog(state) {
            state.courseDialog = false;
        },
        setCourseTitle(state, title) {
            state.courseTitle = title;
        },
        clearCourseItem(state) {
            state.courseItem = {};
        },
        setCourseItem(state, courseItem) {
            state.courseItem = courseItem;
        },
        getAllCourses(state, course) {
            state.course = course;
        },
        addCourse(state, courseItem) {
            state.course.push(courseItem);
        },
        deleteCourse(state, courseId) {
            let index = state.course.findIndex((item) => item._id === courseId);
            state.course.splice(index, 1)
        }

    },

    actions: {
        OPENCOURSEDIALOG({ commit }) {
            commit('openCourseDialog');
        },
        CLOSECOURSEDIALOG({ commit }) {
            commit('closeCourseDialog');
        },
        SETCOURSETITLE({ commit }, userTitle) {
            commit('setCourseTitle', userTitle);
        },
        CLEARCOURSEITEM({ commit }) {
            commit('clearCourseItem');
        },
        SETCOURSEITEM({ commit }, userItem) {
            commit('setCourseItem', userItem);
        },
        GETCOURSELIST() {
            return new Promise((resolve, reject) => {
                api
                    .getCourseList()
                    .then((response) => {
                        resolve(response.data);
                    })
                    .catch((err) => reject(err));
            });
        },
        GETALLCOURSES({ commit }, pagination) {
            return new Promise((resolve, reject) => {
                api
                    .getAllCourses(pagination)
                    .then(async (response) => {
                        commit('getAllCourses', response.data.courses);
                        resolve(response);
                    })
                    .catch((err) => reject(err));
            });
        },
        ADDCOURSE({ commit }, userItem) {
            return new Promise((resolve, reject) => {
                api.addCourse(userItem).then(async response => {
                    commit('addCourse', response.data);
                    resolve(response)
                }).catch((err) => reject(err));
            });
        },
        EDITCOURSE(_, userItem) {
            return new Promise((resolve, reject) => {
                api.editCourse(userItem).then(async response => {
                    resolve(response)
                }).catch((err) => reject(err));
            });
        },
        DELETECOURSE({ commit }, courseId) {
            return new Promise((resolve, reject) => {
                api.deleteCourse(courseId).then(async response => {
                    commit('deleteCourse', courseId);
                    resolve(response)
                }).catch((err) => reject(err));

            })
        }
    }
}