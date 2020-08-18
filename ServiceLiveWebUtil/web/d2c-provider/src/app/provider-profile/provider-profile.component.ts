/* inbuild libraries */
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Observable }       from 'rxjs/Observable';

/* custom services */
import {ProviderProfileService} from '../common/service/provider-profile/provider-profile.service';
import {LoggerUtil} from '../common/util/logger.util';

/* custom components */
import { WaitLoaderComponent } from '../common/component/wait-loader/wait-loader.component';
import { SLConsumerHeader } from '../common/component/sl-consumer-header.component';
import { ProfileImage } from './profile-image/profile-image.component';
import { SelectService } from '../select-services/select-services.component';
import {ProviderZIPCodeCoverageComponent} from '../provider-coverage/provider-coverage.component';

/* custom dto/pojo/bean/modal objects */
import { ProviderDetails, Credentials, Credential, DispatchLocations, DispatchLocation, VendorServiceOfferedOnVO, ServiceOffered, CoverageLocations, CoverageLocation, PrimaryIndustry, PrimaryIndustries, ProviderUpdate, LookupOffersVO } from '../common/modal/provider-profile/provider-details.modal';
import { FirmDetailsResponse, GetFirmResponse, FirmDetails } from '../common/modal/provider-profile/firm-details-response-modal';
import { ErrorResponse } from '../common/modal/results-modal';
import { ErrorModal } from '../common/modal/error.modal';
import { SkuCountModal } from '../common/modal/service-offered-detail/profile-skucount.modal';
import { Status } from '../common/constant/global-status.constant';
import { RateCardEventEmmiter } from '../common/modal/rate-card/rate-card-price-responseui.modal';
import { ServiceOfferedOn, UpdateServiceOfferedOnVO } from '../common/modal/service-offered-detail/service-offered-detail.modal';
import { DataStorageUtil } from '../common/util/data-storage.util';
import { ComponentAddressUI } from './../common/modal/provider-profile/address-validation-responseUI-modal';
import { ProviderAddressValidation, AddressValidation } from './../common/modal/provider-profile/address-validation-response-modal';

declare var jQuery: any;

@Component({
    template: require('./provider-profile.component.html'),
    styles: [
        require('./provider-profile.component.css')
    ],
    providers: [
        ProviderProfileService
    ],
})
export class ProviderProfile implements OnInit {
    private letComponentsChangeLoader: boolean = false;
    private showProfileLoader: boolean;
    private profileImageLocation: string;
    private providerDetails: ProviderDetails;
    private firmId: string;
    private selectedCoverageLoc: DispatchLocation = new DispatchLocation();
    private coverageRadius: number = 0;
    private coverageZip: string;
    private locId: string;
    private dispatchLocation: DispatchLocation;
    private providerUpdate: ProviderUpdate;
    private dropDownCoverageZipCodes = new Array<DispatchLocation>();
    private _primaryIndustry: PrimaryIndustry = new PrimaryIndustry();
    //private errors: Array<string> = new Array<string>();
    private errorHeading: string;
    private message: string = null;
    private showDispatchMessage: string = null;
    private dispatchLocns = Array<DispatchLocation>();
    private errorpopUpModal = new ErrorModal();
    private oldSelectedSku: number;
    private maxWordForOverview = 1000;
    private deleteRadius: number;
    private deleteLocId: string;
    private coverageLocations: CoverageLocation[];
    private profileStrength: number = 0;
    private luOffersOn: Array<LookupOffersVO>;
    private logoPresent: boolean = false;
    private rateCardtext: string;
    private componentAddress: Array<ComponentAddressUI>;
    @ViewChild('errorModal') el: ElementRef;
    @ViewChild('Overview') el2: ElementRef;

    constructor(private _proviverProfileService: ProviderProfileService, private _dataStorage: DataStorageUtil, private _logger: LoggerUtil) {
    }

    ngOnInit(): void {
        this.showProfileLoader = false;
        this.resetUIModal();

        this._logger.log("calling reload from ngOnInit");
        this.loadPage();
    }


    private resetUIModal(): void {
        this.dispatchLocation = new DispatchLocation();
        this.providerDetails = new ProviderDetails();
        this.providerDetails.credentials = new Credentials();
        this.providerDetails.credentials.credential = new Array<Credential>();
        this.providerDetails.serviceOfferedList = new Array<ServiceOffered>();
        this.firmId = "";
    }

    private displayError(errorModal: ErrorModal) {
        this._logger.log("displaying error popup", errorModal);
        //this.errors = errorModal.errors;
        this.showProfileLoader = false;
        this.errorHeading = "Error message";
        this.errorpopUpModal = errorModal;
        jQuery(this.el.nativeElement).modal("show");
    }

