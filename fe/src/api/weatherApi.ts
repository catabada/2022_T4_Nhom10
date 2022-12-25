import { WeatherSearch } from './../dto/WeatherSearch';
import { Weather } from 'entity';
import { HttpResponse } from './../entity/HttpResponse';
import axiosClient from "./axiosClient";

const weatherApi = {
    getWeatherDetail(weatherSearch: any): Promise<HttpResponse<Weather>> {
        const url = '/weather/province';
        
        return axiosClient.post(url, {
            province:  weatherSearch.province, date: weatherSearch.date, time: weatherSearch.time
        }).then((res) => res).catch((err) => err.response);
    },
    getWeathersFuture(weatherSearch: any): Promise<HttpResponse<Array<Weather>>> {
        const url = `/weather/search`;
        return axiosClient.post(url, {
            province:  weatherSearch.province, date: weatherSearch.date, time: weatherSearch.time
        }).then((res) => res).catch((err) => err.response);
    },
}


export default weatherApi;

