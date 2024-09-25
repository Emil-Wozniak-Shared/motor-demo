import React, {FC} from "react";
import Driver from "../Driver";
import Vehicle from "../Vehicle";

type SidebarElementProps = {
    name: string;
    children: JSX.Element
}

const SidebarElement: FC<SidebarElementProps> = (props): JSX.Element => (
    <li>
        <span className="ml-2 text-sm tracking-wide truncate">{props.name}</span>
        <div
            className="relative flex flex-row items-center focus:outline-none hover:bg-gray-50 text-gray-600 hover:text-gray-800 border-l-4 border-transparent hover:border-indigo-500 pr-6">
            <div className="inline-flex justify-center items-center ml-4">
                {props.children}
            </div>
        </div>
    </li>
);

const Sidebar = () => (
    <div className="fixed flex flex-col top-0 left-0 bg-white h-full border-r">
        <div className="overflow-y-auto overflow-x-hidden flex-grow">
            <ul className="flex flex-col py-4 space-y-1">
                <li className="px-5">
                    <div className="flex flex-row items-center h-8">
                        <div className="text-sm font-light tracking-wide text-gray-500">Menu</div>
                    </div>
                </li>
                <SidebarElement name="Driver">
                    <Driver/>
                </SidebarElement>
                <SidebarElement name="Driver">
                    <Vehicle/>
                </SidebarElement>
            </ul>
        </div>
    </div>
);

export default Sidebar;