    loadPage(): void {
        let firmDetailsResponse: FirmDetailsResponse;

        this.showProfileLoader = true;
        this.letComponentsChangeLoader = false;
        this._proviverProfileService.getProfileDetails().subscribe(
            data => {
                firmDetailsResponse = JSON.parse(data);
                this._logger.log(firmDetailsResponse);

                this.resetUIModal();

                if (firmDetailsResponse.result) {

                    if (null != firmDetailsResponse.result.firmDetails
                        && null != firmDetailsResponse.result.firmDetails.vendorId) {

                        let firms: FirmDetails = firmDetailsResponse.result.firmDetails;
                        this.providerDetails.businessName = firms.businessName;

                        this.providerDetails.businessOwner = "";
                        if (firms.firstName) {
                            this.providerDetails.businessOwner = firms.firstName + " ";
                        }
                        if (firms.lastName) {
                            this.providerDetails.businessOwner = this.providerDetails.businessOwner + firms.lastName;
                        }

                        this.providerDetails.overview = firms.businessDesc;
                        this.providerDetails.primaryIndustry = firms.primaryIndustry;
                        this._dataStorage.setStorage(firms.primaryIndustry);
                        this.providerDetails.yearsInBusiness = null != firms.yearsInBusiness
                            ? Math.floor(parseInt(firms.yearsInBusiness)).toString() : "0";
                        this.providerDetails.credentials = new Credentials();
                        this.providerDetails.credentials.credential = new Array<Credential>();

                        if (firms.licensesList) {
                            firms.licensesList.forEach(data => {
                                let displayCredential: Credential = new Credential();
                                displayCredential.type = data.credTypeDesc;
                                displayCredential.name = data.licenseName;
                                displayCredential.status = data.wfStatus;
                                if (data.expirationDate) {
                                    let expiryDate: Date = new Date(data.expirationDate);
                                    displayCredential.expiryDate = (expiryDate.getMonth() + 1)
                                        + "/" + expiryDate.getDate()
                                        + "/" + expiryDate.getFullYear().toString().substr(2);
                                }

                                this.providerDetails.credentials.credential.push(displayCredential);
                            });
                        }

                        this.providerDetails.dispatchLocations = new DispatchLocations();
                        this.providerDetails.dispatchLocations.dispatchLocation = new Array<DispatchLocation>();
                        this.providerDetails.coverageLocations = new CoverageLocations();
                        this.providerDetails.coverageLocations.coverageLocation = new Array<CoverageLocation>();
                        this.providerDetails.primaryIndustries = new PrimaryIndustries();
                        this.providerDetails.primaryIndustries.primaryIndObj = new Array<PrimaryIndustry>();

                        this.providerDetails.profileLogo = firmDetailsResponse.result.imageBase64;
                        this.providerDetails.serviceOfferHelpURL = firmDetailsResponse.result.serviceOfferHelpURL ? firmDetailsResponse.result.serviceOfferHelpURL : "#";
                        this.providerDetails.coverageHelpURL = firmDetailsResponse.result.coverageHelpURL ? firmDetailsResponse.result.coverageHelpURL : "#";

                        if (null != firmDetailsResponse.result.dispatchLoc) {
                            firmDetailsResponse.result.dispatchLoc.forEach(firmLoc => {
                                let newDispatchLocation: DispatchLocation = new DispatchLocation();
                                newDispatchLocation.locnId = firmLoc.locnId;
                                newDispatchLocation.locName = firmLoc.locName;
                                newDispatchLocation.city = firmLoc.city;
                                newDispatchLocation.state = firmLoc.state;
                                newDispatchLocation.street1 = firmLoc.street1;
                                newDispatchLocation.street2 = firmLoc.street2;
                                newDispatchLocation.zip = firmLoc.zip;
                                newDispatchLocation.zip4 = firmLoc.zip4;

                                this.providerDetails.dispatchLocations.dispatchLocation.push(newDispatchLocation);
                            });
                        } else {
                            this._logger.log("dispatch Location list is empty.");
                        }

                        if (null != firmDetailsResponse.result.coverageLoc) {
                            firmDetailsResponse.result.coverageLoc.forEach(cvgLoc => {

                                let newCoverageLocation: CoverageLocation = new CoverageLocation();

                                newCoverageLocation.locName = cvgLoc.locName;

                                newCoverageLocation.city = cvgLoc.city;
                                newCoverageLocation.state = cvgLoc.state;
                                newCoverageLocation.zip = cvgLoc.zip;
                                newCoverageLocation.locnId = cvgLoc.locnId;

                                newCoverageLocation.coverageRadius = cvgLoc.coverageRadius;
                                newCoverageLocation.count = cvgLoc.count;

                                this.providerDetails.coverageLocations.coverageLocation.push(newCoverageLocation);

                            });
                        } else {
                            this._logger.log("Coverage Location list is empty.");
                        }

                        if (null != firmDetailsResponse.result.primaryIndustryList) {

                            let primaryIndBlank: PrimaryIndustry = new PrimaryIndustry();
                            primaryIndBlank.id = '';
                            primaryIndBlank.descr = '';
                            this._primaryIndustry = primaryIndBlank;
                            this.providerDetails.primaryIndustries.primaryIndObj.push(primaryIndBlank);

                            firmDetailsResponse.result.primaryIndustryList.forEach(primaryIndustry => {
                                let primaryInd: PrimaryIndustry = new PrimaryIndustry();

                                primaryInd.id = primaryIndustry.id;
                                primaryInd.descr = primaryIndustry.descr;
                                this.providerDetails.primaryIndustries.primaryIndObj.push(primaryInd);
                            });
                        }
                    } else {
                        this._logger.log("Reply from server is not having firm details");
                    }

                    if (null != firmDetailsResponse.result.serviceOfferDetailVO && firmDetailsResponse.result.serviceOfferDetailVO) {

                        this.luOffersOn = new Array<LookupOffersVO>();
                        if (firmDetailsResponse.result.serviceOfferDetailVO.luOffersOnVO) {
                            firmDetailsResponse.result.serviceOfferDetailVO.luOffersOnVO.forEach(luData => {
                                let lookupOffersTemp: LookupOffersVO = new LookupOffersVO();
                                lookupOffersTemp.id = luData.id;
                                lookupOffersTemp.name = luData.name;
                                this.luOffersOn.push(lookupOffersTemp);
                            });
                        } else {
                            this._logger.log("luOffers is blank in reply.");
                        }

                        if (firmDetailsResponse.result.serviceOfferDetailVO.primaryIndustryDetailsVO) {

                            firmDetailsResponse.result.serviceOfferDetailVO.primaryIndustryDetailsVO.forEach(serviceOfferingDetail => {
                                if (serviceOfferingDetail.servicesOptedCount > 0) {

                                    let serviceOffered: ServiceOffered = new ServiceOffered();
                                    serviceOffered.primaryIndustryId = serviceOfferingDetail.primaryIndustryId;
                                    serviceOffered.primaryIndustryName = serviceOfferingDetail.primaryIndustryName;
                                    serviceOffered.primaryIndustryType = serviceOfferingDetail.primaryIndustryType;
                                    serviceOffered.rateCardAdded = serviceOfferingDetail.rateCardAdded;
                                    serviceOffered.servicesOfferedCount = serviceOfferingDetail.servicesOfferedCount;
                                    serviceOffered.servicesOptedCount = serviceOfferingDetail.servicesOptedCount;

                                    /* serviceOffered.offeredList = new VendorServiceOfferedOnVO();
                                    if (serviceOfferingDetail.vendorServiceOfferedOnVO) {

                                        serviceOffered.offeredList.luOffersOnVO = new Array<LookupOffersVO>();
                                        if (serviceOfferingDetail.vendorServiceOfferedOnVO.luOffersOnVO) {
                                            serviceOfferingDetail.vendorServiceOfferedOnVO.luOffersOnVO.forEach(offersOnTemp => {
                                                let lookupOffersVONew : LookupOffersVO = new LookupOffersVO(); 
                                                lookupOffersVONew.id = offersOnTemp.id;
                                                lookupOffersVONew.name = offersOnTemp.name;
                                                serviceOffered.offeredList.luOffersOnVO.push(lookupOffersVONew);
                                            });
                                        } else {
                                            this._logger.log("no offers on is selected by provider.");
                                        }
                                    } else {
                                         this._logger.log("offers on list in blank");
                                    } */

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


                                    this.providerDetails.serviceOfferedList.push(serviceOffered);
                                }
                            });
                        } else {
                            this._logger.log("service offerings list is empty.-1");
                        }
                    } else {
                        this._logger.log("service offerings list is empty.-2");
                    }
                } else {
                    this._logger.log("Error while loading firm details.");
                    let loadErrorModal: ErrorModal = new ErrorModal();
                    loadErrorModal.errorHeadingMessage = "Profile";
                    loadErrorModal.errors = ["Error occured! Please reload the page."];
                    this.displayError(loadErrorModal);
                }
                this.showProfileLoader = false;
                this.letComponentsChangeLoader = true;

                this.setProfileStrength();
            },
            error => {
                this._logger.log("Error: ", JSON.parse(error));
                firmDetailsResponse = new FirmDetailsResponse();
                firmDetailsResponse.error = new ErrorResponse();
                firmDetailsResponse.error.code = "9999";
                firmDetailsResponse.error.message = error;
                this.showProfileLoader = false;
                this.letComponentsChangeLoader = true;
                this.errorpopUpModal.errors = ["Error while retriving data"];
                this.errorpopUpModal.errorHeadingMessage = "Profile Details";
                this.displayError(this.errorpopUpModal);
            }
        );
    }

