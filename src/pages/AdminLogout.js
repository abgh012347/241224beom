import {useEffect} from "react";
import apiClient from "../api/axiosInstance";
import {useDispatch, useSelector} from "react-redux";
import {adminLogout} from "../store";
import {useNavigate} from "react-router-dom";
import fetchCsrfToken from "../api/fetchCsrfToken";

export default function AdminLogout(){
    const dispatch=useDispatch();
    const navigate=useNavigate();
    const csrfToken=useSelector(state=>state.userInfo.csrfToken);
    useEffect(() => {
        const fetchData=async ()=>{
            try{
                if(csrfToken===null){
                    await fetchCsrfToken();
                }
                const response=await apiClient.post("/admin-logout",{});
                await dispatch(adminLogout());
                navigate("/");
            }catch(error){
                console.log(error.message);
                console.log("로그아웃오류");
            }
        };
        fetchData();
    }, []);
    return(
        <>
        </>
    );
}