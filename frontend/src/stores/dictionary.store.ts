import {create} from 'zustand'
import {ProblemDetails} from "../model/ProblemDetails";
import {DictionaryEntryModel} from "../model/Dictionary.entry.model";
import request from "../lib/http";

type State = {
    dictionaries: DictionaryEntryModel | null
    error: ProblemDetails | null
}

type Actions = {
    getMakeDictionary: (productionYear: number) =>  Promise<void>,
    getModelDictionary:  (typeId: string) => Promise<void>,
    getProductionYearDictionary:  () => Promise<void>,
    getTypeDictionary:  (makeId: string) => Promise<void>
}

const BASE_URL = "dictionaries";

export const useDictionaryStore = create<State & Actions>((setState, _, store) => ({
    dictionaries: null,
    error: null,
    getMakeDictionary: (productionYear: number) => request<DictionaryEntryModel>(
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
    getModelDictionary: (typeId: string) => request<DictionaryEntryModel>(
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
    getTypeDictionary: (makeId: string) => request<DictionaryEntryModel>(
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
}))
