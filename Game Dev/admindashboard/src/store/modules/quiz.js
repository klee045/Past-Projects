import api from "@/service/api";

export const quizModule = {
    namespaced: true,
    state: () => ({
        quiz: [],
        quizTitle: "",
        quizItem: {},
        quizDialog: false
    }),

    getters: {
        getQuiz: (state) => {
            return state.quiz;
        },
        getQuizTitle: (state) => {
            return state.quizTitle;
        },
        getQuizItem: (state) => {
            return state.quizItem;
        },
        getQuizDialog: (state) => {
            return state.quizDialog;
        }
    },

    mutations: {
        openQuizDialog(state) {
            state.quizDialog = true;
        },
        closeQuizDialog(state) {
            state.quizDialog = false;
        },
        setQuizTitle(state, title) {
            state.quizTitle = title;
        },
        clearQuizItem(state) {
            state.quizItem = {};
        },
        setQuizItem(state, quizItem) {
            state.quizItem = quizItem;
        },
        getAllQuiz(state, quiz) {
            state.quiz = quiz;
        },
        addQuiz(state, quizItem) {
            state.quiz.push(quizItem);
        },
        deleteQuiz(state, quizId) {
            let index = state.quiz.findIndex((item) => item._id === quizId);
            state.quiz.splice(index, 1)
        }
    },

    actions: {
        OPENQUIZDIALOG({ commit }) {
            commit('openQuizDialog');
        },
        CLOSEQUIZDIALOG({ commit }) {
            commit('closeQuizDialog');
        },
        SETQUIZTITLE({ commit }, quizTitle) {
            commit('setQuizTitle', quizTitle);
        },
        CLEARQUIZITEM({ commit }) {
            commit('clearQuizItem');
        },
        SETQUIZITEM({ commit }, quizItem) {
            commit('setQuizItem', quizItem);
        },
        GETALLQUIZ({ commit }, pagination) {
            return new Promise((resolve, reject) => {
                api
                    .getAllQuiz(pagination)
                    .then(async (response) => {
                        commit('getAllQuiz', response.data.quizzes);
                        resolve(response);
                    })
                    .catch((err) => reject(err));
            });
        },
        ADDQUIZ({ commit }, quizItem) {
            return new Promise((resolve, reject) => {
                api.addQuiz(quizItem).then(async response => {
                    commit('addQuiz', response.data);
                    resolve(response)
                }).catch((err) => reject(err));
            });
        },
        EDITQUIZ(_, quizItem) {
            return new Promise((resolve, reject) => {
                api.editQuiz(quizItem).then(async response => {
                    resolve(response)
                }).catch((err) => reject(err));
            });
        },
        DELETEQUIZ({ commit }, quizId) {
            return new Promise((resolve, reject) => {
                api.deleteQuiz(quizId).then(async response => {
                    commit('deleteQuiz', quizId);
                    resolve(response)
                }).catch((err) => reject(err));
            })
        }
    }
}