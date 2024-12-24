import axios from "axios";

import store from "../store";
const apiClient=axios.create({
    baseURL:"http://localhost:8080",
    headers:{
        "Content-Type" : "application/json",
    },
    withCredentials: true,
// timeout:3000,
});



apiClient.interceptors.request.use((config) => {
    // 1. 요청 데이터가 URLSearchParams 타입인지 확인
    if (config.data instanceof URLSearchParams) {
        config.headers["Content-Type"] = "application/x-www-form-urlencoded";
    }

    // 2. 요청 메서드가 GET이 아닌 경우 CSRF 토큰 추가
    if (config.method !== "get") {
        const csrfToken = store.getState().userInfo.csrfToken; // Redux 상태에서 csrfToken 가져오기
        config.headers["X-CSRF-TOKEN"] = csrfToken;
    }

    return config; // 수정된 config 반환
}, (error) => {
    // 요청을 가로채는 중에 에러 발생 시 처리
    return Promise.reject(error);
});


export default apiClient;