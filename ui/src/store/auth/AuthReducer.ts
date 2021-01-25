import {LOGIN_SUCCESS, LOGIN_FAILURE, LOGOUT, IUserDto, AuthState, AuthActionTypes } from './types';

const user: IUserDto = JSON.parse(localStorage.getItem('user') || '{}');
const initialState: AuthState = user ? { loggedIn: true, user: user } : { loggedIn: false, user: null };

const AuthReducer = (state: AuthState = initialState, action: AuthActionTypes) => {
    switch (action.type) {
        case LOGIN_SUCCESS:
            return {
                loggedIn: true,
                user: action.payload
            };
        case LOGIN_FAILURE:
            return {};
        case LOGOUT:
            return {
                loggedIn: false,
                user: null
            };
        default:
            return state
    }
}

export default AuthReducer;