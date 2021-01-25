export interface IApiMessageResponse {
    message: string
}

export interface ICarBrand {
    brandId: number,
    brandName: string
}

export interface ICarModel {
    modelId: number,
    modelName: string,
    brandId: number
}

export interface IUserLoginPassword {
    username: string,
    password: string
}

export interface IUserRegisterForm {
    username: string,
    password: string,
    email: string,
    userAvatarUrl: string
}

export interface ICarDto {
    carId: number,
    carModelName: string,
    carBrandName: string,
    carPhotoUrl: string,
    carMileage: number,
    yearOfProduction: string,
    createdDate: string,
    lastUpdated: string
}

export interface IIssueDto {
    issueId: number,
    status: string,
    description: string,
    carId: number
    lastUpdated: string
}

export interface IIssueDetailsDto {
    issueId: number,
    amount: number | null,
    issueNumber: string,
    status: string,
    description: string,
    carDto: ICarDto,
    lastUpdated: string
}

export interface IIssueWithCarDto {
    issueId: number,
    amount: number | null,
    description: string,
    status: string,
    carBrandName: string,
    carModelName: string,
    lastUpdated: string
}

export interface ICreateIssueDto {
    userId: number,
    carId: number,
    description: string
}

export interface IIssueHistoryDto {
    issueId: number,
    username: string,
    carBrandName: string,
    carModelName: string,
    description: string,
    amount: number,
    createdDate: string,
    lastUpdated: string
}

export interface IClientDto {
    userId: number,
    username: string,
    userAvatar: string,
    carDtos: ICarDto[],
    createdDate: string,
    lastUpdated: string
}

export interface ICarEngineTypes {
    typeId: number,
    typeName: string
}

export interface ICreateCarDto {
    userId: number,
    carBrandId: number,
    carModelId: number,
    engineTypeId: number,
    carPhotoUrl: string,
    yearOfProduction: number,
    carMileage: number
}