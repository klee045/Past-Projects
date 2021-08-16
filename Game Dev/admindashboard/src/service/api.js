import axios from "axios";

const BASE_URL = "https://ssad-api.herokuapp.com"
const USER_BASE_URL = "/api/v1/user";
const COURSE_BASE_URL = "/api/v1/course";
const TOPIC_BASE_URL = "/api/v1/topic";
const QUIZ_BASE_URL = "/api/v1/quiz";
const QUESTION_BASE_URL = "/api/v1/question";
const ACHIEVEMENT_BASE_URL = "/api/v1/achievement";
//const LEADERBOARD_BASE_URL = "/api/v1/leaderboard";

const apiClient = axios.create(
    {
        baseURL: BASE_URL,
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
    },
    { withCredentials: true }
);

// All the API Calls to be done here
export default {
    // User
    loginUser(user) {
        return apiClient.post(`${USER_BASE_URL}/login`, user);
    },
    getAllUsers(paginate) {
        return apiClient.get(`${USER_BASE_URL}`, { params: paginate });
    },
    addUser(user) {
        return apiClient.post(`${USER_BASE_URL}/register`, user);
    },
    editUser(user) {
        return apiClient.put(`${USER_BASE_URL}/${user._id}`, user);
    },
    deleteUser(userId) {
        return apiClient.delete(`${USER_BASE_URL}/${userId}`);
    },

    // Course
    getCourseList() {
        return apiClient.get("/api/v1/list/course");
    },
    getAllCourses(paginate) {
        return apiClient.get(`${COURSE_BASE_URL}_all`, { params: paginate });
    },
    addCourse(course) {
        return apiClient.post(`${COURSE_BASE_URL}`, course);
    },
    editCourse(course) {
        return apiClient.put(`${COURSE_BASE_URL}/${course._id}`, course);
    },
    deleteCourse(courseId) {
        return apiClient.delete(`${COURSE_BASE_URL}/${courseId}`);
    },

    // Question
    getAllQuestions(paginate) {
        return apiClient.get(`${QUESTION_BASE_URL}`, { params: paginate });
    },
    addQuestion(question) {
        return apiClient.post(`${QUESTION_BASE_URL}`, question);
    },
    editQuestion(question) {
        return apiClient.put(`${QUESTION_BASE_URL}/${question._id}`, question);
    },
    deleteQuestion(questionId) {
        return apiClient.delete(`${QUESTION_BASE_URL}/${questionId}`);
    },

    // Quiz
    getAllQuiz(paginate) {
        return apiClient.get(`${QUIZ_BASE_URL}`, { params: paginate });
    },
    addQuiz(quiz) {
        return apiClient.post(`${QUIZ_BASE_URL}`, quiz);
    },
    editQuiz(quiz) {
        return apiClient.put(`${QUIZ_BASE_URL}/${quiz._id}`, quiz);
    },
    deleteQuiz(quizId) {
        return apiClient.delete(`${QUIZ_BASE_URL}/${quizId}`);
    },

    // Achievement
    getAllAchievements(paginate) {
        return apiClient.get(`${ACHIEVEMENT_BASE_URL}_all`, { params: paginate });
    },
    addAchievement(achievement) {
        return apiClient.post(`${ACHIEVEMENT_BASE_URL}`, achievement);
    },
    editAchievement(achievement) {
        return apiClient.put(`${ACHIEVEMENT_BASE_URL}/${achievement._id}`, achievement);
    },
    deleteAchievement(achievementId) {
        return apiClient.delete(`${ACHIEVEMENT_BASE_URL}/${achievementId}`);
    },

    // Topic
    getAllTopics(paginate) {
        return apiClient.get(`${TOPIC_BASE_URL}_all`, { params: paginate });
    },
    addTopic(topic) {
        return apiClient.post(`${TOPIC_BASE_URL}`, topic);
    },
    editTopic(topic) {
        return apiClient.put(`${TOPIC_BASE_URL}/${topic._id}`, topic);
    },
    deleteTopic(topicId) {
        return apiClient.delete(`${TOPIC_BASE_URL}/${topicId}`);
    },
}