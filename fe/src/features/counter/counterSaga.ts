import { useAppDispatch } from './../../app/hooks';
import { PayloadAction } from '@reduxjs/toolkit';
import { takeEvery, delay, put } from '@redux-saga/core/effects';
import { incrementSaga, incrementSagaSuccess } from './counterSlice';

function* incrementSagaHandle(action: PayloadAction<number>) {
    console.log('Waiting for 2s');
    yield delay(2000)
    console.log('Done waiting for 2s');
    yield put(incrementSagaSuccess(action.payload));

}

export default function* counterSaga() {
    yield takeEvery(incrementSaga.toString(), incrementSagaHandle);
}

