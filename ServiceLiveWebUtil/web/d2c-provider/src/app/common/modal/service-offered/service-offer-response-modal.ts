import { ErrorResponse } from '../results-modal';

export class ServiceOfferResponseDetails {
    error: ErrorResponse;
    result: Array<PrimaryIndustry>;
} 

export class PrimaryIndustry {
    descr: string;
    dId: number;
} 