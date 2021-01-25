export const LOGIN_REQUEST = "LOGIN_REQUEST";
export const LOGIN_SUCCESS = "LOGIN_SUCCESS";
export const LOGIN_FAILURE = "LOGIN_FAILURE";
export const LOGOUT = "LOGOUT";

export interface IUserDto {
    userId: number,
    accessToken: string,
    username: string,
    password: string,
    userAvatar: string,
    roles: string[]
}
export interface AuthState {
    loggedIn: boolean,
    user: IUserDto | null
}
interface LoginRequestAction {
    type: typeof LOGIN_REQUEST
    payload: string
}
interface LoginSuccessAction {
    type: typeof LOGIN_SUCCESS
    payload: IUserDto
}
interface LoginFailureAction {
    type: typeof LOGIN_FAILURE
    payload: string
}
interface LogoutAction {
    type: typeof LOGOUT
}

export type AuthActionTypes = LoginRequestAction | LoginSuccessAction | LoginFailureAction | LogoutAction