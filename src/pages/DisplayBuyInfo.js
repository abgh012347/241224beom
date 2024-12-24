import apiClient from "../api/axiosInstance";
import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {Link} from "react-router-dom";
import errorDisplay from "../util/errorDisplay";
import {useSelector} from "react-redux";

export default function DisplayBuyInfo(){
    const {userid}=useParams();
    const [buyInfo, setBuyInfo]=useState(null);
    const userInfo=useSelector(state=>state.userInfo.userInfoList.find(t => t.userId === userid))
    useEffect(() => {
        const fetchData=async ()=>{
            try{
                const response=await apiClient.get("/buyinfo/"+userid);
                setBuyInfo(response.data);

            }catch(error){
                errorDisplay(error);
            }
        }
        fetchData();
    }, [userid]);
    const buyList=buyInfo? buyInfo.map(t=>(
        <p>
            <hr/>
            번호: {t.num} 고객이름 : {userInfo.userName} 구입상품 : {t.prodName} 가격 : {t.price} 개수 : {t.amount}
            <hr/>
        </p>
    )):
    <div><hr/>
        "구매정보가 없습니다."
        <hr/>
    </div>;
    return (
        <>
            {buyList}
            <Link to={"/display-userinfo"}>고객정보 다시보기</Link>
        </>
    );
}