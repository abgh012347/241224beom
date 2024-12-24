import {NavLink, Outlet} from "react-router-dom";

export default function CreateUserInfo(){
    return (
        <>
            <hr/>
            <NavLink to={"/create-userinfo/join"}>가입 정보 추가</NavLink>&nbsp;&nbsp;&nbsp;
            <NavLink to={"/create-userinfo/add-buyinfo"}>구매 기록 추가</NavLink>
            <hr/>
            <Outlet></Outlet>
        </>
    );
}