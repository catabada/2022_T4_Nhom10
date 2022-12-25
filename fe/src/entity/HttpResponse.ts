export interface HttpResponse<T> {
    success: boolean;
    httpStatus: string;
    httpStatusCode: number;
    timestamp: string;
    data: T;
}