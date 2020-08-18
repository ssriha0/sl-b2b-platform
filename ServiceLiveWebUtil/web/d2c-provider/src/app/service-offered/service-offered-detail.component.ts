/* inbuild libraries */
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';

/* custom services */
import {ServiceOfferService} from '../common/service/service-offered/service-offer.service';
import {DataStorageUtil} from '../common/util/data-storage.util';
import {LoggerUtil} from '../common/util/logger.util';

/* custom components */
import { WaitLoaderComponent } from '../common/component/wait-loader/wait-loader.component';
import { SLConsumerHeader } from '../common/component/sl-consumer-header.component';
import { SelectService } from '../select-services/select-services.component';
import { RateCard } from '../rate-card/rate-card.component';

import { ServiceOfferedDetailModal, ServiceOffered, ServiceOfferedOn, UpdateServiceOfferedOnVO } from '../common/modal/service-offered-detail/service-offered-detail.modal';
import { ServiceOfferedDetailResponse } from '../common/modal/service-offered-detail/service-offered-detail-response.modal';
import { SkuCountModal } from '../common/modal/service-offered-detail/profile-skucount.modal';
import { ErrorModal } from '../common/modal/error.modal';
import {Status} from '../common/constant/global-status.constant';
import { RateCardEventEmmiter } from '../common/modal/rate-card/rate-card-price-responseui.modal';
import { LookupOffersVO, VendorServiceOfferedOnVO } from '../common/modal/provider-profile/provider-details.modal';

declare var jQuery: any;

@Component({
    template: require('./service-offered-detail.component.html'),
    styles: [
        require('./service-offered-detail.component.css')
    ],
    providers: [
        ServiceOfferService
    ]
})
export class ServiceOfferedDetailComponent implements OnInit {
    private showLoader: boolean;

    private serviceOfferedDetail: ServiceOfferedDetailModal;
    private primaryIndustryIds: Array<string>;
    private errorpopUpModal = new ErrorModal();
    private rateCardtext: string;
    private primaryIndustry: string;
    @ViewChild('errorModal') el: ElementRef;

    constructor(private _serviceOfferService: ServiceOfferService, private _dataStorage: DataStorageUtil, private _logger: LoggerUtil) {
        this.primaryIndustry = _dataStorage.getStorage();
    }

    ngOnInit(): void {
        this.showLoader = false;
        //this.primaryIndustryIds = this._dataStorage.getStorage();
        this._logger.log("this._dataStorage.getStorage() ", this._dataStorage.getStorage());

        this.resetUIModal();
        this.loadPage();
    }

    private resetUIModal(): void {
        this.serviceOfferedDetail = new ServiceOfferedDetailModal();
        this.serviceOfferedDetail.serviceOfferedList = new Array<ServiceOffered>();
    }

    private displayError(errorModal: ErrorModal) {
        this._logger.log("displaying error popup", errorModal);
        //this.errors = errorModal.errors;
        this.showLoader = false;
        //this.errorHeading = "Error message";
        this.errorpopUpModal = errorModal;
        jQuery(this.el.nativeElement).modal("show");
    }

