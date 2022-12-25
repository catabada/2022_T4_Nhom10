import { ProvinceSearch } from './ProvinceSearch';
export interface WeatherSearch {
    naturalKey: string;
    dateTime: string;
    time: string;
    province: ProvinceSearch;
}