import { useAppDispatch, useAppSelector } from 'app/hooks'
import { GET_PROVINCE_BY_CODE } from 'features/province/types';
import React, { useEffect } from 'react'
import './Header.css'

interface Props {
    codeProvince: string;
}

export function Header({ codeProvince }: Props) {

    const day = new Date().getDate();
    const month = new Date().getMonth();
    const year = new Date().getFullYear();
    const hour = new Date().getHours();
    const minute = new Date().getMinutes();

    const province = useAppSelector((state) => state.province)
    const dispatch = useAppDispatch();

    const url = window.location.href;
    const codeName = url.substring(url.lastIndexOf('/') + 1)
    useEffect(() => {
        dispatch({ type: GET_PROVINCE_BY_CODE, payload: codeName });

    }, [dispatch])


    return (
        <div className="header">
            <div className="container">
                <div className="row">
                    <div className="col-md-3">
                        <div className="title">
                            <h4><i className="bi bi-geo-alt"></i> Thành phố bạn chọn: </h4>
                            <p>{province.name}</p>
                        </div>
                    </div>
                    <div className="offset-md-6 col-md-3">
                        <div className="title">
                            <h4>Giờ địa phương: </h4>
                            <p>{hour}:{minute} | {day}/{month}/{year}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}