    loadPage(): void {
        this.showLoader = true;

        let serviceOfferedDetailResponse: ServiceOfferedDetailResponse;
        let serviceOfferedList = this.serviceOfferedDetail.serviceOfferedList = new Array<ServiceOffered>();
        let primaryIndustryIds: Array<string> = this.primaryIndustryIds;
        this._serviceOfferService.getServiceOfferedDetail(primaryIndustryIds).subscribe(
            data => {
                serviceOfferedDetailResponse = JSON.parse(data);
                this._logger.log(serviceOfferedDetailResponse);

                this.resetUIModal();
                let serviceOfferedWithPrimaryIndustry: Array<ServiceOffered> = new Array<ServiceOffered>();
                let serviceOfferedRemainingIndustry: Array<ServiceOffered> = new Array<ServiceOffered>();
                if (serviceOfferedDetailResponse && serviceOfferedDetailResponse.result && serviceOfferedDetailResponse.result.primaryIndustryDetailsVO) {
                    serviceOfferedDetailResponse.result.primaryIndustryDetailsVO.forEach(serviceOfferingDetail => {
                        let serviceOffered: ServiceOffered = new ServiceOffered();
                        serviceOffered.primaryIndustryId = serviceOfferingDetail.primaryIndustryId;
                        serviceOffered.primaryIndustryName = serviceOfferingDetail.primaryIndustryName;
                        serviceOffered.primaryIndustryType = serviceOfferingDetail.primaryIndustryType;
                        serviceOffered.rateCardAdded = serviceOfferingDetail.rateCardAdded;
                        serviceOffered.servicesOfferedCount = serviceOfferingDetail.servicesOfferedCount;
                        serviceOffered.servicesOptedCount = serviceOfferingDetail.servicesOptedCount;
                        serviceOffered.luOffersOnVO = new Array<LookupOffersVO>();
                        //serviceOffered.luOffersOnVO = serviceOfferingDetail.luOffersOnVO;
                        serviceOffered.serviceOfferedOn = new VendorServiceOfferedOnVO();
                        serviceOffered.serviceOfferedOn.luOffersOnVO = new Array<LookupOffersVO>();
                        //serviceOffered.serviceOfferedOn.luOffersOnVO = serviceOfferingDetail.vendorServiceOfferedOnVO.luOffersOnVO;
                        for (let luOffersOn of serviceOfferingDetail.luOffersOnVO) {
                            let tmp: LookupOffersVO = new LookupOffersVO();
                            tmp.id = luOffersOn.id;
                            tmp.name = luOffersOn.name;
                            tmp.offersOnFlag = luOffersOn.offersOnFlag;
                            serviceOffered.luOffersOnVO.push(tmp);
                        }
                        if (null != serviceOfferingDetail.vendorServiceOfferedOnVO.luOffersOnVO) {
                            for (let offersOn of serviceOfferingDetail.vendorServiceOfferedOnVO.luOffersOnVO) {
                                let tmp: LookupOffersVO = new LookupOffersVO();
                                tmp.id = offersOn.id;
                                tmp.name = offersOn.name;
                                tmp.offersOnFlag = offersOn.offersOnFlag;
                                serviceOffered.serviceOfferedOn.luOffersOnVO.push(tmp);
                            }
                        }

                        if (null != serviceOffered.serviceOfferedOn.luOffersOnVO) {
                            this._logger.log("entering into if block");

                            let index = 0;

                            for (let tmpLuServiceOfferedOn of serviceOffered.luOffersOnVO) {
                                for (let tmpServiceOfferedOn of serviceOffered.serviceOfferedOn.luOffersOnVO) {
                                    if (tmpLuServiceOfferedOn.id == tmpServiceOfferedOn.id && serviceOffered.luOffersOnVO[index].offersOnFlag != undefined) {
                                        this._logger.log("condition became true at index..:" + index);
                                        serviceOffered.luOffersOnVO[index].offersOnFlag = true;
                                        //this._logger.log(serviceOffered.luOffersOnVO[index].offersOnFlag);
                                        break;
                                    }
                                }
                                index++;
                            }
                        }

                        if (serviceOffered.primaryIndustryName.localeCompare(this.primaryIndustry) == 0) {
                            serviceOfferedWithPrimaryIndustry.push(serviceOffered);
                        } else {
                            serviceOfferedRemainingIndustry.push(serviceOffered)
                        }
                    });
                    serviceOfferedRemainingIndustry = serviceOfferedRemainingIndustry.sort((a: ServiceOffered, b: ServiceOffered) => {
                        return a.primaryIndustryName.localeCompare(b.primaryIndustryName);
                    });
                    this.serviceOfferedDetail.serviceOfferedList = this.serviceOfferedDetail.serviceOfferedList.concat(serviceOfferedWithPrimaryIndustry, serviceOfferedRemainingIndustry);
                }

                this.showLoader = false;
            }, error => {
                this._logger.log("Error" + error);
                this.showLoader = false;
            });
    }

