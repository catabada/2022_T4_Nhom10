import { PayloadAction } from '@reduxjs/toolkit';
import { GET_WEATHER_DETAIL } from './types';
import { put, takeEvery } from '@redux-saga/core/effects';
import { Weather } from '../../entity/weather';
import { HttpResponse } from '../../entity/HttpResponse';
import weatherApi from 'api/weatherApi';
import { getWeatherDetail } from './weatherDetailSlice';


export function* getWeatherDetailWorkerSaga(action: PayloadAction<any>) {
    const detail: HttpResponse<Weather> = yield weatherApi.getWeatherDetail(action.payload);
    yield put(getWeatherDetail(detail));
}

export default function* weatherDetailWatcherSaga() {
    yield takeEvery(GET_WEATHER_DETAIL, getWeatherDetailWorkerSaga);
}