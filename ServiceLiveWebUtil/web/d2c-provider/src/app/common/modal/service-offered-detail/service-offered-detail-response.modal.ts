import { ErrorResponse } from '../results-modal';

export class ServiceOfferedDetailResponse {
    error: ErrorResponse;
    result: ServiceOfferingDetail;
}

export class ServiceOfferingDetail {
    luOffersOnVO: Array<LookupOffersVO>;
    primaryIndustryDetailsVO: Array<PrimaryIndustryServiceDetail>;
}

export class PrimaryIndustryServiceDetail {
    primaryIndustryId: string;
    primaryIndustryName: string;
    primaryIndustryType: string;
    rateCardAdded: boolean;
    servicesOfferedCount: number;
    servicesOptedCount: number;
    vendorServiceOfferedOnVO: VendorServiceOfferedOnVO;
    luOffersOnVO: Array<LookupOffersVO>;
}

export class VendorServiceOfferedOnVO {
    id: string;
    luOffersOnVO: Array<LookupOffersVO>;
}

export class LookupOffersVO {
    id: string;
    name: string;
    offersOnFlag:boolean = false;

}