    providerLogoExist(logoExist: boolean): void {
        this.logoPresent = logoExist;
        this.setProfileStrength();
    }

    setProfileStrength(): void {
        this._logger.log("start setProfileStrength");
        this.profileStrength = 0;

        if (this.logoPresent) {
            this._logger.log("setProfileStrength logoPresent");
            this.profileStrength += 20;
        }

        if (this.providerDetails.overview && this.providerDetails.overview.trim()) {
            this._logger.log("setProfileStrength overview");
            this.profileStrength += 20;
        }

        if (this.providerDetails.dispatchLocations
            && this.providerDetails.dispatchLocations.dispatchLocation
            && this.providerDetails.dispatchLocations.dispatchLocation.length > 0) {
            this._logger.log("setProfileStrength dispatchLocation");
            this.profileStrength += 20;
        }

        if (this.providerDetails.coverageLocations
            && this.providerDetails.coverageLocations.coverageLocation.length > 0) {
            this._logger.log("setProfileStrength coverageLocation");
            this.profileStrength += 20;
        }

        if (this.providerDetails.serviceOfferedList
            && this.providerDetails.serviceOfferedList.length > 0) {
            this._logger.log("setProfileStrength serviceOfferedList");
            this.profileStrength += 20;
        }

        // let percent: number = this.profileStrength;
        // let pixels: number = (percent / 100) * 90;
        // jQuery(".fill").css('top', (90 - pixels) + "px");
        // jQuery(".fill").css('height', pixels + "px");
        // jQuery(".line").css('top', (90 - pixels) + "px");

        this._logger.log("end setProfileStrength", this.profileStrength);
    }

