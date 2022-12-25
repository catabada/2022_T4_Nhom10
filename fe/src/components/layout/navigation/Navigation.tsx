import { useAppDispatch, useAppSelector } from 'app/hooks';
import { GET_PROVINCES_SEARCH } from 'features/province/types';
import { ChangeEvent, useEffect, useRef, useState } from 'react';
import { EventEmitter } from 'stream';
import './Navigation.css'

export function Navigation() {
    const ref = useRef<HTMLInputElement>(null)
    const [showSearch, setShowSearch] = useState(true);
    const dispatch = useAppDispatch()

    const provinces = useAppSelector((state) => state.provinceSearch);


    const handleChangeSearch = () => {
        dispatch({ type: GET_PROVINCES_SEARCH, payload: { query: ref.current?.value } })
        if (provinces.length > 0) {
            setShowSearch(false);
        }

        setShowSearch(false);
    }
    const closeSearch = () => {
        setShowSearch(true);
    }

    return (
        <div className="navigation">
            <div className="container">
                <div className="row">
                    <div className="col-md-3 logo text-center">
                        <img src="https://thoitiet.vn/img/logo-header.png" alt="" />
                    </div>
                    <div className=" col-md-5 search">
                        <div className="search--wrapper">
                            <i className="bi bi-search"></i>
                            <input ref={ref} id="search" type="text" placeholder="Nhập tên địa điểm"
                                onChange={handleChangeSearch}
                            />
                            <i onClick={closeSearch} hidden={showSearch} className="bi bi-x x"></i>
                        </div>
                        <div hidden={showSearch} className="province-list--wrapper">
                            <div className="province-list">
                                {provinces.map((province) => {
                                    return <a key={province.id} href={`/${province.codeName.toLowerCase()}`} className="province-item">
                                        {province.name}
                                    </a>
                                })}

                            </div>
                        </div>
                    </div>
                    <div className="col-md-3 select-province">
                        <i className="bi bi-clipboard-data"></i>Tỉnh - Thành Phố
                        <i className="bi bi-caret-down"></i>
                    </div>
                </div>
            </div>
        </div>
    );
}