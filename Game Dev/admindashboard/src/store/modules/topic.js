import api from "@/service/api";

export const topicModule = {
    namespaced: true,
    state: () => ({
        topic: [],
        topicTitle: "",
        topicItem: {},
        topicDialog: false
    }),

    getters: {
        getTopic: (state) => {
            return state.topic;
        },
        getTopicTitle: (state) => {
            return state.topicTitle;
        },
        getTopicItem: (state) => {
            return state.topicItem;
        },
        getTopicDialog: (state) => {
            return state.topicDialog;
        }
    },

    mutations: {
        openTopicDialog(state) {
            state.topicDialog = true;
        },
        closeTopicDialog(state) {
            state.topicDialog = false;
        },
        setTopicTitle(state, title) {
            state.topicTitle = title;
        },
        clearTopicItem(state) {
            state.topicItem = {};
        },
        setTopicItem(state, topicItem) {
            state.topicItem = topicItem;
        },
        getAllTopic(state, topic) {
            state.topic = topic;
        },
        addTopic(state, topicItem) {
            state.topic.push(topicItem);
        },
        deleteTopic(state, topicId) {
            let index = state.topic.findIndex((item) => item._id === topicId);
            state.topic.splice(index, 1)
        }
    },

    actions: {
        OPENTOPICDIALOG({ commit }) {
            commit('openTopicDialog');
        },
        CLOSETOPICDIALOG({ commit }) {
            commit('closeTopicDialog');
        },
        SETTOPICTITLE({ commit }, topicTitle) {
            commit('setTopicTitle', topicTitle);
        },
        CLEARTOPICITEM({ commit }) {
            commit('clearTopicItem');
        },
        SETTOPICITEM({ commit }, topicItem) {
            commit('setTopicItem', topicItem);
        },
        GETALLTOPICS({ commit }, pagination) {
            return new Promise((resolve, reject) => {
                api
                    .getAllTopics(pagination)
                    .then(async (response) => {
                        commit('getAllTopic', response.data.topics);
                        resolve(response);
                    })
                    .catch((err) => reject(err));
            });
        },
        ADDTOPIC({ commit }, topicItem) {
            return new Promise((resolve, reject) => {
                api.addTopic(topicItem).then(async response => {
                    commit('addTopic', response.data);
                    resolve(response)
                }).catch((err) => reject(err));
            });
        },
        EDITTOPIC(_, topicItem) {
            return new Promise((resolve, reject) => {
                api.editTopic(topicItem).then(async response => {
                    resolve(response)
                }).catch((err) => reject(err));
            });
        },
        DELETETOPIC({ commit }, topicId) {
            return new Promise((resolve, reject) => {
                api.deleteTopic(topicId).then(async response => {
                    commit('deleteTopic', topicId);
                    resolve(response)
                }).catch((err) => reject(err));
            })
        }
    }
}