import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Cookies from 'js-cookie'

import '@/assets/css/global.css'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'dayjs/locale/zh-cn'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

createApp(App).use(ElementPlus,{locale: zhCn,}).use(store).use(router).use(Cookies,{size:Cookies.get('size') || 'medium'}).mount('#app')
