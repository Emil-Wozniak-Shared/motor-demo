import React, {useEffect} from 'react';
import {useVehicleStore} from "../stores/vehicle.store";

const Vehicle = () => {
    const ctrl = useVehicleStore(state => state);
    const dictCtrl = useDictionaryStore(state => state);
    useEffect(() => {
        ctrl.getVehicle().finally();
        dictCtrl.getProductionYearDictionary()
            .then(function (response) {
                ctrl.productionYearDictionary = response.data;
            });
        if (ctrl.vehicle.productionYear) {
            getMakeDictionary();
        }
        if (ctrl.vehicle.makeId) {
            getTypeDictionary();
        }
        if (ctrl.vehicle.typeId) {
            getModelDictionary();
        }

    }, [])

    return (
        <>
            <div className="common-input-default">
                <div className="common-input-default__label">Production year</div>
                <select
                    className="common-input-default__select"
                    value={ctrl.vehicle?.productionYear}
                    onChange={(event) => ctrl.updateVehicleProductionYear(Number(event.target.value))}
                >
                    <option value=''>Production Year</option>
                </select>
            </div>

            <div className="common-input-default">
                <div className="common-input-default__label">Make</div>
                <select
                    className="common-input-default__select"
                    value={ctrl.vehicle?.makeId}
                    onChange={(event) => ctrl.updateVehicleMake(event.target.value, "")}
                >
                    <option value=''>Make</option>
                </select>
            </div>

            <div className="common-input-default">
                <div className="common-input-default__label">Type</div>
                <select
                    className="common-input-default__select"
                    value={ctrl.vehicle?.typeId ?? ""}
                    onChange={(event) => ctrl.updateVehicleType(event.target.value)}
                    ng-options="+(element.code) as element.name for element in ctrl.typeDictionary">
                    <option value=''>Type</option>
                </select>
            </div>

            <div className="common-input-default">
                <div className="common-input-default__label">Model</div>
                <select
                    className="common-input-default__select"
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