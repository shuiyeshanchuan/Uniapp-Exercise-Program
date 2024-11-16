import Vue from 'vue'
import App from './App.vue'
import router from './router'

Vue.config.productionTip = false
import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
import '@/styles/commin.css'
import '@/styles/theme/index.css'
//设置项目样式为mini
Vue.use(Element,{size:"mini"});

import request from '@/utils/request'
Vue.prototype.request = request
Vue.prototype.$httpUrl=process.env.VUE_APP_SERVER
Vue.prototype.$httpUploadFile=process.env.VUE_APP_SERVER+"/file/upload/tea-project"
// console.log("环境",process.env.NODE_ENV)
// console.log("服务器",process.env.VUE_APP_SERVER)
// console.log(process.env)

import CustomEditor from "@/components/CustomEditor";
//全局引入
Vue.component("CustomEditor",CustomEditor)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
