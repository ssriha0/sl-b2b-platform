import { ErrorResponse } from '../results-modal';

export class SkusResponseObject {
    error: ErrorResponse;
    result: Array<GetSkuDetailResponse>;
}

export class GetSkuDetailResponse {
    skuId: string;
    sku: string;
    vendorServiceOfferVo: Array<VendorServiceOffering>;
    buyerSkuCategory:BuyerSkuCategory;
    buyerSkuTasks:Array<BuyerSkuTasks>;
    primaryIndustryId: number;
    primaryIndustryDesc: string;
}

export class BuyerSkuCategory{
    categoryName:string;
}

export class BuyerSkuTasks{
    luServiceTypeTemplate:LuServiceTypeTemplate;
    taskComments:string;
    taskName : string;
}

export class LuServiceTypeTemplate{
    descr:string;
}

export class VendorServiceOffering {
    id: number;
    vendorServiceOfferPriceVo: Array<VendorServiceOfferPrice>;
}

export class VendorServiceOfferPrice {
    isSelected: boolean;
    id: number;
    price: number;
    dailyLimit: number;
}