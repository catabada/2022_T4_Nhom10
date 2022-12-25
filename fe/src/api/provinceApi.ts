import { Province } from './../entity/province';
import { HttpResponse } from './../entity/HttpResponse';
import axiosClient from "./axiosClient";

export const provinceApi = {
    getProvinceSearch(provinceSearch: any): Promise<HttpResponse<Array<Province>>> {
        const url = '/province/search';
        return axiosClient.post(url, {
            name: provinceSearch.query
        }).then((res) => res).catch((err) => err.response)
    },
    getProvinceByCode(code: number): Promise<HttpResponse<Province>> {
        const url = `/province/${code}`;
        return axiosClient.get(url,).then((res) => res).catch((err) => err.response)
    }

}