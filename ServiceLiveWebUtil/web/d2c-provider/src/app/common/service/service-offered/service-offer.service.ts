import { Injectable } from '@angular/core';
import { URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { marketFrontend, actionClass } from '../../constant/global.constant';
import { getPrimaryIndustry, getSkuDetails, getServiceOfferedDetail, saveDailyLimitAndPrice } from '../../constant/service-offer.constant.ts';

import { HttpRestAPIService } from '../http-restapi.service';

import { ServiceOfferResponseDetails} from '../../modal/service-offered/service-offer-response-modal';
import { SelectServiceSkuSaveRequest } from '../../modal/service-offered/select-service-save-request.modal';
import { ServiceOfferRequestDetails} from '../../modal/service-offered/service-offer-request-modal';
import { SelectServicesRequestObject} from '../../modal/service-offered/select-services-request-modal';
import { ServiceOfferedDetailRequest} from '../../modal/service-offered-detail/service-offered-detail-request.modal';
import { SelectServiceDetail } from '../../modal/service-offered/select-service-detail.modal';
import { updateOffersOn } from '../../constant/service-offer.constant';
import { UpdateServiceOfferedOnVO } from '../../modal/service-offered-detail/service-offered-detail.modal';

@Injectable()
export class ServiceOfferService {
    constructor(private _httpRestApiService: HttpRestAPIService) {
    }

    // if not used anywhere- delete
    getPrimaryIndustry(): Observable<any> {
        let getprimaryIndustryUrl: string = marketFrontend + actionClass + getPrimaryIndustry;
        return this._httpRestApiService.postRequest(getprimaryIndustryUrl, "");
    }

    getSkuDetails(primaryIndustryId: string): Observable<any> {
        let body: SelectServicesRequestObject = new SelectServicesRequestObject();
        body.primaryIndustryId = primaryIndustryId;
        let getprimaryIndustryUrl: string = marketFrontend + actionClass + getSkuDetails;
        return this._httpRestApiService.postRequest(getprimaryIndustryUrl, body);
    }

    getServiceOfferedDetail(primaryIndustryIds: Array<string>): Observable<any> {
        let body: ServiceOfferedDetailRequest = new ServiceOfferedDetailRequest();
        body.primaryIndustryIds = primaryIndustryIds;
        let getServiceOfferedDetailUrl: string = marketFrontend + actionClass + getServiceOfferedDetail;
        return this._httpRestApiService.postRequest(getServiceOfferedDetailUrl, body);
    }

    getSaveSkuDetails(selected: SelectServiceDetail): Observable<any> {
        let body : SelectServiceSkuSaveRequest = new SelectServiceSkuSaveRequest();
        body.skuDetails = selected.skus;
        body.primaryIndustryId = selected.primaryIndustryId;
        let getServiceOfferedDetailUrl: string = marketFrontend + actionClass + saveDailyLimitAndPrice;
        return this._httpRestApiService.postRequest(getServiceOfferedDetailUrl, body);
    }
    updateOffersOn(updateServiceOfferedOnVO: UpdateServiceOfferedOnVO):Observable<any> {
        let body: UpdateServiceOfferedOnVO = updateServiceOfferedOnVO;
        let url: string = marketFrontend + actionClass + updateOffersOn;

        return this._httpRestApiService.postRequest(url, body);;

    }
    ping(): void {
        this._httpRestApiService.ping();
        console.log("ServiceOfferService : ping...received");
    }
}