    // saveZipCoverageRadius() {
    //     let jsonString: string;
    //     this.locId =this.selectedCoverageLoc.locnId; 
    //     jsonString = JSON.stringify({ locId: this.locId, coverageRadius: this.coverageRadius });
    //     this._proviverProfileService.saveZipCoverageRadius(jsonString).subscribe(
    //         data => {
    //             console.log(data);
    //         }, error => {
    //             console.log(error);
    //         });
    // }

    resetDispatchModalData(): void {
        this.dispatchLocation = new DispatchLocation();
    }

    changeCoverageDropDown(selected: any) {
        this._logger.log("selected coverage area");
        this._logger.log(selected);
        this.selectedCoverageLoc = selected;
        this.coverageZip = this.selectedCoverageLoc.zip;
        this.locId = this.selectedCoverageLoc.locnId;
    }

    changePrimaryDropDown(selected: any) {
        this._logger.log("selected primary industry");
        this._logger.log(selected);
        this._primaryIndustry = selected;
    }

    incrementCoverageRadius() {
        if (this.coverageRadius < 100) {
            this.coverageRadius++;
        }

    }

    decrementCoverageRadius() {
        if (this.coverageRadius > 0) {
            this.coverageRadius--;
        }
    }

    validateCoverageRadius() {
        if (this.coverageRadius && this.coverageRadius < 0) {
            this.coverageRadius = 0;
        }
        this._logger.log(this.coverageRadius);
    }

    saveSuggestedDispatchLoc(updateFlag: boolean, componentAddressUI: ComponentAddressUI): void {
        let street1:string, street2: string;
        if (null != componentAddressUI.city_name) {
            this.dispatchLocation.city = componentAddressUI.city_name;
        } else {
            this.dispatchLocation.city = " ";
        }
        if (null != componentAddressUI.state_abbreviation) {
            this.dispatchLocation.state = componentAddressUI.state_abbreviation;
        } else {
            this.dispatchLocation.state = " ";
        }

        if (null != componentAddressUI.street_name) {
            street1 = componentAddressUI.street_name;
        } else {
            street1 = " ";
        }

        if (null != componentAddressUI.street_suffix) {
            street2 = componentAddressUI.street_suffix;
        } else {
            street2 = " ";
        }
        this.dispatchLocation.street1 = street1 + ' ' + street2;


        if (null != componentAddressUI.zipcode) {
            this.dispatchLocation.zip = componentAddressUI.zipcode;
        } else {
            this.dispatchLocation.zip = " ";
        }

        if (null != componentAddressUI.plus4_code) {
            this.dispatchLocation.zip4 = componentAddressUI.plus4_code;
        } else {
            this.dispatchLocation.zip4 = " ";
        }
        this.addDispatchLocation(updateFlag);
    }

