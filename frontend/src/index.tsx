import React from 'react';
import {createRoot} from 'react-dom/client';
import './index.css';
// @ts-ignore
import App from './App.tsx';
import reportWebVitals from './reportWebVitals';

const root = createRoot(document.getElementById('root')!!);
root.render(
    <React.StrictMode>
        <App/>
    </React.StrictMode>
);

reportWebVitals();
