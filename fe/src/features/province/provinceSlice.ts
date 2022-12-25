import { HttpResponse } from './../../entity/HttpResponse';
import { Province } from './../../entity/province';
import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { ProvinceState } from './provincesSearchSlice';
const initialState: ProvinceState = {
    id: 0,
    name: '',
    codeName: ''
}

export const provinceSlice = createSlice({
    name: 'provincesSearch',
    initialState,
    reducers: {
        getProvinceByCode: (state, action: PayloadAction<HttpResponse<Province>>) => {
            state = action.payload.data;
            return state;
        }
    }
})

export const { getProvinceByCode } = provinceSlice.actions;
export default provinceSlice.reducer;