import Vue from 'vue'
import VueRouter from 'vue-router'
import AdminLayout from '../views/layout/AdminLayout.vue'
import FrontLayout from '../views/layout/FrontLayout.vue'
import tools from "@/utils/utils";

Vue.use(VueRouter)

const routes = [
    {
        path: '/front', name: '', component: FrontLayout, redirect: "/front/index",
        children: [
            {path: "index", name: "index", component: () => import('../views/front/Index')},
            {path: "about", name: "about", component: () => import('../views/front/About')},
            {path: "editUserInfo", name: "editUserInfo", component: () => import('../views/editUserInfo')},
            {path: "editPassword", name: "editPassword", component: () => import('../views/editPassword')},
        ]
    },
    {path: "/login", name: "login", component: () => import('../views/Login')},
    {path: "/register", name: "register", component: () => import('../views/Register')},
    {path: '*', name: 'NotFound', meta: {name: ''}, component: () => import('../views/404.vue')},
]

let router = new VueRouter({
    //路由模式
    // mode: 'history',
    routes
})

export const AddRoutes = () => {
//从本地存储中进行读取
//每次调用，都要重新获取菜单
    let routesCopy =
        {
            path: '/',
            name: 'home',
            component: AdminLayout,
            redirect: "/home",
            children: [
                {path: "/home", name: "", component: () => import('../views/admin/Home.vue')},
                {path: '/editUserInfo', name: 'editUserInfo', component: () => import('../views/editUserInfo.vue')},
                {path: '/editPassword', name: 'editPassword', component: () => import('../views/editPassword.vue')},
            ]
        }
    let roleMenu = tools.getRoleMenu()
    console.log(roleMenu)
    roleMenu.forEach(v => {
        if (!v.isPage) {
            routesCopy.children.push(
                {
                    path: v.path,
                    name: v.path,
                    component: (resolve) => require([`@/views/admin/${v.componentPath}`], resolve)
                }
            )
        }
        if (v.isPage) {
            v.list.forEach(v => {
                routesCopy.children.push(
                    {
                        path: v.path,
                        name: v.path,
                        component: (resolve) => require([`@/views/admin/${v.componentPath}`], resolve)
                    }
                )
            })
        }
    })
    router.addRoute(routesCopy)
}
AddRoutes()
export default router
