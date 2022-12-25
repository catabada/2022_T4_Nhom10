import { useAppDispatch, useAppSelector } from 'app/hooks';
import { GET_WEATHER_DETAIL } from 'features/weather-detail/types';
import { GET_WEATHERS_FUTURE } from 'features/weather-future/types';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import './WeatherDetail.css';

interface Props {
    updateCode: (code: any) => void;
}

export function WeatherDetail({ updateCode }: Props) {
    const { code } = useParams();

    const weather = useAppSelector((state) => state.weatherDetail);
    const weathers = useAppSelector((state) => state.weatherFuture);
    const hourCurrent = new Date().getHours();
    const dayCurrent = new Date().getDate();

    const monthCurrent = new Date().getMonth() + 1;
    const yearCurrent = new Date().getFullYear();

    const qualityAtmosphere = (quality: string): number => {
        quality = quality.substring(0, quality.lastIndexOf('\r'))
        switch (quality) {
            case "Tốt":
                return 1;
            case "Khá":
                return 2;
            case "Trung bình":
                return 3;
            case "Kém":
                return 4;
            case "Rất kém":
                return 5;
            default: return 0;
        }
    }

    const separateCloudDes = (des: string): string[] => {
        let separate: string[] = des.split(' ');
        let result: string[] = [separate[0] + ' ', ''];
        let i: number = 0;
        separate.map((item, index) => {
            if (index > 0) {
                for (let word of item) {
                    if (word.toUpperCase() === word) {
                        console.log(word);
                        if (i < 1) {
                            i++;
                            break;
                        }
                    }
                }
                result[i] = result[i] + item + ' ';
            }
        })
        
        return result;

    }

    const dispatch = useAppDispatch();
    useEffect(() => {
        updateCode(code);
        dispatch({
            type: GET_WEATHER_DETAIL, payload: {
                province: {
                    codeName: code,
                },
                date: {
                    day: dayCurrent,
                    month: monthCurrent,
                    year: yearCurrent,
                },
                time: hourCurrent
            }
        })
        dispatch({
            type: GET_WEATHERS_FUTURE, payload: {
                province: {
                    codeName: code,
                },
                date: {
                    day: dayCurrent,
                    month: monthCurrent,
                    year: yearCurrent,
                },
                time: hourCurrent
            }
        })

    }, [dispatch]);
    return (
        <div className="weather-detail">
            <div className="container">
                <div className="row">
                    <div className="col-md-8">
                        <div className="detail">
                            <h4 className="title"><i className="bi bi-geo"></i>Dự báo thời tiết {weather.province.name}</h4>
                            <div className="location-auto-refresh">
                                <p>Đã cập nhật 18 phút trước</p>
                            </div>
                            <div className="description">
                                <div className="description-img">
                                    <img src="https://data.thoitiet.vn/weather/icons/03n@2x.png" alt="" />
                                </div>
                                <div className="temperature">
                                    {weather.temp}°
                                </div>
                                <div className="unit-group">
                                    <div className="unit">C</div>
                                    <div className="unit">F</div>
                                </div>
                                <div className="cloud">
                                    {}
                                    <h5>{separateCloudDes(weather.cloudDescription)[0]}</h5>
                                    <p>{separateCloudDes(weather.cloudDescription)[1]}</p>
                                </div>
                            </div>
                            <div className="parameter row">
                                <div className="col  parameter-item">
                                    <div className="parameter-icon">
                                        <i className="bi bi-thermometer"></i>
                                    </div>
                                    <div className="parameter-title">
                                        Thấp/Cao
                                        <p>{weather.minTemp}°/{weather.maxTemp}°</p>
                                    </div>

                                </div>
                                <div className="col  parameter-item">
                                    <div className="parameter-icon">
                                        <i className="bi bi-droplet"></i>
                                    </div>
                                    <div className="parameter-title">
                                        Độ ẩm
                                        <p>{weather.humidity * 100}%</p>
                                    </div>
                                </div>
                                <div className="col  parameter-item">
                                    <div className="parameter-icon">
                                        <i className="bi bi-eye-fill"></i>
                                    </div>
                                    <div className="parameter-title">
                                        Tầm nhìn
                                        <p>{weather.vision}km</p>
                                    </div>
                                </div>
                                <div className="col  parameter-item">
                                    <div className="parameter-icon">
                                        <i className="bi bi-wind"></i>
                                    </div>
                                    <div className="parameter-title">
                                        Gió
                                        <p>{weather.windSpd} km/giờ</p>
                                    </div>
                                </div>
                                <div className="col  parameter-item">
                                    <div className="parameter-icon">
                                        <i className="bi bi-gear-fill"></i>
                                    </div>
                                    <div className="parameter-title">
                                        Chỉ số UV
                                        <p>{weather.uvIndex}</p>
                                    </div>
                                </div>

                            </div>
                        </div>
                        <div className="quality">
                            <div className="row">
                                <div className="air-title col">
                                    Chất lượng không khí: <span className={`span-${qualityAtmosphere(weather.atmosphereQuality)}`}>{weather.atmosphereQuality}</span>
                                </div>
                                <div className="air-rules col">
                                    <div className={`air-api air-api-1 ${qualityAtmosphere(weather.atmosphereQuality) === 1 ? 'air-active' : ''} `} title="Chất lượng không khí đạt tiêu chuẩn, và ô nhiễm không khí coi như không hoặc gây rất ít nguy hiểm">
                                        Tốt
                                    </div>
                                    <div className={`air-api air-api-2 ${qualityAtmosphere(weather.atmosphereQuality) === 2 ? 'air-active' : ''} `} title="Chất lượng không khí ở mức chấp nhận được. Tuy nhiên, một số chất gây ô nhiễm có thể ảnh hưởng tới sức khỏe của một số ít những người nhạy cảm với không khí bị ô nhiễm">
                                        Khá
                                    </div>
                                    <div className={`air-api air-api-3 ${qualityAtmosphere(weather.atmosphereQuality) === 3 ? 'air-active' : ''} `} title="Không tốt cho người nhạy cảm. Nhóm người nhạy cảm có thể chịu ảnh hưởng sức khỏe. Số đông không có nguy cơ bị tác động." >
                                        Trung bình
                                    </div>
                                    <div className={`air-api air-api-4 ${qualityAtmosphere(weather.atmosphereQuality) === 4 ? 'air-active' : ''} `} title="Có hại cho sức khỏe với đa số người. Mỗi người đều có thể sẽ chịu tác động đến sức khỏe. Nhóm người nhạy cảm có thể bị ảnh hưởng nghiêm trọng hơn.">
                                        Kém
                                    </div>
                                    <div className={`air-api air-api-5 ${qualityAtmosphere(weather.atmosphereQuality) === 5 ? 'air-active' : ''} `} title="Rất có hại cho sức khỏe. Cảnh báo nguy hại sức khỏe nghiêm trọng. Đa số mọi người đều bị ảnh hưởng." >
                                        Rất kém
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                    <div className="col-md-4">
                        <div className="current-location">
                            <div className="location-name">
                                <span className="location-name-icon">
                                    <i className="bi bi-geo-alt"></i>
                                </span>
                                <span className="location-name-main">
                                    Thời tiết theo các giờ và ngày mai
                                </span>
                            </div>
                            <div className="time-list">
                                {weathers.map((weather, index) => {
                                    return <>
                                        <div className="time-item" key={weather.id}>
                                            <div className="title">
                                                <div className="date">{weather.dateTime.dayOfMonth}/{weather.dateTime.monthOfYear}</div>
                                                <div className="time">{weather.time}</div>
                                            </div>
                                            <div className="detail-list">
                                                <div className="detail-item">
                                                    <i className="bi bi-thermometer"></i>{weather.minTemp}°/{weather.maxTemp}°
                                                </div>
                                                <div className="detail-item">
                                                    <i className="bi bi-droplet"></i> {Math.round(weather.humidity * 100)}%
                                                </div>
                                                <div className="detail-item">
                                                    <i className="bi bi-eye-fill"></i> {weather.vision}km
                                                </div>
                                                <div className="detail-item">
                                                    <i className="bi bi-wind"></i> {weather.windSpd} km/giờ
                                                </div>
                                                <div className="detail-item">
                                                    <i className="bi bi-cloud"></i> {weather.cloudDescription}
                                                </div>
                                            </div>
                                        </div>
                                        <div className="separator-dashed"></div>
                                    </>
                                })}

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}