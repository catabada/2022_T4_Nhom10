import { DateTime } from './../../entity/datetime';
import { Weather } from './../../entity/weather';
import { HttpResponse } from './../../entity/HttpResponse';
import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { Province } from './../../entity/province';
export interface WeatherState {
    id: number;
    naturalKey: string;
    dateTime: DateTime;
    province: Province;
    time: string;
    temp: number;
    cloudDescription: string;
    minTemp: number;
    maxTemp: number;
    humidity: number;
    vision: number;
    windSpd: number;
    uvIndex: number;
    atmosphereQuality: string;
    status: string;
    expiredDate: string;
}



const initialState: WeatherState[] = [{
    id: 0,
    naturalKey: '',
    province: {
        id: 0,
        name: '',
        codeName: '',
    },
    dateTime: {
        id: 0,
        date: '',
        year: 0,
        dayOfMonth: 0,
        month: '',
        monthOfYear: 0,
        dayOfYear: 0,
        day: '',
        dayOfWeek: 0,
        weekend: '',
        getDayOfYear:  0,
        weekOfYear: 0,
        quarter: 0,
        previousDay: '',
        nextDay: '',
    },
    time: '',
    temp: 0,
    cloudDescription: '',
    minTemp: 0,
    maxTemp: 0,
    humidity: 0,
    vision: 0,
    windSpd: 0,
    uvIndex: 0,
    atmosphereQuality: '',
    status: '',
    expiredDate: '',
}];

export const weathersFutureSlice = createSlice({
    name: "weathersFuture",
    initialState,
    reducers: {
        getWeathersFuture: (state, action: PayloadAction<HttpResponse<Array<Weather>>>) => {
            state = action.payload.data;
            return state;
        },
    }
})

export const { getWeathersFuture } = weathersFutureSlice.actions;
export default weathersFutureSlice.reducer;