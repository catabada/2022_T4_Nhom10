import { Province } from './../../entity/province';
import { HttpResponse } from './../../entity/HttpResponse';
import { createSlice, PayloadAction } from '@reduxjs/toolkit';
export interface ProvinceState {
    id: number;
    name: string;
    codeName: string;
}

const initialState: ProvinceState[] = [{
    id: 0,
    name: '',
    codeName: ''
}]

export const provincesSearchSlice = createSlice({
    name: 'provincesSearch',
    initialState,
    reducers: {
        getProvincesSearch: (state, action: PayloadAction<HttpResponse<Array<Province>>>) => {
            state = action.payload.data;
            return state;
        }
    }
})

export const { getProvincesSearch } = provincesSearchSlice.actions;
export default provincesSearchSlice.reducer;