import { Province } from './../../entity/province';
import { HttpResponse } from './../../entity/HttpResponse';
import { PayloadAction } from '@reduxjs/toolkit';
import { provinceApi } from './../../api/provinceApi';
import { GET_PROVINCES_SEARCH, GET_PROVINCE_BY_CODE } from './types';
import { takeEvery, put } from '@redux-saga/core/effects';
import { getProvincesSearch } from './provincesSearchSlice';
import { getProvinceByCode } from './provinceSlice';


export function* getProvincesSearchWorkerSaga(action: PayloadAction<any>) {
    const provincesSearch: HttpResponse<Array<Province>> = yield provinceApi.getProvinceSearch(action.payload);
    yield put(getProvincesSearch(provincesSearch))
}

export function* getProvinceWorkerSaga(action: PayloadAction<any>) {
    const provincesSearch: HttpResponse<Province> = yield provinceApi.getProvinceByCode(action.payload);
    yield put(getProvinceByCode(provincesSearch))
}

export default function* provinceWatcherSaga() {
    yield takeEvery(GET_PROVINCES_SEARCH, getProvincesSearchWorkerSaga)
    yield takeEvery(GET_PROVINCE_BY_CODE, getProvinceWorkerSaga);
}