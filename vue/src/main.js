import Vue from 'vue'
import App from './App.vue'
import router from './router'

// import 'jquery/dist/jquery.min'
//
// import 'bootstrap/dist/css/bootstrap.css'
// import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '@/assets/globel.css'

Vue.config.productionTip = false
Vue.use(ElementUI, {size: 'medium'}); //medium small

new Vue({
  // store,
  router,
  render: h => h(App)
}).$mount('#app')
