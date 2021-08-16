import Vue from 'vue'
import App from './App.vue'
import router from "./router/index";
import vuetify from './plugins/vuetify';
import store from "./store";
import Multiselect from "vue-multiselect";
import VueSocialSharing from 'vue-social-sharing'

Vue.config.productionTip = false

Vue.component("multiselect", Multiselect);
Vue.use(VueSocialSharing);

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
