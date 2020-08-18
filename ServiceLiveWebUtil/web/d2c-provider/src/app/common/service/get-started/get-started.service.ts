import {Injectable} from '@angular/core';
import { Observable } from 'rxjs/Observable';

import {LoggerUtil} from '../../util/logger.util';
import { HttpRestAPIService } from '../http-restapi.service';
import { notOptedSkuURL } from '../../constant/get-started.constant';
import { marketFrontend, actionClass } from '../../constant/global.constant';

@Injectable()
export class GetStartedService {
    constructor(private _httpRestApiService: HttpRestAPIService, private _logger: LoggerUtil) {
    }

    getNotOptedSKUList(): Observable<any> {
        let url: string = marketFrontend + actionClass + notOptedSkuURL;
        return this._httpRestApiService.postRequest(url, '');
    }
}