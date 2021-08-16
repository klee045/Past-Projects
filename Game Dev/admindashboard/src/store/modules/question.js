import api from "@/service/api";

export const questionModule = {
    namespaced: true,
    state: () => ({
        question: [],
        questionTitle: "",
        questionItem: {},
        questionDialog: false
    }),

    getters: {
        getQuestions: (state) => {
            return state.question;
        },
        getQuestionTitle: (state) => {
            return state.questionTitle;
        },
        getQuestionItem: (state) => {
            return state.questionItem;
        },
        getQuestionDialog: (state) => {
            return state.questionDialog;
        }
    },

    mutations: {
        openQuestionDialog(state) {
            state.questionDialog = true;
        },
        closeQuestionDialog(state) {
            state.questionDialog = false;
        },
        setQuestionTitle(state, title) {
            state.questionTitle = title;
        },
        clearQuestionItem(state) {
            state.questionItem = {};
        },
        setQuestionItem(state, questionItem) {
            state.questionItem = questionItem;
        },
        getAllQuestions(state, question) {
            state.question = question;
        },
        addQuestion(state, questionItem) {
            state.question.push(questionItem);
        },
        deleteQuestion(state, questionId) {
            let index = state.question.findIndex((item) => item._id === questionId);
            state.question.splice(index, 1)
        }

    },

    actions: {
        OPENQUESTIONDIALOG({ commit }) {
            commit('openQuestionDialog');
        },
        CLOSEQUESTIONDIALOG({ commit }) {
            commit('closeQuestionDialog');
        },
        SETQUESTIONTITLE({ commit }, userTitle) {
            commit('setQuestionTitle', userTitle);
        },
        CLEARQUESTIONITEM({ commit }) {
            commit('clearQuestionItem');
        },
        SETQUESTIONITEM({ commit }, userItem) {
            commit('setQuestionItem', userItem);
        },
        GETQUESTIONLIST() {
            return new Promise((resolve, reject) => {
                api
                    .getQuestionList()
                    .then((response) => {
                        resolve(response.data);
                    })
                    .catch((err) => reject(err));
            });
        },
        GETALLQUESTIONS({ commit }, pagination) {
            return new Promise((resolve, reject) => {
                api
                    .getAllQuestions(pagination)
                    .then(async (response) => {
                        commit('getAllQuestions', response.data.questions);
                        resolve(response);
                    })
                    .catch((err) => reject(err));
            });
        },
        ADDQUESTION({ commit }, questionItem) {
            return new Promise((resolve, reject) => {
                api.addQuestion(questionItem).then(async response => {
                    commit('addQuestion', response.data);
                    resolve(response)
                }).catch((err) => reject(err));
            });
        },
        EDITQUESTION(_, questionItem) {
            return new Promise((resolve, reject) => {
                api.editQuestion(questionItem).then(async response => {
                    resolve(response)
                }).catch((err) => reject(err));
            });
        },
        DELETEQUESTION({ commit }, questionId) {
            return new Promise((resolve, reject) => {
                api.deleteQuestion(questionId).then(async response => {
                    commit('deleteQuestion', questionId);
                    resolve(response)
                }).catch((err) => reject(err));
            })
        }
    }
}