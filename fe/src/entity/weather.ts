import { DateTime } from './datetime';
import { Province } from "./province";

export interface Weather {
    id: number;
    naturalKey: string;
    dateTime: DateTime;
    province: Province;
    time: string;
    temp: number;
    cloudDescription: string;
    minTemp: number;
    maxTemp: number;
    humidity: number;
    vision: number;
    windSpd: number;
    uvIndex: number;
    atmosphereQuality: string;
    status: string;
    expiredDate: string;
}