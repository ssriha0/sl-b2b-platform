export class ProviderDetails {
    profileLogo: string;
    businessName: string;
    primaryIndustry: string;
    yearsInBusiness: string;
    overview: string;
    businessOwner: string;
    providerCount: string;
    profileStrength:string;
    credentials: Credentials;
    dispatchLocations: DispatchLocations;
    serviceOfferedList: Array<ServiceOffered>;
    lookupOffersOn : LookupOffersVO;
    coverageLocations:CoverageLocations;
    primaryIndustries:PrimaryIndustries;
    coverageHelpURL: string;
    serviceOfferHelpURL: string;
}

export class ServiceOffered {
    primaryIndustryId: string;
    primaryIndustryName: string;
    primaryIndustryType: string;
    rateCardAdded: boolean;
    displayMessage:boolean=false;
    servicesOfferedCount: number;
    servicesOptedCount: number;

    // oldServicesOptedCount: number;
    tempServiceMessage: string;
    offeredList: VendorServiceOfferedOnVO;

    serviceOfferedOn: VendorServiceOfferedOnVO;
    luOffersOnVO: Array<LookupOffersVO>;
}

export class VendorServiceOfferedOnVO {
    id: string;
    luOffersOnVO: Array<LookupOffersVO>;
}

export class LookupOffersVO {
    id: string;
    name: string
    offersOnFlag:boolean = false;
}

export class Credentials {
    credential: Array<Credential>;
}

export class Credential {
    credentialType: string;
    type: string;
    category: string;
    source: string;
    name: string;
    status: string;
    no: string;
    issueDate: string;
    expiryDate: string;
}

export class DispatchLocations {
    dispatchLocation: Array<DispatchLocation>;
}

export class DispatchLocation {
    uiIndex : number;
    locnId:string;
    street1: string;
    street2: string;
    city: string;
    state: string;
    zip: string;
    zip4: string;
    locName: String;
    updateDeleteFlag:boolean;
}

export class CoverageLocation extends DispatchLocation{
    coverageRadius:number;
    count:number;
}

export class CoverageLocations{
    coverageLocation:Array<CoverageLocation>;
} 

export class PrimaryIndustry {
    id:string;
    descr:string;
}

export class PrimaryIndustries{
      primaryIndObj:Array<PrimaryIndustry>;
}

export class ProviderUpdate{
     name:string;
     value:string;
}

export class FirmLogo{
    formData:string[];
    file:File;
}
