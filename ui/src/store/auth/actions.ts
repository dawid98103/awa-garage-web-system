import { LOGOUT, LOGIN_REQUEST } from './types';
import { authService } from '../../service/authService';

export function loginRequest(username: string) {
    return { type: LOGIN_REQUEST, payload: { username } }
}

export function logout() {
    authService.logout();
    return { type: LOGOUT }
}