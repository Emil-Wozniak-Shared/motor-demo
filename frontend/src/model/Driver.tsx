import {Gender} from "./Gender";

export type Driver = {
    zipCode: string
    city: string
    street: string
    birthDate: string
    firstName: string
    gender: Gender
    lastName: string
    accidentCount: number
    trafficTicketsCount: number
    licenceObtainedAtAge: number
}
