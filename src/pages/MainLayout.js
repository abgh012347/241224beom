import {NavLink, Outlet} from "react-router-dom";
import {useSelector} from "react-redux";

export default function MainLayout(){
    const adminLoginFlag=useSelector(state=>state.userInfo.adminLoginFlag);
    const adminComponent=<div><NavLink to={"/admin-login"}>관리자 로그인</NavLink>|<NavLink to={"/admin-join"}>관리자 추가</NavLink></div>;
    const mainComponent=<div><NavLink to={"/home"}>홈</NavLink>|<NavLink to={"/search"}>검색</NavLink>|
        <NavLink to={"/create-userinfo"}>고객정보추가</NavLink>|<NavLink to={"/admin-logout"}>로그아웃</NavLink></div>
    return (
        <>
            <h1>&lt;고객 관리&gt;</h1>
            {!adminLoginFlag? adminComponent:mainComponent}
            <Outlet/>
        </>
    );
}