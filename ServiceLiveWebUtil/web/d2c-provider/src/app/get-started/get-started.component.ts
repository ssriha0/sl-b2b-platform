import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';

import {LoggerUtil} from '../common/util/logger.util';
import {GetStartedService} from '../common/service/get-started/get-started.service';
import { SkusResponseObject, GetSkuDetailResponse} from '../common/modal/get-started/get-started-response.modal';
import { SkuDetails, Sku, SkuPrices } from '../common/modal/get-started/get-started.modal';

declare var jQuery: any;
@Component({
    template: require('./get-started.component.html'),
    styles: [
        require('./get-started.component.css')
    ],
    providers: [
        GetStartedService
    ]
})
export class GetStarted implements OnInit {
    private showLoader: boolean = false;
    private loadingMessage: string = '';
    private skuData: SkuDetails;

    constructor(private _getStartedService: GetStartedService, private _logger: LoggerUtil, private _route: ActivatedRoute, private _router: Router) {

    }

    public ngOnInit() {
        this._logger.log("start Init getStarted");
        this.showLoader = false;
        this.loadingMessage = 'Loading skus...';

        let redirectUrl: string = null;
        this._route.queryParams.subscribe((params: Params) => {
            this._logger.log("Query params: ", params);
            redirectUrl = params['redirect'] ? params['redirect'] : null;
        });

        if (!redirectUrl) {
            this._logger.log("redirecting to profile");
            this._router.navigate(['/profile'], { relativeTo: this._route, skipLocationChange: true })
        } else {
            if ('getStarted' != redirectUrl) {
                this._logger.log("redirecting to: ", redirectUrl);
                this._router.navigate(['/', redirectUrl], { relativeTo: this._route, skipLocationChange: true })
            } else {
                this.skuData = new SkuDetails();
                this.skuData.skus = new Array<Sku>();

                let skuResponse: SkusResponseObject;
                this._getStartedService.getNotOptedSKUList().subscribe(
                    data => {
                        this._logger.log("setting sku data...");

                        skuResponse = JSON.parse(data);
                        this._logger.log(skuResponse);

                        if (null != skuResponse.result
                            && skuResponse.result.length > 0) {

                            skuResponse.result.forEach(skudetail => {
                                let sku: Sku = new Sku();
                                sku.sku = skudetail.sku;
                                sku.skuid = skudetail.skuId;
                                sku.primaryIndustryDesc = skudetail.primaryIndustryDesc;
                                sku.primaryIndustryId = skudetail.primaryIndustryId;
                                // sku.skuName = skudetail.sku;
                                sku.categoryName = skudetail.buyerSkuCategory.categoryName;
                                skudetail.buyerSkuTasks.forEach(buyerskutask => {
                                    sku.taskComments = buyerskutask.taskComments;
                                    sku.descr = buyerskutask.luServiceTypeTemplate.descr;
                                    sku.skuName = buyerskutask.taskName;
                                });

                                sku.skuPrices = new Array<SkuPrices>();
                                this.skuData.skus.push(sku);
                            });
                        } else {
                            this._logger.log("no skus in reply.");
                        }

                        this._logger.log("done setting sku data.", this.skuData.skus);
                        this.loadingMessage = '';
                    },
                    error => {
                        jQuery("#errorModal").modal("show");
                        this._logger.log(error);
                        this.loadingMessage = '';
                    });
            }
        }
        this._logger.log("end Init getStarted");
    }

    public redirectHandle() {

    }
}

