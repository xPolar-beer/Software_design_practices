import axios from "axios";
import router from "@/router";
import Cookies from "js-cookie"

const request = axios.create({
    baseURL: 'http://localhost:9090/api',
    timeout: 5000
})// 新的一个request对象

//拦截器
//自请求发送前对请求做一些处理
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;chars1et=utf-8';

    const adminJson = Cookies.get('admin')
    if(adminJson) {
        //设置请求头
        config.headers['token'] = JSON.parse(adminJson).token
    }
    return config
}, error => {
    return Promise.reject(error)
});

//response拦截器
//可以在接口相应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        //兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        if(res.code === '401') {
            router.push('/login')
        }
        return res;
    },
    error => {
        console.log('err' + error)
        return Promise.reject(error)
    }
)

export default request