    addDispatchLocation(updateFlag: boolean) {
        this._logger.log("Inside addDispatchLocation");

        jQuery("#validAddress").modal("hide");
        jQuery("#nosuggession").modal("hide");
        jQuery("#updatedispatchLocationModal").modal("hide");
        jQuery("#dispatchLocationModal").modal("hide");

        if (updateFlag && null == this.dispatchLocation.locnId) {
            this.showDispatchMessage = "Saving dispatch location...";
        } else if (updateFlag) {
            this.showDispatchMessage = "Updating dispatch location...";
            for (let tmpDispatch of this.providerDetails.dispatchLocations.dispatchLocation) {
                if (tmpDispatch.locnId == this.dispatchLocation.locnId && tmpDispatch.zip != this.dispatchLocation.zip
                    && this.providerDetails.coverageLocations.coverageLocation.length > 0) {
                    for (let tmpCvg of this.providerDetails.coverageLocations.coverageLocation) {
                        if (tmpCvg.locnId == this.dispatchLocation.locnId) {
                            this.message = "Deleting coverage area for corresponding dispatch location zip code..."
                            this.deleteCoverageArea(tmpCvg.coverageRadius, tmpCvg.locnId);
                            break;
                        }
                    }
                    break;
                }
            }
        } else {
            this.showDispatchMessage = "Deleting dispatch location...";
            if (this.providerDetails.coverageLocations.coverageLocation.length > 0) {
                for (let tmpCvg of this.providerDetails.coverageLocations.coverageLocation) {
                    if (tmpCvg.locnId == this.dispatchLocation.locnId) {
                        this.message = "Deleting coverage area for corresponding dispatch location zip code..."
                        break;
                    }
                }
            }
        }
        if (null != this.dispatchLocation.zip) {
            this.dispatchLocation.updateDeleteFlag = updateFlag;
            //this._logger.log("sending for save: ",this.dispatchLocation);
            this._proviverProfileService.addDispatchLocation(this.dispatchLocation).subscribe(
                data => {
                    let reply = JSON.parse(data);
                    //this._logger.log(reply);
                    if (reply.result) {
                        this.getDispatchLocation();
                        this.getCoverageAreas();
                        // this.setProfileStrength();
                    } else {
                        this._logger.log(reply);
                        this.showDispatchMessage = null;
                        this.errorpopUpModal.errors = ["Please provide a valid zip."];
                        this.errorpopUpModal.errorHeadingMessage = "Dispatch Location";
                        this.displayError(this.errorpopUpModal);
                    }
                }, error => {
                    this._logger.log(error);
                    this.showDispatchMessage = null;
                    this.message = null;
                    this.errorpopUpModal.errors = ["Error occured while saving zip."];
                    this.errorpopUpModal.errorHeadingMessage = "Dispatch Location";
                    this.displayError(this.errorpopUpModal);
                }, () => {
                    this.setProfileStrength();
                });
        }
    }

    fillUpdateDispatchLocationModal(selected: DispatchLocation, index: number) {
        this._logger.log("updating dispatch location");

        this.dispatchLocation = new DispatchLocation();
        this.dispatchLocation.uiIndex = index;
        this.dispatchLocation.city = selected.city;
        this.dispatchLocation.locName = selected.locName;
        this.dispatchLocation.locnId = selected.locnId;
        this.dispatchLocation.state = selected.state;
        this.dispatchLocation.street1 = selected.street1;
        this.dispatchLocation.street2 = selected.street2;
        this.dispatchLocation.updateDeleteFlag = selected.updateDeleteFlag;
        this.dispatchLocation.zip = selected.zip;
        this.dispatchLocation.zip4 = selected.zip4;

        this._logger.log(this.dispatchLocation);
        this._logger.log(this.dispatchLocation.locnId);
    }

    getDispatchLocation() {
        this._logger.log("Inside getDispatchLocation");
        this._proviverProfileService.getDispatchLocation().subscribe(
            data => {
                //this._logger.log("return dispatch loc", data);
                this.dispatchLocns = JSON.parse(data).result.dispatchLocs;
                this.providerDetails.dispatchLocations.dispatchLocation = this.dispatchLocns;
                this.resetDispatchModalData();
                this.showDispatchMessage = null;
            }, error => {
                this._logger.log(error);
            }, () => {
                this.setProfileStrength();
            });
    }

