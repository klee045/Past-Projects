import api from "@/service/api";

export const achievementModule = {
    namespaced: true,
    state: () => ({
        achievement: [],
        achievementTitle: "",
        achievementItem: {},
        achievementDialog: false
    }),

    getters: {
        getAchievements: (state) => {
            return state.achievement;
        },
        getAchievementTitle: (state) => {
            return state.achievementTitle;
        },
        getAchievementItem: (state) => {
            return state.achievementItem;
        },
        getAchievementDialog: (state) => {
            return state.achievementDialog;
        }
    },

    mutations: {
        openAchievementDialog(state) {
            state.achievementDialog = true;
        },
        closeAchievementDialog(state) {
            state.achievementDialog = false;
        },
        setAchievementTitle(state, title) {
            state.achievementTitle = title;
        },
        clearAchievementItem(state) {
            state.achievementItem = {};
        },
        setAchievementItem(state, achievementItem) {
            state.achievementItem = achievementItem;
        },
        getAllAchievements(state, achievement) {
            state.achievement = achievement;
        },
        addAchievement(state, achievementItem) {
            state.achievement.push(achievementItem);
        },
        deleteAchievement(state, achievementId) {
            let index = state.achievement.findIndex((item) => item._id === achievementId);
            state.achievement.splice(index, 1)
        }

    },

    actions: {

        OPENACHIEVEMENTDIALOG({ commit }) {
            commit('openAchievementDialog');
        },
        CLOSEACHIEVEMENTDIALOG({ commit }) {
            commit('closeAchievementDialog');
        },
        SETACHIEVEMENTTITLE({ commit }, userTitle) {
            commit('setAchievementTitle', userTitle);
        },
        CLEARACHIEVEMENTITEM({ commit }) {
            commit('clearAchievementItem');
        },
        SETACHIEVEMENTITEM({ commit }, userItem) {
            commit('setAchievementItem', userItem);
        },
        GETALLACHIEVEMENTS({ commit }, pagination) {
            return new Promise((resolve, reject) => {
                api
                    .getAllAchievements(pagination)
                    .then(async (response) => {
                        commit('getAllAchievements', response.data.achievements);
                        resolve(response);
                    })
                    .catch((err) => reject(err));
            });
        },
        ADDACHIEVEMENT({ commit }, achievementItem) {
            return new Promise((resolve, reject) => {
                api.addAchievement(achievementItem).then(async response => {
                    commit('addAchievement', response.data);
                    resolve(response)
                }).catch((err) => reject(err));
            });
        },
        EDITACHIEVEMENT(_, achievementItem) {
            return new Promise((resolve, reject) => {
                api.editAchievement(achievementItem).then(async response => {
                    resolve(response)
                }).catch((err) => reject(err));
            });
        },
        DELETEACHIEVEMENT({ commit }, achievementId) {
            return new Promise((resolve, reject) => {
                api.deleteAchievement(achievementId).then(async response => {
                    commit('deleteAchievement', achievementId);
                    resolve(response)
                }).catch((err) => reject(err));

            })
        }
    }
}