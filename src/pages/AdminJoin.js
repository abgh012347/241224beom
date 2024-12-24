import React, {useState} from "react";
import apiClient from "../api/axiosInstance";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import fetchCsrfToken from "../api/fetchCsrfToken";
import {saveCsrfToken} from "../store";

export default function AdminJoin(){
    const [authenNumber, setAuthenNumber]=useState("");
    const [adminName, setAdminName] = useState("");
    const [adminPassword, setAdminPassword] = useState("");
    const navigate=useNavigate();
    const csrfToken=useSelector(state=>state.userInfo.csrfToken);
    const dispatch=useDispatch();

    const handleAdminJoin= async (e)=>{
        e.preventDefault();
        try {
            if(csrfToken===null){
               const token= await fetchCsrfToken();
               await dispatch(saveCsrfToken(token));
            }
            const response = await apiClient.post("/admin-join",
                {authenNumber, adminName , adminPassword}
            );
            alert(response.data);
            navigate("/");

        } catch (error) {
            if(error.response){
                console.log(error.response.data);
                alert(error.response.data);
                navigate("/");
            }
        }
    };

    return (
        <>
             <form onSubmit={handleAdminJoin}>
                <p>인증번호 : <input
                    type="text"
                    placeholder="Authentication Number"
                    name="authenNumber"
                    value={authenNumber}
                    onChange={(e) => setAuthenNumber(e.target.value)}
                /></p>
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
                <button type="submit">추가</button>
            </form>
        </>
    );
}
