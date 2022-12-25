import { provinceSlice } from './../features/province/provinceSlice';
import { weathersFutureSlice } from './../features/weather-future/weatherFutureSlice';
import { weatherDetailSlice } from 'features/weather-detail/weatherDetailSlice';
import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import createSagaMiddleware from '@redux-saga/core';
import counterReducer from '../features/counter/counterSlice';
import rootSaga from './rootSaga';
import { provincesSearchSlice } from 'features/province/provincesSearchSlice';

const sagaMiddleWare = createSagaMiddleware()
export const store = configureStore({
  reducer: {
    counter: counterReducer,
    weatherDetail: weatherDetailSlice.reducer,
    weatherFuture: weathersFutureSlice.reducer,
    provinceSearch: provincesSearchSlice.reducer,
    province: provinceSlice.reducer,
  },
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(sagaMiddleWare)

});

sagaMiddleWare.run(rootSaga)

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
export type AppThunk<ReturnType = void> = ThunkAction<
  ReturnType,
  RootState,
  unknown,
  Action<string>
>;
