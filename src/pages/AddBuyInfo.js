import {useState, useRef} from "react";
import errorDisplay from "../util/errorDisplay";
import apiClient from "../api/axiosInstance";


export default function AddBuyInfo(){
    const [selectUserId, setSelectUserId]=useState(false);
    const userIdRef=useRef();

    const handleClick=()=>{
        if(userIdRef.current.value===""){
            alert("아이디를 입력하세요");
            return;
        }
        setSelectUserId(true);
    }
    const handleSumbit=async (e)=>{
        e.preventDefault();
        try{
            const formData=new FormData(e.target);
            const data=Object.fromEntries(formData.entries());
            data.userId=userIdRef.current.value;
            const response=await apiClient.post("/buyinfo", data);
            alert("정상적으로 추가되었습니다.");
            setSelectUserId(false);
            userIdRef.current.value="";

        }catch(error){
            errorDisplay(error);
            alert(error.message);

        }
    }
    return(
        <>
            <form>
                고객아이디 : <input type="text" ref={userIdRef} name="userId"></input>
                <button type="button" onClick={handleClick}>아이디 선택</button>
            </form>
            {selectUserId &&
                <form onSubmit={handleSumbit}>
                    <p> 제품이름 : <input type="text" name="prodName"></input></p>
                    <p> 그룹이름 : <input type="text" name="groupName"></input></p>
                    <p> 가격 : <input type="text" name="price"></input></p>
                    <p> 구매개수 : <input type="text" name="amount"></input></p>
                    <button type="submit">입력완료</button>
                </form>}
        </>
    );
}