    // method to validate Dispatch Location 
    addressValidation(): void {
        this._logger.log("Inside addressValidation - " + JSON.stringify(this.dispatchLocation));

        let duplicateExist: boolean = false;
        this.providerDetails.dispatchLocations.dispatchLocation.forEach(dispatchLocTemp => {
            if (dispatchLocTemp.zip == this.dispatchLocation.zip && dispatchLocTemp.locnId != this.dispatchLocation.locnId) {
                duplicateExist = true;
            }
        });

        if (duplicateExist) {
            let errorModalTemp: ErrorModal = new ErrorModal();
            errorModalTemp.errorHeadingMessage = "Warning!";
            errorModalTemp.errors = ['Duplicate Dispatch Location! The zipcode you have entered already exists!'];
            this.displayError(errorModalTemp);
            return;
        }

        let providerAddressValidation: ProviderAddressValidation;
        this.componentAddress = new Array<ComponentAddressUI>();
        this._proviverProfileService.addressValidation(this.dispatchLocation).subscribe(
            data => {
                providerAddressValidation = JSON.parse(data);
                this._logger.log(providerAddressValidation);

                if (null != providerAddressValidation.result) {
                    providerAddressValidation.result.forEach(addressValidation => {
                        let componentAddressUI: ComponentAddressUI = new ComponentAddressUI();
                        componentAddressUI.city_name = addressValidation.city_name;
                        componentAddressUI.plus4_code = addressValidation.plus4_code;
                        componentAddressUI.state_abbreviation = addressValidation.state_abbreviation;
                        componentAddressUI.street_name = addressValidation.street_name;
                        componentAddressUI.street_suffix = addressValidation.street_suffix;
                        componentAddressUI.zipcode = addressValidation.zipcode;
                        this.componentAddress.push(componentAddressUI);
                    });
                }
                this._logger.log("this.componentAddress :", this.componentAddress);
                if (this.componentAddress.length > 0) {
                    jQuery("#validAddress").modal("show");
                } else {
                    jQuery("#nosuggession").modal("show");
                }
            }, error => {
                this._logger.log("error from address validation - " + error);
            });
    }

    reLoadPageData(reload: boolean): void {
        if (reload) {
            this._logger.log("reloading profile page");
            this._logger.log("calling reload from reLoadPageData");
            this.loadPage();
        } else {
            this._logger.log("showing loader on detail page");
            this.showProfileLoader = this.letComponentsChangeLoader ? !this.showProfileLoader : this.showProfileLoader;
        }
    }

    updateProfile(name: string, value: string): void {

        let oldPrimaryIndustry = this.providerDetails.primaryIndustry;
        let oldoverview = this.providerDetails.overview;
        this.message = null;
        let remainingChars: number = this.el2.nativeElement.value.length;
        let linebreak = (this.el2.nativeElement.value.match(/\n/) !== null ? this.el2.nativeElement.value.match(/\n/gm).length : 0);
        remainingChars = Number(remainingChars) + Number(linebreak);
        this.providerUpdate = new ProviderUpdate();

        if (null != name && name == 'businessName') {
            this.providerUpdate.name = name;
            this.providerUpdate.value = this.providerDetails.businessName;

        } else if (null != name && name == 'primaryIndustry') {
            this.providerUpdate.name = name;
            this.providerUpdate.value = this._primaryIndustry.id;
            this.providerDetails.primaryIndustry = this._primaryIndustry.descr;

        } else if (null != name && name == 'overview') {
            this.providerUpdate.name = name;
            this.providerUpdate.value = value;

            this._logger.log(value);
            if (!value || value.trim().length <= 0) {
                this._logger.log("Overview value is not valid.");
                this.errorpopUpModal.errors = ["Company overview can not be blank."];
                this.errorpopUpModal.errorHeadingMessage = "Overview";
                this.displayError(this.errorpopUpModal);
                //overview can not be blank
                this.el2.nativeElement.value = this.providerDetails.overview;
                return;
            } else if (remainingChars > 1000) {
                this._logger.log("Overview value is not valid.");
                this.errorpopUpModal.errors = ["Overview text length can not be more than 1000."];
                this.errorpopUpModal.errorHeadingMessage = "Overview";
                this.displayError(this.errorpopUpModal);
                //overview can not be blank
                this.el2.nativeElement.value = this.providerDetails.overview;
                return;
            } else if (!value.match(/[a-zA-Z0-9]/)) {
                this.errorpopUpModal.errors = ["Invalid company overview. Correct the special characters."];
                this.errorpopUpModal.errorHeadingMessage = "Overview";
                this.displayError(this.errorpopUpModal);
                //overview can not be blank
                this.el2.nativeElement.value = this.providerDetails.overview;
                return;
            } else {
                value = value.trim();
                this.providerDetails.overview = value;
            }

            this._logger.log("Updating overview: " + value);
        } else if (null != name && name == 'yearsInBusiness') {

        } else {
            this._logger.log("Blank update data block");
        }

        if (null != this.providerUpdate.name && this.providerUpdate.name != '') {
            this._logger.log('send for profile save');
            this._logger.log(this.providerUpdate);

            // this.showProfileLoader = true;
            this._proviverProfileService.updateProfile(this.providerUpdate).subscribe(
                data => {
                    let reply = JSON.parse(data);
                    this._logger.log(reply);
                    // this.showProfileLoader = false;
                    if (reply.result) {
                        this._logger.log("successfuly saved data");
                        // this.loadPage();
                    } else {
                        this._logger.log("Error while overview..", reply);

                        this._logger.log("Reverting changes", oldPrimaryIndustry, oldoverview);
                        this.providerDetails.primaryIndustry = oldPrimaryIndustry;
                        this.providerDetails.overview = oldoverview;

                        this.errorpopUpModal.errors = ["Error while saving " + this.providerUpdate.name];
                        this.errorpopUpModal.errorHeadingMessage = "Update Profile";
                        this.displayError(this.errorpopUpModal);
                    }
                }, error => {
                    this._logger.log("Error while overview..", error);

                    this._logger.log("Reverting changes", oldPrimaryIndustry, oldoverview);
                    this.providerDetails.primaryIndustry = oldPrimaryIndustry;
                    this.providerDetails.overview = oldoverview;

                    // this.showProfileLoader = false;

                    this.errorpopUpModal.errors = ["Error while saving " + this.providerUpdate.name];
                    this.errorpopUpModal.errorHeadingMessage = "Update Profile";
                    this.displayError(this.errorpopUpModal);
                }, () => {
                    this.setProfileStrength();
                });
        } else {
            this._logger.log("No data to update.");
        }
    }

