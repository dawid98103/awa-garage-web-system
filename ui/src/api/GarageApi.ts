import axios, { AxiosResponse } from 'axios';
import { IUserLoginPassword, IClientDto, IIssueDetailsDto, ICarDto, ICreateIssueDto, IApiMessageResponse, IIssueWithCarDto, IIssueHistoryDto, IUserRegisterForm, ICarEngineTypes, ICreateCarDto, ICarModel, ICarBrand } from './types';
import { IUserDto } from '../store/auth/types';
import { authHeader } from '../helper/authHeader';

const baseUrl: string = "http://localhost:4090/garage"

export const signin = async (data: IUserLoginPassword): Promise<AxiosResponse<IUserDto>> => {
    const retrievedUser: AxiosResponse<IUserDto> = await axios.post(
        baseUrl + "/authentication/signin",
        data
    )

    return retrievedUser
}

export const signup = async (data: IUserRegisterForm): Promise<AxiosResponse<IApiMessageResponse>> => {
    const retrievedMessage: AxiosResponse<IApiMessageResponse> = await axios.post(
        baseUrl + "/authentication/signup",
        data
    )

    return retrievedMessage;
}

export const fetchUserAvatar = async (userId: number): Promise<AxiosResponse<string>> => {
    try {
        const config = {
            headers: authHeader()
        }

        const userAvatarUrl: AxiosResponse<string> = await axios.get(
            baseUrl + `/users/${userId}/avatar`,
            config
        )

        return userAvatarUrl;

    } catch (error) {
        console.log(error.message);
        throw new Error(error);
    }
}

export const fetchAllActiveIssues = async (): Promise<AxiosResponse<IIssueWithCarDto[]>> => {
    try {
        const config = {
            headers: authHeader()
        }

        const retrievedIssues: AxiosResponse<IIssueWithCarDto[]> = await axios.get(
            baseUrl + `/issues`,
            config
        )

        return retrievedIssues;
    } catch (error) {
        console.log(error.message);
        throw new Error(error);
    }
}

export const fetchAllAvailableEngineTypes = async (): Promise<AxiosResponse<ICarEngineTypes[]>> => {
    const config = {
        headers: authHeader()
    }

    const retrievedEngineTypes: AxiosResponse<ICarEngineTypes[]> = await axios.get(
        baseUrl + `/cars/engine/types`,
        config
    )

    return retrievedEngineTypes;
}

export const fetchAllCarModels = async (): Promise<AxiosResponse<ICarModel[]>> => {
    const config = {
        headers: authHeader()
    }

    const retrievedCarModels: AxiosResponse<ICarModel[]> = await axios.get(
        baseUrl + `/cars/models`,
        config
    )

    return retrievedCarModels;
}

export const fetchAllCarBrands = async (): Promise<AxiosResponse<ICarBrand[]>> => {
    const config = {
        headers: authHeader()
    }

    const retrievedCarBrands: AxiosResponse<ICarBrand[]> = await axios.get(
        baseUrl + `/cars/brands`,
        config
    )

    return retrievedCarBrands;
}

export const fetchActiveIssuesForUser = async (userId: number): Promise<AxiosResponse<IIssueWithCarDto[]>> => {
    try {
        const config = {
            headers: authHeader()
        }

        const retrievedIssues: AxiosResponse<IIssueWithCarDto[]> = await axios.get(
            baseUrl + `/issues/${userId}`,
            config
        )

        return retrievedIssues;
    } catch (error) {
        console.log(error.message);
        throw new Error(error)
    }
}

export const fetchIssueDetails = async (issueId: number): Promise<AxiosResponse<IIssueDetailsDto>> => {
    try {
        const config = {
            headers: authHeader()
        }

        const retrievedIssueDetails: AxiosResponse<IIssueDetailsDto> = await axios.get(
            baseUrl + `/issues/${issueId}/details`,
            config
        )
        return retrievedIssueDetails;
    } catch (error) {
        console.log(error.message);
        throw new Error(error);
    }
}

export const fetchIssueByNumber = async (issueNumber: string): Promise<AxiosResponse<IIssueDetailsDto>> => {
    const config = {
        headers: authHeader()
    }

    const retrievedIssueDetails: AxiosResponse<IIssueDetailsDto> = await axios.get(
        baseUrl + `/issues/byNumber/${issueNumber}`,
        config
    )

    return retrievedIssueDetails;
}

