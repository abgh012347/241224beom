import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import apiClient from "../api/axiosInstance";
import {useDispatch, useSelector} from "react-redux";
import {adminLogin, saveCsrfToken} from "../store";
import fetchCsrfToken from "../api/fetchCsrfToken";

export default function AdminLogin(){
    const [adminName, setAdminName] = useState("");
    const [adminPassword, setAdminPassword] = useState("");
    const navigate=useNavigate();
    const dispatch=useDispatch();
    const csrfToken=useSelector(state=>state.userInfo.csrfToken);

    const handleAdminLogin= async (e)=>{
        e.preventDefault();
        try {
            if(csrfToken===null){
                const token= await fetchCsrfToken();
                await dispatch(saveCsrfToken(token));
            }
            const response = await apiClient.post("/admin-login",
                new URLSearchParams({username:adminName , password:adminPassword}),
            );

            await dispatch(saveCsrfToken(response.data.token));
            await dispatch(adminLogin());
            navigate("/home");

        } catch (error) {
            if(error.response){
                console.log(error.response.data);
                alert(error.response.data.error);
                navigate("/");
            }
        }
    };

    return (
        <>
            <form onSubmit={handleAdminLogin}>
                <p>관리자 아이디 : <input
                    type="text"
                    placeholder="Admin Name"
                    name="adminName"
                    value={adminName}
                    onChange={(e) => setAdminName(e.target.value)}
                />
                </p>
                <p>관리자 비밀번호 : <input
                    type="password"
                    placeholder="Admin Password"
                    name="password"
                    value={adminPassword}
                    onChange={(e) => setAdminPassword(e.target.value)}
                />
                </p>
                <button type="submit">로그인</button>
            </form>
        </>
    );
}