    setRateCardAdded(ratecard: RateCardEventEmmiter) {
        this._logger.log("ratecard primaryIndustryId ", ratecard);
        this.serviceOfferedDetail.serviceOfferedList.forEach(service => {
            if (service.primaryIndustryId == ratecard.primaryIndustryId && ratecard.rateCardAdded == "true") {
                service.rateCardAdded = true;
                service.displayMessage = false;
            } else if (service.primaryIndustryId == ratecard.primaryIndustryId && ratecard.rateCardAdded == "false") {
                service.rateCardAdded = false;
                service.displayMessage = false;
            } else if (service.primaryIndustryId == ratecard.primaryIndustryId && ratecard.rateCardAdded == "update") {
                service.displayMessage = true;
                this.rateCardtext = "Updating rate card price...";
            }
        });
    }
    reLoadPageData(reload: boolean): void {
        if (reload) {
            this._logger.log("reloading service detail page");
            this.loadPage();
        } else {
            this.showLoader = true;
        }
    }

    getSelectedSkuCount(selectedSkuCount: SkuCountModal) {
        this._logger.log("start getSelectedSkuCount", selectedSkuCount);

        this.serviceOfferedDetail.serviceOfferedList.forEach(select => {
            if (selectedSkuCount.primaryIndustryId == select.primaryIndustryId) {
                select.tempServiceMessage = "";

                if (selectedSkuCount.status == Status.INPROGRESS) {
                    select.tempServiceMessage = "Updating service offering...";
                } else if (selectedSkuCount.status == Status.SUCCESS) {
                    select.servicesOptedCount = selectedSkuCount.selectSkuCount;
                } else if (selectedSkuCount.status == Status.ERROR) {

                } else {

                }

                return;
            }
        });

        this._logger.log("end getSelectedSkuCount");
    }
    updateOffersOn(serviceOfferedOn: ServiceOfferedOn, serviceOffered: ServiceOffered) {
        this._logger.log("selected serviceOfferedOn details;");
        this._logger.log(serviceOfferedOn.offersOnFlag);
        this._logger.log(serviceOffered.primaryIndustryId);
        let updateServiceOfferedOnVO: UpdateServiceOfferedOnVO = new UpdateServiceOfferedOnVO();
        updateServiceOfferedOnVO.luOffersOnVOforUpdate = serviceOfferedOn;
        updateServiceOfferedOnVO.offersOnFlag = serviceOfferedOn.offersOnFlag;
        updateServiceOfferedOnVO.primaryIndustry = serviceOffered.primaryIndustryId;

        this._serviceOfferService.updateOffersOn(updateServiceOfferedOnVO).subscribe(data => {
            let reply: any = JSON.parse(data);
            this._logger.log("reply from server in updateOffersOn: ", reply);
            if (reply.error) {
                serviceOfferedOn.offersOnFlag = !serviceOfferedOn.offersOnFlag;

                let errorModalTemp: ErrorModal = new ErrorModal();
                errorModalTemp.errorHeadingMessage = "Offers on";
                errorModalTemp.errors = ['Error occured while saving Offers on.'];
                this.displayError(errorModalTemp);
            }
        }, error => {
            serviceOfferedOn.offersOnFlag = !serviceOfferedOn.offersOnFlag;

            let errorModalTemp: ErrorModal = new ErrorModal();
            errorModalTemp.errorHeadingMessage = "Offers on";
            errorModalTemp.errors = ['Error occured while saving Offers on.'];
            this.displayError(errorModalTemp);
            this._logger.log("error from server in updateOffersOn: ", error);
        });




    }
}