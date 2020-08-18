import { ErrorResponse } from '../results-modal';
import { CoverageLocation, PrimaryIndustry } from './provider-details.modal';

export class FirmDetailsResponse {
    error: ErrorResponse;
    result: GetFirmResponse;
}

export class GetFirmResponse {
    dispatchLoc: Array<FirmLocation>;
    coverageLoc: Array<CoverageLocation>
    firmDetails: FirmDetails;
    serviceOfferDetailVO: ServiceOfferingDetail;
    imageBase64: string; 
    primaryIndustryList: Array<PrimaryIndustry>;
    coverageHelpURL: string;
    serviceOfferHelpURL: string;
}

export class ServiceOfferingDetail {
    luOffersOnVO: Array<LookupOffersVO>;
    primaryIndustryDetailsVO : Array<PrimaryIndustryServiceDetail>;
}

export class FirmLocation {
    locnId: string;
    locName: string;
    street1: string;
    street2: string;
    city: string;
    state: string;
    zip: string;
    zip4: string;
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

export class LookupOffersVO {
    id: string;
    name: string
    offersOnFlag:boolean = false;
}

export class PrimaryIndustryDetailsVO {
    primaryIndustryId: string;
    primaryIndustryName: string;
    primaryIndustryType: string;
    rateCardAdded: boolean;
    servicesOfferedCount: number;
    servicesOptedCount: number;
}

export class VendorServiceOfferedOnVO {
    id: string;
    luOffersOnVO: Array<LookupOffersVO>;
}

export class FirmDetails {
    vendorId: string;
    businessName: string;
    businessDesc: string;
    firstName: string;
    lastName: string;
    numberOfEmployees: string;
    yearsInBusiness: string;
    hourlyRate: string;
    primaryIndustry: string;
    licensesList: Array<Credential>;
}

export class Credential {
    credTypeDesc: string;
    licenseName: string;
    wfStatus: string;
    expirationDate: string;
}