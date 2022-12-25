import provinceWatcherSaga from 'features/province/provinceSaga';
import weatherDetailWatcherSaga from 'features/weather-detail/weatherDetailSaga';
import weathersFutureWatcherSaga from 'features/weather-future/weatherFutureSaga';
import { all } from 'redux-saga/effects'


export default function* rootSaga() {
    yield console.log("Root Saga");
    yield all([
        weatherDetailWatcherSaga(),
        weathersFutureWatcherSaga(),
        provinceWatcherSaga(),
    ]);
}

