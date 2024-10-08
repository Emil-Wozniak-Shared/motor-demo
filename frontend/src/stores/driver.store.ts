import {create} from 'zustand'
import {DriverModel} from "../model/Driver.model";
import {GenderModel} from "../model/Gender.model";
import {ProblemDetails} from "../model/ProblemDetails";
import request from "../lib/http";

type State = {
    driver: DriverModel | null
    error: ProblemDetails | null
}

type Actions = {
    getDriver: () => Promise<void>
    updateAddressZipCode: (zipCode: string) => Promise<void>
    updateAddressCity: (city: string) => Promise<void>
    updateAddressStreet: (street: string) => Promise<void>
    updateDriverBirthDate: (birthDate: string) => Promise<void>
    updateDriverFirstName: (firstName: string) => Promise<void>
    updateDriverGender: (gender: GenderModel) => Promise<void>
    updateDriverLastName: (lastName: string) => Promise<void>
    updateDriverAccidentCount: (accidentCount: number) => Promise<void>
    updateDriverTrafficTicketsCount: (trafficTicketsCount: number) => Promise<void>
    updateDriverLicenceObtainedAtAge: (licenceObtainedAtAge: number) => Promise<void>
}

const BASE_URL = "driver";

export const useDriverStore = create<State & Actions>((setState, _, store) => ({
    driver: null,
    error: null,
    getDriver: () => request<DriverModel>({url: `${BASE_URL}`, method: "GET"})
        .then((driver) => setState({driver, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
    updateAddressZipCode: (zipCode: string) => request<DriverModel>({
        url: `${BASE_URL}`,
        method: "PUT",
        params: {zipCode}
    })
        .then((driver) => setState({driver, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
    updateAddressCity: (city: string) => request<DriverModel>({
        url: `${BASE_URL}`,
        method: "PUT",
        params: {city}
    })
        .then((driver) => setState({driver, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
    updateAddressStreet: (street: string) => request<DriverModel>({
        url: `${BASE_URL}`,
        method: "PUT",
        params: {street}
    })
        .then((driver) => setState({driver, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
    updateDriverBirthDate: (birthDate: string) => request<DriverModel>({
        url: `${BASE_URL}`,
        method: "PUT",
        params: {birthDate}
    })
        .then((driver) => setState({driver, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
    updateDriverFirstName: (firstName: string) => request<DriverModel>({
        url: `${BASE_URL}`,
        method: "PUT",
        params: {firstName}
    })
        .then((driver) => setState({driver, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
    updateDriverGender: (gender: GenderModel) =>
        request<DriverModel>({
            url: `${BASE_URL}`,
            method: "PUT",
            params: {gender}
        })
            .then((driver) => setState({driver, ...store}))
            .catch(error => {
                setState({error, ...store})
                console.error(error)
            }),
    updateDriverLastName: (lastName: string) => request<DriverModel>({
        url: `${BASE_URL}`,
        method: "PUT",
        params: {lastName}
    })
        .then((driver) => setState({driver, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
    updateDriverAccidentCount: (accidentCount: number) => request<DriverModel>({
        url: `${BASE_URL}`, method: "PUT",
        params: {accidentCount}
    })
        .then((driver) => setState({driver, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
    updateDriverLicenceObtainedAtAge: (licenceObtainedAtAge: number) => request<DriverModel>({
        url: `${BASE_URL}`,
        method: "PUT",
        params: {licenceObtainedAtAge}
    })
        .then((driver) => setState({driver, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
    updateDriverTrafficTicketsCount: (trafficTicketsCount: number) => request<DriverModel>({
        url: `${BASE_URL}`,
        method: "PUT",
        params: {trafficTicketsCount}
    })
        .then((driver) => setState({driver, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
}))
