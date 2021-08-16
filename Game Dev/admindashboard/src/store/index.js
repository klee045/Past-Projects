import Vue from "vue";
import Vuex from "vuex";
import { userModule } from "./modules/user";
import { courseModule } from "./modules/course";
import { questionModule } from "./modules/question";
import { quizModule } from "./modules/quiz";
import { achievementModule } from "./modules/achievement";
import { topicModule } from "./modules/topic";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {},
    mutations: {},
    actions: {},
    modules: {
        user: userModule,
        course: courseModule,
        question: questionModule,
        quiz: quizModule,
        achievement: achievementModule,
        topic: topicModule
    },
});
