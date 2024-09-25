import React, {useEffect} from 'react';
import {useDriverStore} from "../../stores/driver.store";
import {GenderModel} from "../../model/Gender.model";
import {FaFemale, FaMale} from "react-icons/fa";

const Driver = () => {
    const ctrl = useDriverStore((state) => state)
    useEffect(() => { ctrl.getDriver().finally();}, [])

    return (
        <div className="w-full max-w-xs">
            <form className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
                <div className="md:w-2/3">
                    <div className="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4">First name</div>
                    <input
                        name="firstName"
                        type="text"
                        className="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500"
                        value={ctrl.driver?.firstName}
                        onChange={(event) => ctrl.updateDriverFirstName(event.target.value)}
                    />
                </div>

                <div className="md:w-2/3">
                    <div className="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4">Last name</div>
                    <input
                        type="text"
                        name="lastName"
                        className="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500"
                        value={ctrl.driver?.lastName}
                        onChange={(event) => ctrl.updateDriverLastName(event.target.value)}
                    />
                </div>
                <div className="md:w-2/3">
                    <div className="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4">Date of birth</div>
                    <input
                        type="date"
                        name="birthDate"
                        className="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500"
                        value={ctrl.driver?.birthDate ? ctrl.driver.birthDate : "1986-01-01"}
                        onChange={(event) => ctrl.updateDriverBirthDate(event.target.value)}
                    />
                </div>

                {/*     GENDER       */}
                <div className="md:w-2/3">
                    <div className="flex me-2 justify-center mt-2">
                        <FaMale
                            className="my-2 w-8 h-8 text-blue-600 focus:ring-2 cursor-pointer"
                            width={32} height={32}
                            onClick={(_) => ctrl.updateDriverGender(GenderModel.MALE)}/>
                        <FaFemale
                            className="my-2 w-8 h-8 text-blue-600 focus:ring-2 cursor-pointer"
                            width={32} height={32}
                            onClick={(_) => ctrl.updateDriverGender(GenderModel.FEMALE)}/>
                    </div>
                </div>

                <div className="common-subsection-heading">Address</div>
                <div className="common-subsection-divider"></div>

                <div className="md:w-2/3">
                    <div className="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4">Zip code</div>
                    <input
                        name="zipCode"
                        type="text"
                        className="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500"
                        value={ctrl.driver?.zipCode}
                        onChange={(event) => ctrl.updateAddressZipCode(event.target.value)}
                    />
                </div>

                <div className="md:w-2/3">
                    <div className="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4">City</div>
                    <input
                        name="city"
                        type="text"
                        className="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500"
                        value={ctrl.driver?.city}
                        onChange={(event) => ctrl.updateAddressCity(event.target.value)}
                    />
                </div>

                <div className="md:w-2/3">
                    <div className="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4">Street</div>
                    <input
                        name="street"
                        type="text"
                        className="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500"
                        value={ctrl.driver?.street}
                        onChange={(event) => ctrl.updateAddressStreet(event.target.value)}
                    />
                </div>
            </form>
        </div>
    );
};

export default Driver;
