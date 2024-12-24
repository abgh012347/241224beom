import apiClient from "../api/axiosInstance";
import errorDisplay from "../util/errorDisplay";
import {useState} from "react";
import {HttpStatusCode} from "axios";
import {useRef} from "react";

export default function JoinUser(){
    const [resultData, setResultData]=useState(null);
    const [check, setCheck]=useState(false);
    const userIdRef=useRef();
    const handleClick=async (e)=>{
        try{
            const urlpath="/check-id/"+userIdRef.current.value;
            const response=await apiClient.get(urlpath);
            alert(response.data);
            setCheck(true);
        }catch(error){
            if(error.response && error.response.status===HttpStatusCode.Conflict){
                alert(error.response.data);
            }else{
                errorDisplay(error);
            }
        }
    }

    const handleSubmit=async (e)=>{
        e.preventDefault();
        try{
            if(!check){
                alert("아이디 중복체크를 해주세요");
                return;
            }
            const formData = new FormData(e.target);
            const data = Object.fromEntries(formData.entries()); //폼객체를 일반 객체로 변환
            if(!data.mobile){
                data.mobile=null;
            }
            const response=await apiClient.post("/join-userinfo", data);
            setResultData(<h2>"정상적으로 저장되었습니다."</h2>);
        }catch(error){
            if(error.response && error.response.status===HttpStatusCode.Conflict){
                setResultData(<h2>{error.response.data}</h2>);
            }else{
                errorDisplay(error);
            }
        }
    }

    return (
        <>
            {!resultData && <form onSubmit={handleSubmit}>
                <p>고객아이디 : <input type="text" ref={userIdRef} name="userId" required/>
                    <button type="button" onClick={handleClick}>아이디 중복 체크</button>
                </p>
                <p>비밀번호 : <input type="password" name="password" required/></p>
                <p>고객이름 : <input type="text" name="userName" required/></p>
                <p>전화번호 : <input type="text" name="mobile"/></p>
                <p>출생년도 : <input type="text" name="birthYear" required/></p>
                <p>신장 : <input type="text" name="height" required/></p>
                <p>거주지역 : <input type="text" name="addr" required/></p>
                <p>가입일자 : <input type="date" name="joinDate" required/></p>
                <p>
                    <button type="submit">저장</button>
                </p>
            </form>}
            {resultData}
            {resultData && <button onClick={(e) => {
                setResultData(null);
                setCheck(false);
            }}>계속입력</button>}
        </>
    );
}