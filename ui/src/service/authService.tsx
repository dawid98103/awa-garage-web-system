import { signin } from '../api/GarageApi';

export const authService = {
    login,
    logout
    // register
}

function login(username: string, password: string) {
    return signin({ username, password })
        .then(response => {
            if (response.data.accessToken) {
                localStorage.setItem("user", JSON.stringify(response.data));
            }
            return response.data
        })
}

function logout() {
    localStorage.removeItem("user")
}

// function register(username: string, password: string) {
//     return garageApi
//         .post(signup, { username, password });
// }


export default authService;