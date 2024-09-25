import {create} from 'zustand'
import {ProblemDetails} from "../model/ProblemDetails";
import {DictionaryEntryModel} from "../model/Dictionary.entry.model";
import request from "../lib/http";
import {VehicleModel} from "../model/Vehicle.model";

type State = {
    dictionaries: DictionaryEntryModel | null
    error: ProblemDetails | null
}

type Actions = {
    getMakeDictionary: (productionYear: number) => Promise<void>,
    getModelDictionary: (typeId: number) => Promise<void>,
    getProductionYearDictionary: () => Promise<void>,
    getTypeDictionary: (makeId: number) => Promise<void>
    init: (vehicle: VehicleModel | null) => void
}

const BASE_URL = "dictionaries";

export const useDictionaryStore = create<State & Actions>((setState, _, store) => ({
    dictionaries: null,
    error: null,
    getMakeDictionary: (productionYear) => request<DictionaryEntryModel>(
        {
            url: `${BASE_URL}/make`,
            method: "GET",
            params: {productionYear}
        })
        .then((dictionaries) => setState({dictionaries, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
    getModelDictionary: (typeId) => request<DictionaryEntryModel>(
        {
            url: `${BASE_URL}/model`,
            method: "GET",
            params: {typeId}
        })
        .then((dictionaries) => setState({dictionaries, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
    getProductionYearDictionary: () => request<DictionaryEntryModel>(
        {
            url: `${BASE_URL}/productionYear`,
            method: "GET",
        })
        .then((dictionaries) => setState({dictionaries, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
    getTypeDictionary: (makeId) => request<DictionaryEntryModel>(
        {
            url: `${BASE_URL}/productionYear`,
            method: "GET",
            params: {makeId}
        })
        .then((dictionaries) => setState({dictionaries, ...store}))
        .catch(error => {
            setState({error, ...store})
            console.error(error)
        }),
    init: async (vehicle) => {
        await store.getState().getProductionYearDictionary()
        if (vehicle?.productionYear) await store.getState().getMakeDictionary(vehicle.productionYear);
        if (vehicle?.makeId) await store.getState().getTypeDictionary(vehicle.makeId);
        if (vehicle?.typeId) await store.getState().getModelDictionary(vehicle.typeId);
    }
}))
