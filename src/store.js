import {createSlice, configureStore} from "@reduxjs/toolkit";

const userInfoSlice=createSlice({
    name:"userInfo",
    initialState:{
        userInfoList:[],
        count:0,
        adminLoginFlag:false,
        csrfToken:null,
    },
    reducers: {
        addUserInfo: (state, action) => {
            state.userInfoList.push(action.payload);
            state.count++;
        },
        clearUserInfo: (state) => {
            state.userInfoList = [];
            state.count = 0;
        },
        adminLogin: (state) => {
            state.adminLoginFlag = true;
        },
        adminLogout: (state) => {
            state.adminLoginFlag = false;
            state.csrfToken=null;
        },
        saveCsrfToken:(state, action)=>{
            state.csrfToken=action.payload;
        }
    }
});

const store=configureStore({
    reducer:{
        userInfo:userInfoSlice.reducer,
    }
});

export const {addUserInfo,clearUserInfo, adminLogin,adminLogout, saveCsrfToken }=userInfoSlice.actions;
export default store;