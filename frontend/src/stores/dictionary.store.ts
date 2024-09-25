import {create} from 'zustand'
import {DriverModel} from "../model/Driver.model";
import {GenderModel} from "../model/Gender.model";
import {ProblemDetails} from "../model/ProblemDetails";
import request from "../lib/http";
import {VehicleModel} from "../model/Vehicle.model";

type State = {
    vehicle: VehicleModel | null
    error: ProblemDetails | null
}

type Actions = {
    getMakeDictionary: (productionYear: number) =>  Promise<void>,
    getModelDictionary:  (typeId: string) => Promise<void>,
    getProductionYearDictionary:  () => Promise<void>,
    getTypeDictionary:  (makeId: string) => Promise<void>
}

const BASE_URL = "vehicle";

export const useVehicleStore = create<State & Actions>((setState, _, store) => ({
    vehicle: null,
    error: null,
    getMakeDictionary: () => request<VehicleModel>({url: `${BASE_URL}`, method: "GET"})
        .then((vehicle) => setState({vehicle, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
    updateVehicleMake: (makeId: string, make: string) => request<VehicleModel>({
        url: `${BASE_URL}`,
        method: "PUT",
        params: {makeId, make}
    })
        .then((vehicle) => setState({vehicle, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
    updateVehicleModel: (modelId: string) => request<VehicleModel>({
        url: `${BASE_URL}`,
        method: "PUT",
        params: {modelId}
    })
        .then((vehicle) => setState({vehicle, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
    updateVehicleProductionYear: (productionYear: number) => request<VehicleModel>({
        url: `${BASE_URL}`,
        method: "PUT",
        params: {productionYear}
    })
        .then((vehicle) => setState({vehicle, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
    updateVehicleType: (typeId: string) => request<VehicleModel>({
        url: `${BASE_URL}`,
        method: "PUT",
        params: {typeId}
    })
        .then((vehicle) => setState({vehicle, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
}))