    reloadByChild(message: string) {

        this._logger.log("calling reload from reloadByChild");
        this.loadPage();
    }

    updateCoverageDropdown(): void {
        let dropDownCoverageZipCodesTemp: Array<DispatchLocation> = new Array<DispatchLocation>();

        for (let tempDispatchLoc of this.providerDetails.dispatchLocations.dispatchLocation) {
            let addInList: boolean = true;
            for (let tempCoverageLoc of this.providerDetails.coverageLocations.coverageLocation) {
                if (tempCoverageLoc.zip == tempDispatchLoc.zip) {
                    addInList = false;
                    break;
                }
            }

            if (addInList) {
                dropDownCoverageZipCodesTemp.push(tempDispatchLoc);
            }
        }

        this._logger.log(dropDownCoverageZipCodesTemp);

        this.dropDownCoverageZipCodes = new Array<DispatchLocation>();
        let blankZip: DispatchLocation = new DispatchLocation();
        this.changeCoverageDropDown(blankZip);
        this.dropDownCoverageZipCodes.push(blankZip);

        for (let indexOutter = 0; indexOutter < dropDownCoverageZipCodesTemp.length - 1; indexOutter++) {

            let addInList: boolean = true;
            for (let indexInner = indexOutter + 1; indexInner < dropDownCoverageZipCodesTemp.length; indexInner++) {
                if (dropDownCoverageZipCodesTemp[indexOutter].zip == dropDownCoverageZipCodesTemp[indexInner].zip) {
                    addInList = false;
                    break;
                }
            }

            if (addInList) {
                this.dropDownCoverageZipCodes.push(dropDownCoverageZipCodesTemp[indexOutter]);
                this._logger.log(this.dropDownCoverageZipCodes);
            }
        }
        if (dropDownCoverageZipCodesTemp.length > 0) {
            this.dropDownCoverageZipCodes.push(dropDownCoverageZipCodesTemp[dropDownCoverageZipCodesTemp.length - 1]);
        }
        this.coverageRadius = 0;

        this._logger.log(this.dropDownCoverageZipCodes);
    }

    resetOverview() {
        //alert("in resetOverview");
        //this.providerDetails.overview='';
        this.message = null;
        this.el2.nativeElement.value = this.providerDetails.overview;
    }
    //   bindCoverageData(coverage:CoverageLocation){
    //       this.coverageRadius=coverage.coverageRadius;
    //       this.coverageZip = coverage.zip;
    //       this.locId=coverage.locnId;
    //   }

    displayDefaultValue() {
        let primaryIndistry: Array<PrimaryIndustry> = this.providerDetails.primaryIndustries.primaryIndObj.filter(primaryIndustry => primaryIndustry.descr == this.providerDetails.primaryIndustry);
        this._primaryIndustry = primaryIndistry[0];

    }

    addCoverageInUI(coverageLocation: CoverageLocation[]) {

        this.providerDetails.coverageLocations.coverageLocation = coverageLocation;
        this.message = null;
        this._logger.log("getcoverage result in addCoverageInUI :");
        this._logger.log(coverageLocation);
        this.setProfileStrength();
        this._logger.log("Exiting addCoverageInUI..");
    }

