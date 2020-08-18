import { LookupOffersVO, VendorServiceOfferedOnVO } from './service-offered-detail-response.modal';
export class ServiceOfferedDetailModal {
    serviceOfferedList: Array<ServiceOffered>;
    serviceOfferedOnList:Array<ServiceOfferedOn>;
}

export class ServiceOffered {
    primaryIndustryId: string;
    primaryIndustryName: string;
    primaryIndustryType: string;
    rateCardAdded: boolean;
    displayMessage:boolean=false;
    servicesOfferedCount: number;
    servicesOptedCount: number;
    // oldServicesOptedCount:number;
    tempServiceMessage: string;
    offeredList: Array<string>;
    serviceOfferedOn: VendorServiceOfferedOnVO;
    luOffersOnVO: Array<LookupOffersVO>;
}

export class ServiceOfferedOn{
    id: string;
    name: string
    offersOnFlag:boolean=false;

}

export class UpdateServiceOfferedOnVO {

	id: string;
	luOffersOnVOforUpdate: ServiceOfferedOn;
	primaryIndustry: string;
    offersOnFlag:boolean = false;

}