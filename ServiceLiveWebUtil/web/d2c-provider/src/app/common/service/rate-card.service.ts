import { Injectable } from '@angular/core';
import { URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { HttpRestAPIService } from './http-restapi.service';
import { marketFrontend, actionClass } from '../constant/global.constant';
import { getRateCardLookupDetails, getRateCardPrice, saveRateCardPrice } from '../constant/rate-card.constant';
import { LoggerUtil } from '../util/logger.util';
import { SelectServicesRequestObject } from '../modal/service-offered/select-services-request-modal';
import { RatePriceUIModal } from '../modal/rate-card/rate-card-price-responseui.modal';
import { RatePriceRequestModal } from '../modal/rate-card/rate-card-price-request.modal';
import { UpdateServiceOfferedOnVO } from '../modal/service-offered-detail/service-offered-detail.modal';
import { updateOffersOn } from '../constant/service-offer.constant';

@Injectable()
export class RateCardService {
    constructor(private _httpRestApiService: HttpRestAPIService, private _logger: LoggerUtil) {
    }

    getRateCardLookupDetails(): Observable<any> {
        let getRateCardLookupUrl: string = marketFrontend + actionClass + getRateCardLookupDetails;
        return this._httpRestApiService.getRequest(getRateCardLookupUrl);
    }

    getRateCardPrice(primaryIndustryId: string): Observable<any> {
        let body: SelectServicesRequestObject = new SelectServicesRequestObject();
        body.primaryIndustryId = primaryIndustryId;
        let getRateCardPriceUrl: string = marketFrontend + actionClass + getRateCardPrice;
        return this._httpRestApiService.postRequest(getRateCardPriceUrl, body);
    }

    saveRateCardPrice(primaryIndustryId: string, rateCardPrice: Array<RatePriceUIModal>): Observable<any> {
        let body: RatePriceRequestModal = new RatePriceRequestModal();
        this._logger.log("in service ",rateCardPrice);
        body.primaryIndustry = primaryIndustryId;
        body.rateCardPriceUIModal=rateCardPrice;
        let saveRateCardPriceUrl: string = marketFrontend + actionClass + saveRateCardPrice;
        return this._httpRestApiService.postRequest(saveRateCardPriceUrl, body);
    }

    updateOffersOn(updateServiceOfferedOnVO: UpdateServiceOfferedOnVO):Observable<any> {
        let body: UpdateServiceOfferedOnVO = updateServiceOfferedOnVO;
        let url: string = marketFrontend + actionClass + updateOffersOn;

        return this._httpRestApiService.postRequest(url, body);;

    }
}