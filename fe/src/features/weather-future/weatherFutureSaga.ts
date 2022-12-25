import { PayloadAction } from '@reduxjs/toolkit';
import { GET_WEATHERS_FUTURE } from './types';
import { put, takeEvery } from '@redux-saga/core/effects';
import { Weather } from '../../entity/weather';
import { HttpResponse } from '../../entity/HttpResponse';
import weatherApi from 'api/weatherApi';
import { getWeathersFuture } from './weatherFutureSlice';


export function* getWeathersFutureWorkerSaga(action: PayloadAction<any>) {
    const list: HttpResponse<Array<Weather>> = yield weatherApi.getWeathersFuture(action.payload);
    
    yield put(getWeathersFuture(list));
}

export default function* weathersFutureWatcherSaga() {
    
    
    yield takeEvery(GET_WEATHERS_FUTURE, getWeathersFutureWorkerSaga);
}