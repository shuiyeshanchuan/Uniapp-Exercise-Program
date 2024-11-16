
import router from "../router"
import Element from "element-ui";
const tools = {
    //获取认证信息
    getToken(){
        if(tools.isUserLogin()){
            return JSON.parse(localStorage.getItem("systemUser")).token
        }else{
            Element.Message.error("用户信息获取失败，请重新登录")
            //跳转到登录页面
            router.push({path:"/login"})
        }
    },
    //获取用户信息
    getUserInfo(){
        if(tools.isUserLogin()){
            return JSON.parse(localStorage.getItem("systemUser"))
        }else{
            Element.Message.error("")
            //跳转到登录页面
            router.push({path:"/login"})
        }
    },

    isUserLogin(){
        return localStorage.getItem("systemUser") !== null
    },
    getRoleMenu(){
        return  JSON.parse(localStorage.getItem("systemRoleMenu"))===null?[]:JSON.parse(localStorage.getItem("systemRoleMenu"))
    },
    stripHTMLTagsWithRegex(html) {
        let tagBody = "(?:<[^>]+>|)";
        let cleanText = html.replace(new RegExp(tagBody, "gi"), "");
        return cleanText;
    },
    isAdmin(){
      return   tools.getUserInfo().roleType==='ADMIN'
    },

}

export  default tools
