import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import {LoggerUtil} from '../../util/logger.util';
import { HttpRestAPIService } from '../http-restapi.service';
import { DispatchLocation, FirmLogo } from '../../modal/provider-profile/provider-details.modal';

import { firmDetailsUrl, addUpdateDispatchLocUrl, getDispatchLocationURL, addressValidationUrl, saveZipCoverageUrl, uploadFirmLogo, fetchFirmLogo, updateProfileUrl, deleteCoverageAreaUrl, getCoverageAreas } from '../../constant/profile.constant';
import { marketFrontend, actionClass, pingMarketFrontEnd } from '../../constant/global.constant';
import { UpdateServiceOfferedOnVO } from '../../modal/service-offered-detail/service-offered-detail.modal';
import { updateOffersOn } from '../../constant/service-offer.constant';

@Injectable()
export class ProviderProfileService {
    constructor(private _httpRestApiService: HttpRestAPIService, private _logger: LoggerUtil) {
    }

    getProfileDetails(): Observable<any> {
        let url: string = marketFrontend + actionClass + firmDetailsUrl;
        return this._httpRestApiService.postRequest(url, '');
    }

    addDispatchLocation(dispatchLocation: DispatchLocation): Observable<any> {

        let url: string = marketFrontend + actionClass + addUpdateDispatchLocUrl;
        return this._httpRestApiService.postRequest(url, dispatchLocation);
    }

    updateDispatchLocation(dispatchLocation: DispatchLocation): Observable<any>{
        let url: string = marketFrontend + actionClass + addUpdateDispatchLocUrl;
        return this._httpRestApiService.postRequest(url, dispatchLocation);
    }

    getDispatchLocation(): Observable<any>{
        let url: string = marketFrontend + actionClass + getDispatchLocationURL;
        return this._httpRestApiService.getRequest(url);
    }

    addressValidation(dispatchLocation: DispatchLocation): Observable<any> {
        let url: string = marketFrontend + actionClass + addressValidationUrl;
        return this._httpRestApiService.postRequest(url, dispatchLocation);
    }

    saveZipCoverageRadius(jsonString: any): Observable<any> {
        let url: string = marketFrontend + actionClass + saveZipCoverageUrl;
        return this._httpRestApiService.saveZipCoverageRadius(url, jsonString);
    }
    deleteCoverageArea(jsonString: any): Observable<any> {
      let url: string = marketFrontend + actionClass + deleteCoverageAreaUrl;
        console.log("delete coverage area");
         console.log(jsonString);
        return this._httpRestApiService.deleteCoverageArea(url, jsonString);
    }
    updateProfile(provderUpdate:any): Observable<any> {
        let url: string = marketFrontend + actionClass + updateProfileUrl;
        return this._httpRestApiService.postRequest(url, provderUpdate);
    }
    uploadFirmLogo(formData: string[], file: any): Observable<any> {
        let body: FirmLogo = new FirmLogo();
        body.file = file;
        body.formData = formData;
        let uploadFirmLogoUrl: string = marketFrontend + actionClass + uploadFirmLogo;
        return this._httpRestApiService.postRequest(uploadFirmLogoUrl, body);
    }

    fetchFirmLogo(): Observable<any> {
         let fetchFirmLogoUrl: string = marketFrontend + actionClass + fetchFirmLogo;
         return this._httpRestApiService.getRequest(fetchFirmLogoUrl);
    }

    getCoverageAreas():Observable<any> {
        let url: string = marketFrontend + actionClass + getCoverageAreas;
        return this._httpRestApiService.postRequest(url, '');
    }

    pingAJAX(): void {
        let pingUrl: string = marketFrontend + actionClass + pingMarketFrontEnd;
        this._httpRestApiService.postRequest(pingUrl, '').subscribe(
            data => {
                console.log(data);
            }, error => {
                console.log(error);
            });
    }
}