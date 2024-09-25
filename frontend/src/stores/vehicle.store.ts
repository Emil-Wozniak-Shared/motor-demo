import {create} from 'zustand'
import {ProblemDetails} from "../model/ProblemDetails";
import {VehicleModel} from "../model/Vehicle.model";
import request from "../lib/http";

type State = {
    vehicle: VehicleModel | null
    error: ProblemDetails | null
}

type Actions = {
    getVehicle: () => Promise<void>,
    updateVehicleMake: (makeId: string, make: string) => Promise<void>,
    updateVehicleModel: (modelId: string) => Promise<void>,
    updateVehicleProductionYear: (productionYear: number) => Promise<void>,
    updateVehicleType: (typeId: string) => Promise<void>,
}

const BASE_URL: string = "vehicle";

export const useVehicleStore = create<State & Actions>((setState, _, store) => ({
    vehicle: null,
    error: null,
    getVehicle: () => request<VehicleModel>({url: `${BASE_URL}`, method: "GET"})
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
