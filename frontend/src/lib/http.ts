import axios, {AxiosError, AxiosRequestConfig, AxiosResponse, InternalAxiosRequestConfig} from "axios";

export const client = (() => {
    console.warn("root", process.env.REACT_APP_BASE_URL)
    return axios.create({
        baseURL: process.env.REACT_APP_BASE_URL,
        headers: {
            Accept: "application/json, text/plain, */*",
        },
    });
})();

const request = async <T>(options: AxiosRequestConfig) => {
    const onSuccess = (response: AxiosResponse<T>) => {
        const { data } = response;
        return data;
    };

    const onError = function (error: AxiosError) {
        return Promise.reject({
            message: error.message,
            code: error.code,
            response: error.response,
        });
    };

    return client(options).then(onSuccess).catch(onError);
};

// client.interceptors.request.use(
//     (config: InternalAxiosRequestConfig) => {
//         const accessToken = localStorage.getItem(STORAGE_TOKEN.ACCESS_TOKEN);
//         if (accessToken) {
//             config.headers.Authorization = `Bearer ${accessToken}`;
//         }
//         return config;
//     },
//     (error: AxiosError) => {
//         return Promise.reject(error);
//     },
// );

export default request;