    getSelectedSkuCount(selectedSkuCount: SkuCountModal) {
        this._logger.log("start getSelectedSkuCount", selectedSkuCount);

        let serviceOfferedToRemove: ServiceOffered;
        this.providerDetails.serviceOfferedList.forEach(select => {
            if (selectedSkuCount.primaryIndustryId == select.primaryIndustryId) {
                select.tempServiceMessage = "";

                if (selectedSkuCount.status == Status.INPROGRESS) {
                    select.tempServiceMessage = "Updating service offering...";
                } else if (selectedSkuCount.status == Status.SUCCESS) {
                    if (selectedSkuCount.selectSkuCount <= 0) {
                        serviceOfferedToRemove = select;
                    } else {
                        select.servicesOptedCount = selectedSkuCount.selectSkuCount;
                    }
                } else if (selectedSkuCount.status == Status.ERROR) {

                } else {

                }

                return;
            }
        });

        if (serviceOfferedToRemove) {
            this._logger.log("length before removing: ", this.providerDetails.serviceOfferedList.length);
            this.providerDetails.serviceOfferedList = this.providerDetails.serviceOfferedList.filter(data => {
                return data != serviceOfferedToRemove;
            });

            this._logger.log("length after removing: ", this.providerDetails.serviceOfferedList.length);
        }

        //alert(selectedSkuCount);
        this.setProfileStrength();
        this._logger.log("end getSelectedSkuCount");
    }


    displayMessage(message: string) {
        this.message = message;
    }

    countWord(event: any) {
        let remainingChars: number = this.el2.nativeElement.value.length;
        let linebreak = (this.el2.nativeElement.value.match(/\n/) !== null ? this.el2.nativeElement.value.match(/\n/gm).length : 0);
        remainingChars = Number(remainingChars) + Number(linebreak);
        remainingChars = this.maxWordForOverview - remainingChars;
        if (remainingChars < 0) {
            this.message = "Text exceeds maximum limit of 1000";
        } else {
            this.message = remainingChars + " character(s) remaining of maximum " + this.maxWordForOverview
        }
    }


    getCoverageAreas() {
        this._proviverProfileService.getCoverageAreas()
            .subscribe(dataList => {
                this.coverageLocations = JSON.parse(dataList)
                this._logger.log("inside getCoverageAreas");
                this._logger.log(this.coverageLocations);
                if (null != this.coverageLocations && this.coverageLocations.length > 0) {
                    this.message = null;
                    this.providerDetails.coverageLocations.coverageLocation = this.coverageLocations;
                } else {
                    this.message = null;
                    this.providerDetails.coverageLocations.coverageLocation = new Array<CoverageLocation>();
                }
            }, error => {
            }, () => {
                this.setProfileStrength();
            });
    }

    deleteCoverageArea(coverageRadius: number, locId: string): void {
        this._logger.log("inside deleteCoverageArea");

        let jsonString: string;
        jsonString = JSON.stringify({ locId: locId, coverageRadius: coverageRadius });
        this._proviverProfileService.deleteCoverageArea(jsonString).subscribe(
            data => {
                this._logger.log(data);
                this.getCoverageAreas();
            }, error => {
                this._logger.log(error);
            });
    }

    deleteCoverageAreaOnConfirm(): void {

        this._logger.log("inside deleteCoverageArea");
        jQuery("#deleteCoveragePopUp").modal("hide");

        let jsonString: string;
        this.message = "Deleting coverage area...";
        if (null != this.deleteRadius && null != this.deleteLocId) {
            jsonString = JSON.stringify({ locId: this.deleteLocId, coverageRadius: this.deleteRadius });
            this._proviverProfileService.deleteCoverageArea(jsonString).subscribe(
                data => {
                    this._logger.log(data);
                    this.message = null;
                    this.deleteRadius = null;
                    this.deleteLocId = null;
                    this.getCoverageAreas();
                    // this.setProfileStrength();
                    // let coverageLocation:CoverageLocation = new CoverageLocation();
                    // coverageLocation.coverageRadius=coverageRadius;
                    // coverageLocation.locnId = locId;
                    // this.deleteCoverage.emit(coverageLocation);
                }, error => {
                    this._logger.log(error);
                    //this.reLoadDetailPage.emit(false);
                });

        }



    }

    showDeleteCoveragePopUp(coverageRadius: number, locId: string): void {
        this.deleteRadius = coverageRadius;
        this.deleteLocId = locId;
        jQuery("#deleteCoveragePopUp").modal("show");

    }

    setRateCardAdded(ratecard: RateCardEventEmmiter) {
        this._logger.log("ratecard primaryIndustryId ", ratecard);
        this.providerDetails.serviceOfferedList.forEach(service => {
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
}