export const fetchCarsForUserId = async (userId: number): Promise<AxiosResponse<ICarDto[]>> => {
    try {
        const config = {
            headers: authHeader()
        }

        const retrievedCars: AxiosResponse<ICarDto[]> = await axios.get(
            baseUrl + `/cars/user/${userId}`,
            config
        )
        return retrievedCars;
    } catch (error) {
        console.log(error.message);
        throw new Error(error);
    }
}

export const saveNewIssue = async (newIssue: ICreateIssueDto): Promise<AxiosResponse<IApiMessageResponse>> => {
    const config = {
        headers: authHeader()
    }

    const retrievedMessage: AxiosResponse<IApiMessageResponse> = await axios.post(
        baseUrl + `/issues`,
        newIssue,
        config
    )

    return retrievedMessage
}

export const saveCarForUser = async (carToCreate: ICreateCarDto): Promise<AxiosResponse<IApiMessageResponse>> => {
    const user = JSON.parse(localStorage.getItem('user') || 'null');

    const config = {
        headers: authHeader()
    }

    const retrievedMessage: AxiosResponse<IApiMessageResponse> = await axios.post(
        baseUrl + `/cars/user/${user.userId}`,
        carToCreate,
        config
    )

    return retrievedMessage;
}

export const changeIssueStatus = async (issueId: number, statusId: number): Promise<AxiosResponse<IApiMessageResponse>> => {
    const config = {
        headers: authHeader()
    }

    const retrievedMessage: AxiosResponse<IApiMessageResponse> = await axios.patch(
        baseUrl + `/issues/${issueId}/status/${statusId}`,
        null,
        config
    )

    return retrievedMessage;
}

export const assignAmountToIssue = async (issueId: number, amount: number): Promise<AxiosResponse<IApiMessageResponse>> => {
    try {
        const config = {
            headers: authHeader()
        }

        const retrievedMessage: AxiosResponse<IApiMessageResponse> = await axios.patch(
            baseUrl + `/issues/${issueId}/amount/${amount}`,
            null,
            config
        )

        return retrievedMessage;
    } catch (error) {
        alert(error.response.data.message);
        throw new Error(error);
    }
}

export const fetchIssuesHistory = async (): Promise<AxiosResponse<IIssueHistoryDto[]>> => {
    try {
        const config = {
            headers: authHeader()
        }

        const retrievedIssuesHistory: AxiosResponse<IIssueHistoryDto[]> = await axios.get(
            baseUrl + `/issues/history`,
            config
        )

        return retrievedIssuesHistory;
    } catch (error) {
        alert(error.response.data.message);
        throw new Error(error);
    }
}

export const fetchIssuesHistoryForCar = async (carId: number): Promise<AxiosResponse<IIssueHistoryDto[]>> => {
    try {
        const config = {
            headers: authHeader()
        }

        const retrievedIssuesHistory: Promise<AxiosResponse<IIssueHistoryDto[]>> = axios.get(
            baseUrl + `/issues/history/${carId}`,
            config
        )

        return retrievedIssuesHistory;
    } catch (error) {
        alert(error.response.data.message);
        throw new Error(error);
    }
}

export const fetchUsersByRole = async (roleId: number): Promise<AxiosResponse<IClientDto[]>> => {
    try {
        const config = {
            headers: authHeader()
        }

        const retrievedClients: AxiosResponse<IClientDto[]> = await axios.get(
            baseUrl + `/users/roles/${roleId}`,
            config
        )

        return retrievedClients;
    } catch (error) {
        alert(error.response.data.message);
        throw new Error(error);
    }
}

export const deleteUserByUserId = (userId: number): Promise<AxiosResponse<IApiMessageResponse>> => {
    const config = {
        headers: authHeader()
    }

    const retrievedMessage = axios.delete(
        baseUrl + `/users/${userId}`,
        config
    )

    return retrievedMessage;
}

export const deleteCarForUser = (carId: number): Promise<AxiosResponse<IApiMessageResponse>> => {
    const user = JSON.parse(localStorage.getItem('user') || 'null');

    const config = {
        headers: authHeader()
    }

    const retrievedMessage = axios.delete(
        baseUrl + `/users/${user.userId}/cars/${carId}`,
        config
    )

    return retrievedMessage
}

