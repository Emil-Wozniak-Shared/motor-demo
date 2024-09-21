import React from "react";
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/Card"

const App = () => (
    <div className="md:flex">
        <div className="md:flex-shrink-0">
            <img className="rounded-lg md:w-56" src="/img/shopping.jpg" alt="Woman paying for a purchase"/>
        </div>
        <div className="mt-4 md:mt-0 md:ml-6">
            <div className="uppercase tracking-wide text-sm text-indigo-600 font-bold">
                Marketing
            </div>
            <a href="/get-started"
               className="block mt-1 text-lg leading-tight font-semibold text-gray-900 hover:underline">
                Finding customers for your new business
            </a>
            <p className="mt-2 text-gray-600">
                Getting a new business off the ground is a lot of hard work.
                Here are five ideas you can use to find your first customers.
            </p>
        </div>

        <Card>
            <CardHeader>
                <CardTitle>Card Title</CardTitle>
                <CardDescription>Card Description</CardDescription>
            </CardHeader>
            <CardContent>
                <p>Card Content</p>
            </CardContent>
            <CardFooter>
                <p>Card Footer</p>
            </CardFooter>
        </Card>

    </div>
);

export default App;
