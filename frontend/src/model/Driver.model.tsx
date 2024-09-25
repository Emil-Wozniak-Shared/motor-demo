import {GenderModel} from "./Gender.model";

export type DriverModel = {
    zipCode: string
    city: string
    street: string
    birthDate: string
    firstName: string
    gender: GenderModel
    lastName: string
    accidentCount: number
    trafficTicketsCount: number
    licenceObtainedAtAge: number
}
