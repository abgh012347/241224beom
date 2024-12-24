import axios from "axios";
import {saveCsrfToken} from "../store";
import {useDispatch} from "react-redux";

const fetchCsrfToken=async ()=>{
    try{
        const response=await axios.get('/csrf-token', { withCredentials: true });
        console.log("토큰생성 성공: ", response.data.token);
        return response.data.token;
    }catch(error){
        console.log(error);
        console.log("csrf토큰 발급에러");
    }
}

export default fetchCsrfToken;