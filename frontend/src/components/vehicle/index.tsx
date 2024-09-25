import React, {useEffect} from 'react';
import {useVehicleStore} from "../../stores/vehicle.store";
import {useDictionaryStore} from "../../stores/dictionary.store";

const Vehicle = () => {
    const ctrl = useVehicleStore(state => state);
    const dictCtrl = useDictionaryStore(state => state);
    useEffect(() => {
        ctrl.getVehicle().then(() =>
            dictCtrl.init(ctrl.vehicle)
        )
    }, [])

    return (
        <>
            <div className="inline-block relative w-full">
                <div className="common-input-default__label">Production year</div>
                <select
                    className="block appearance-none w-full bg-white border border-gray-400 hover:border-gray-500 px-4 py-2 pr-8 rounded shadow leading-tight focus:outline-none focus:shadow-outline"
                    role="listbox"
                    aria-labelledby="listbox-label"
                    aria-activedescendant="listbox-option-3"
                    value={ctrl.vehicle?.productionYear}
                    onChange={(event) => ctrl.updateVehicleProductionYear(Number(event.target.value))}
                >
                    <option value=''>Production Year</option>
                </select>
            </div>

            <div className="inline-block relative w-64">
                <div className="common-input-default__label">Make</div>
                <select
                    className="block appearance-none w-full bg-white border border-gray-400 hover:border-gray-500 px-4 py-2 pr-8 rounded shadow leading-tight focus:outline-none focus:shadow-outline"
                    value={ctrl.vehicle?.makeId}
                    onChange={(event) => ctrl.updateVehicleMake(event.target.value, "")}
                >
                    <option value=''>Make</option>
                </select>
            </div>

            <div className="inline-block relative w-64">
                <div className="common-input-default__label">Type</div>
                <select
                    className="block appearance-none w-full bg-white border border-gray-400 hover:border-gray-500 px-4 py-2 pr-8 rounded shadow leading-tight focus:outline-none focus:shadow-outline"
                    value={ctrl.vehicle?.typeId ?? ""}
                    onChange={(event) => ctrl.updateVehicleType(event.target.value)}
                    ng-options="+(element.code) as element.name for element in ctrl.typeDictionary">
                    <option value=''>Type</option>
                </select>
            </div>

            <div className="inline-block relative w-64">
                <div className="common-input-default__label">Model</div>
                <select
                    className="block appearance-none w-full bg-white border border-gray-400 hover:border-gray-500 px-4 py-2 pr-8 rounded shadow leading-tight focus:outline-none focus:shadow-outline"
                    value={ctrl.vehicle?.modelId ?? ""}
                    onChange={(event) => ctrl.updateVehicleModel(event.target.value)}
                    ng-options="+(element.code) as element.name for element in ctrl.modelDictionary">
                    <option value=''>Model</option>
                </select>
            </div>
        </>
    );
};

export default Vehicle;
