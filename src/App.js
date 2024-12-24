import logo from './logo.svg';
import './App.css';
import {BrowserRouter, Routes, Route} from "react-router-dom";
import MainLayout from "./pages/MainLayout";
import Home from "./pages/Home";
import DetailCondition from "./pages/DetailCondition";
import ConditionSelect from "./pages/ConditionSelect";
import DisplayUserInfo from "./pages/DisplayUserInfo";
import DisplayBuyInfo from "./pages/DisplayBuyInfo";
import CreateUserInfo from "./pages/CreateUserInfo";
import JoinUser from "./pages/JoinUser";
import AddBuyInfo from "./pages/AddBuyInfo";
import AdminJoin from "./pages/AdminJoin";
import AdminLogin from "./pages/AdminLogin";
import AdminLogout from "./pages/AdminLogout";
import {useDispatch} from "react-redux";
import {saveCsrfToken} from "./store";
import {useEffect} from "react";
import axios from "axios";
import fetchCsrfToken from "./api/fetchCsrfToken";

function App() {
    const dispatch=useDispatch();
    useEffect(()=>{
        const fetchData=async()=>{
            const token=await fetchCsrfToken();
            await dispatch(saveCsrfToken(token));
        };
        fetchData();

    },[]);


  return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<MainLayout/>}>
            <Route path="/admin-login" element={<AdminLogin/>}></Route>
            <Route path="/admin-join" element={<AdminJoin/>}></Route>
            <Route path="/home" element={<Home/>}/>
            <Route path="/search" element={<ConditionSelect/>}>
                <Route path="/search/detail-condition" element={<DetailCondition/>}/>
            </Route>
            <Route path="/display-userinfo" element={<DisplayUserInfo/>}></Route>
            <Route path="/display-buyinfo/:userid" element={<DisplayBuyInfo/>}></Route>
            <Route path="/create-userinfo" element={<CreateUserInfo/>}>
                <Route path="/create-userinfo/join" element={<JoinUser/>}></Route>
                <Route path="/create-userinfo/add-buyinfo" element={<AddBuyInfo/>}></Route>
            </Route>
          <Route path="/admin-logout" element={<AdminLogout/>}></Route>
          </Route>
        </Routes>
      </BrowserRouter>

  );
}

export default App;
