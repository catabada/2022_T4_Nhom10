export interface DateTime {
    id: number;
    date: string;
    year: number;
    month: string;
    dayOfMonth: number;
    monthOfYear: number;
    dayOfYear: number;
    day: string;
    dayOfWeek: number;
    weekend: string;
    getDayOfYear: number;
    weekOfYear: number;
    quarter: number;
    previousDay: string;
    nextDay: string;
}