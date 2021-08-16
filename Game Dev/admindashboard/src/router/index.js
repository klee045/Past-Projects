import Vue from "vue";
import VueRouter from "vue-router";
import Meta from "vue-meta";

import Login from "@/views/Login";
import Dashboard from '@/views/Dashboard'
import DashboardHome from '@/components/main/Home'
import DashboardCourse from '@/components/main/Course'
import DashboardTopic from '@/components/main/Topic'
import DashboardQuestion from '@/components/main/Question'
import DashboardQuiz from '@/components/main/Quiz'
import DashboardUser from '@/components/main/User'
import DashboardAchievement from '@/components/main/Achievement'

Vue.use(VueRouter);
Vue.use(Meta);

const routes = [
    {
        path: '/',
        component: Login,
        // redirect if already signed in
        beforeEnter: (to, from, next) => {
            if (localStorage.getItem('auth') == true) {
                next("/dashboard");
            } else {
                next();
            }
        },
        children: [{ path: "", component: Login, }, { path: 'login', component: Login },]
    },
    {
        path: '/dashboard',
        component: Dashboard,
        meta: { requiresAuth: true },
        beforeEnter: (to, from, next) => {
            if (localStorage.getItem('role') === "admin") {
                next();
            } else {
                next("/");
            }
        },
        children: [
            { path: "", redirect: { name: 'DashboardHome' }, meta: { requiresAuth: true } },
            { path: 'home', name: 'DashboardHome', component: DashboardHome, meta: { requiresAuth: true } },
            { path: 'course', name: 'DashboardCourse', component: DashboardCourse, meta: { requiresAuth: true } },
            { path: 'topic', name: 'DashboardTopic', component: DashboardTopic, meta: { requiresAuth: true } },
            { path: 'question', name: 'DashboardQuestion', component: DashboardQuestion, meta: { requiresAuth: true } },
            { path: 'quiz', name: 'DashboardQuiz', component: DashboardQuiz, meta: { requiresAuth: true } },
            { path: 'user', name: 'DashboardUser', component: DashboardUser, meta: { requiresAuth: true } },
            { path: 'achievement', name: 'DashboardAchievement', component: DashboardAchievement, meta: { requiresAuth: true } },
        ]
    }
]

const router = new VueRouter({
    mode: "history",
    routes: routes,

    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) {
            return savedPosition;
        }
        if (to.hash) {
            return { selector: to.hash };
        }
        return { x: 0, y: 0 };
    },
});

export default router;
