import api from "@/service/api";

export const userModule = {
    namespaced: true,
    state: () => ({
        user: [],
        userTitle: "",
        userItem: {},
        userDialog: false
    }),

    getters: {
        getUsers: (state) => {
            return state.user;
        },
        getUserTitle: (state) => {
            return state.userTitle;
        },
        getUserItem: (state) => {
            return state.userItem;
        },
        getUserDialog: (state) => {
            return state.userDialog;
        }
    },

    mutations: {
        openUserDialog(state) {
            state.userDialog = true;
        },
        closeUserDialog(state) {
            state.userDialog = false;
        },
        setUserTitle(state, title) {
            state.userTitle = title;
        },
        clearUserItem(state) {
            state.userItem = {};
        },
        setUserItem(state, userItem) {
            state.userItem = userItem;
        },
        getAllUsers(state, user) {
            state.user = user;
        },
        addUser(state, userItem) {
            state.user.push(userItem);
        },
        deleteUser(state, userId) {
            let index = state.user.findIndex((item) => item._id === userId);
            state.user.splice(index, 1)
        }

    },

    actions: {
        OPENUSERDIALOG({ commit }) {
            commit('openUserDialog');
        },
        CLOSEUSERDIALOG({ commit }) {
            commit('closeUserDialog');
        },
        SETUSERTITLE({ commit }, userTitle) {
            commit('setUserTitle', userTitle);
        },
        CLEARUSERITEM({ commit }) {
            commit('clearUserItem');
        },
        SETUSERITEM({ commit }, userItem) {
            commit('setUserItem', userItem);
        },
        LOGIN(_, user) {
            return new Promise((resolve, reject) => {
                api
                    .loginUser(user)
                    .then(async (response) => {
                        localStorage.setItem("auth", "true");
                        localStorage.setItem("role", "admin");
                        localStorage.setItem("name", response.data.user.username);
                        resolve(response);
                    })
                    .catch((err) => reject(err));
            });
        },
        GETALLUSERS({ commit }, pagination) {
            return new Promise((resolve, reject) => {
                api
                    .getAllUsers(pagination)
                    .then(async (response) => {
                        commit('getAllUsers', response.data.users);
                        resolve(response);
                    })
                    .catch((err) => reject(err));
            });
        },
        ADDUSER({ commit }, userItem) {
            return new Promise((resolve, reject) => {
                api.addUser(userItem).then(async response => {
                    commit('addUser', response.data.user);
                    resolve(response)
                }).catch((err) => reject(err));
            });
        },
        EDITUSER(_, userItem) {
            return new Promise((resolve, reject) => {
                api.editUser(userItem).then(async response => {
                    resolve(response)
                }).catch((err) => reject(err));
            });
        },
        DELETEUSER({ commit }, userId) {
            return new Promise((resolve, reject) => {
                api.deleteUser(userId).then(async response => {
                    commit('deleteUser', userId);
                    resolve(response)
                }).catch((err) => reject(err));

            })
        }
    }
}