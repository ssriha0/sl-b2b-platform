/* inbuild libraries */
import { Component, OnInit, EventEmitter, Output, ViewChild, ElementRef } from '@angular/core';
import {DomSanitizer, SafeHtml} from '@angular/platform-browser';
import { Observable }       from 'rxjs/Observable';
/* custom services */
import {ServiceOfferService} from '../common/service/service-offered/service-offer.service';
import { WaitLoaderComponent } from '../common/component/wait-loader/wait-loader.component';

/* custom dto/pojo/bean/modal objects */
import { SelectServicesResponseObject, GetSkuDetailResponse} from '../common/modal/service-offered/select-services-response.modal';
import { SelectServiceDetail, Sku, SkuPrices } from '../common/modal/service-offered/select-service-detail.modal';
import {LoggerUtil} from '../common/util/logger.util';
import { SkuCountModal } from '../common/modal/service-offered-detail/profile-skucount.modal';
import { ErrorModal } from '../common/modal/error.modal';
import {Status} from '../common/constant/global-status.constant';

declare var jQuery: any;

@Component({
    selector: 'select-service',
    template: require('./select-services.component.html'),
    styles: [
        require('./select-services.component.css')
    ],
    providers: [
        ServiceOfferService,
    ]
})

export class SelectService implements OnInit {
    @Output()
    private reLoadDetailPage = new EventEmitter<boolean>();

    @Output()
    private errormessage = new EventEmitter<ErrorModal>();
    @Output()
    private selectedSkuCount = new EventEmitter<SkuCountModal>();

    @ViewChild('hideServiceDetailPopUp') el: ElementRef;


    private showLoader: boolean;
    private selectServiceDetail: SelectServiceDetail;
    private skuDetailResponseArray = Array<GetSkuDetailResponse>();
    private selectedSkuDetails: Sku;
    private selectedPrimaryIndustryId: string;
    private validateSku: SkuPrices;
    private oldValidPrice: string;
    private oldValidLimit: string;
    private errorpopUpModal = new ErrorModal();
    // private index: number;
    private primaryIndustryName: string;

    //pagination declaration start

    pages: number = 1;
    pageSize: number = 10;
    pageNumber: number = 0;
    currentIndex: number = 1;
    paginatedSkuDetails: SelectServiceDetail;
    pagesIndex: Array<number>;
    pageStart: number = 1;
    inputName: string = '';
    totalRecords: number;
    curPageStartIndex: number;
    curPageEndIndex: number;
    endIndex: number = 0;


    //pagination end

    private sort: any = {
        column: 0, // intial sort
        descending: false
    }

    constructor(private _selectService: ServiceOfferService, private _logger: LoggerUtil, private _sanitizer: DomSanitizer) {

    }

    ngOnInit(): void {
        this.paginatedSkuDetails = new SelectServiceDetail();
        this.paginatedSkuDetails.skus = new Array<Sku>();
        // this.loadData("900");
        // this.selectedPrimaryIndustryId = "900";
    }
    closeServiceDetailPopUp(): void {
        jQuery(this.el.nativeElement).modal("hide");
    }
    passValueToModel(skuDetails: Sku) {
        this._logger.log(skuDetails);
        this.selectedSkuDetails = skuDetails;
    }

    loadData(primaryIndustryId: string, primaryIndustryName: string): void {
        jQuery("#softValidationLimitPrice").modal("hide");
        jQuery("#softValidationLimitPrice").removeClass('in');
        this.sort.column = 0;
        this.sort.descending = false;
        this.primaryIndustryName = primaryIndustryName;
        this.selectServiceDetail = new SelectServiceDetail();
        this.selectServiceDetail.skus = new Array<Sku>();
        this.resetData();


        this._logger.log("primary industry id : " + primaryIndustryId);
        this.selectedPrimaryIndustryId = primaryIndustryId;

        this.showLoader = true;
        let selectServicesResponseObject: SelectServicesResponseObject;
        this._selectService.getSkuDetails(primaryIndustryId).subscribe(
            data => {
                selectServicesResponseObject = JSON.parse(data);
                this._logger.log(selectServicesResponseObject);

                if (null != selectServicesResponseObject.result
                    && selectServicesResponseObject.result.length > 0) {

                    selectServicesResponseObject.result = selectServicesResponseObject.result.sort((skudetail1: GetSkuDetailResponse, skudetail2: GetSkuDetailResponse) => {
                        if (null != skudetail1.buyerSkuTasks[0] && null != skudetail2.buyerSkuTasks[0]) {
                            return skudetail1.buyerSkuTasks[0].taskName.localeCompare(skudetail2.buyerSkuTasks[0].taskName);
                        }
                    });
                    this._logger.log("sorted response with task name: ", selectServicesResponseObject);

                    let skuSelected: Array<Sku> = new Array<Sku>();
                    let skuNotSelected: Array<Sku> = new Array<Sku>();
                    selectServicesResponseObject.result.forEach(skudetail => {
                        let sku: Sku = new Sku();

                        sku.sku = skudetail.sku;
                        sku.skuid = skudetail.skuId;
                        // sku.skuName = skudetail.sku;
                        sku.categoryName = skudetail.buyerSkuCategory.categoryName;
                        skudetail.buyerSkuTasks.forEach(buyerskutask => {
                            sku.taskComments = buyerskutask.taskComments;
                            sku.descr = buyerskutask.luServiceTypeTemplate.descr;
                            sku.skuName = buyerskutask.taskName;
                        });

                        sku.skuPrices = new Array<SkuPrices>();

                        skudetail.vendorServiceOfferVo.forEach(venseroffvo => {

                            sku.offeringId = venseroffvo.id;
                            if (null != venseroffvo.vendorServiceOfferPriceVo && venseroffvo.vendorServiceOfferPriceVo.length > 0) {
                                venseroffvo.vendorServiceOfferPriceVo.forEach(venSerOffPrice => {
                                    let skuPrice: SkuPrices = new SkuPrices();
                                    skuPrice.id = venSerOffPrice.id;
                                    skuPrice.price = venSerOffPrice.price ? venSerOffPrice.price.toFixed(2) : "0.00";
                                    skuPrice.dailyLimit = venSerOffPrice.dailyLimit ? venSerOffPrice.dailyLimit.toString() : "";
                                    skuPrice.isSelected = true;
                                    skuPrice.dailyLimitValid = true;
                                    skuPrice.priceValid = true;
                                    sku.skuPrices.push(skuPrice);

                                    skuSelected.push(sku);
                                });
                            } else {

                                let skuPrice: SkuPrices = new SkuPrices();
                                skuPrice.isSelected = false;
                                skuPrice.dailyLimitValid = true;
                                skuPrice.priceValid = true;
                                skuPrice.price = '';
                                skuPrice.dailyLimit = '';
                                sku.skuPrices.push(skuPrice);

                                skuNotSelected.push(sku);
                            }
                        });
                    });

                    this.selectServiceDetail.skus = this.selectServiceDetail.skus.concat(skuSelected, skuNotSelected);
                    this._logger.log("Filtered skus ", this.selectServiceDetail.skus);
                }
                this.showLoader = false;
                this.totalRecords = this.selectServiceDetail.skus.length;
                this.init();
                // jQuery("#selectService").modal("handleUpdate");
            },
            error => {
                this._logger.log("Error: " + JSON.stringify(error));
                this.showLoader = false;
            }
        );
    }

    saveSkuDetails(): void {
        let selectServiceDetailSave: SelectServiceDetail = new SelectServiceDetail();
        let selected: Array<Sku> = new Array<Sku>();



        /* 
        this.selectServiceDetail.skus.forEach(selectService => {
            if (selectService.skuPrices[0].isSelected) {
                selected.push(selectService);
            }
        }); 
        */

        let isValid: boolean = true;
        this.selectServiceDetail.skus.forEach(selectService => {
            if (selectService.skuPrices[0].isSelected) {
                if (selectService.skuPrices[0].dailyLimit
                    && selectService.skuPrices[0].price
                    && parseInt(selectService.skuPrices[0].dailyLimit) > 0
                    && parseFloat(selectService.skuPrices[0].price) > 0.0) {
                    selected.push(selectService);
                } else {
                    if (!selectService.skuPrices[0].dailyLimit || parseInt(selectService.skuPrices[0].dailyLimit) <= 0) {
                        selectService.skuPrices[0].dailyLimitValid = false;
                    }

                    if (!selectService.skuPrices[0].price || parseFloat(selectService.skuPrices[0].price) <= 0.0) {
                        selectService.skuPrices[0].priceValid = false;
                    }
                    isValid = false;
                }
            }
        });

        if (!isValid) {
            this._logger.log("Not valid data.");
            return;
        } else {
            this._logger.log("Valid data.");
        }

        this._logger.log(selected);
        let skuCountModal = new SkuCountModal();
        skuCountModal.primaryIndustryId = this.selectedPrimaryIndustryId;
        skuCountModal.selectSkuCount = selected.length;
        skuCountModal.error = false;
        skuCountModal.status = Status.INPROGRESS;
        this.selectedSkuCount.emit(skuCountModal);

        jQuery("#selectService").modal("hide");

        this.showLoader = true;
        this.reLoadDetailPage.emit(false);

        selectServiceDetailSave.skus = selected;
        selectServiceDetailSave.primaryIndustryId = this.selectedPrimaryIndustryId;
        //resets pagination data of pages and view
        //this.resetData();
        this._selectService.getSaveSkuDetails(selectServiceDetailSave).subscribe(
            data => {
                let reply = JSON.parse(data);
                this._logger.log(reply);
                if (reply.result) {
                    this._logger.log("successfuly saved data");
                    // this.loadPage();
                    let skuCountModal = new SkuCountModal();
                    skuCountModal.primaryIndustryId = this.selectedPrimaryIndustryId;
                    skuCountModal.selectSkuCount = selected.length;
                    skuCountModal.error = false;
                    skuCountModal.status = Status.SUCCESS;
                    this.selectedSkuCount.emit(skuCountModal);

                } else {
                    this._logger.log("Error while udating price and dailylimit..", reply);

                    this._logger.log("Reverting changes", selected);
                    let skuCountModal = new SkuCountModal();
                    skuCountModal.primaryIndustryId = this.selectedPrimaryIndustryId;
                    skuCountModal.selectSkuCount = this.selectServiceDetail.skus.length;
                    skuCountModal.error = true;
                    skuCountModal.status = Status.ERROR;
                    this.selectedSkuCount.emit(skuCountModal);

                    this.errorpopUpModal.errors = ["Error while saving sku price and dailylimit."];
                    this.errorpopUpModal.errorHeadingMessage = "Sku Detail";
                    this.errormessage.emit(this.errorpopUpModal);
                }
                //this.showLoader = false;
                //this.reLoadDetailPage.emit(true);
            },

            error => {
                this._logger.log(JSON.parse(error));
                this.showLoader = false;
            }
        );
    }

    private sanitize(htmlString: string): SafeHtml {
        this._logger.log(this._sanitizer.bypassSecurityTrustHtml(htmlString));
        return null != htmlString && htmlString.length > 0 ? this._sanitizer.bypassSecurityTrustHtml(htmlString) : "";
    }

    onCheckBoxChange(index: number): void {
        //index = this.getActualIndex(index);
        this._logger.log("onCheckBoxChange", index);
        if (this.paginatedSkuDetails.skus[index].skuPrices[0].isSelected) {
            this.paginatedSkuDetails.skus[index].skuPrices[0].dailyLimit = "1";
        } else {
            this.paginatedSkuDetails.skus[index].skuPrices[0].dailyLimit = "";
            this.paginatedSkuDetails.skus[index].skuPrices[0].price = "";
        }
    }

    keyPressListPrice(event: any, index: number): void {
        const pattern: RegExp = /[0-9\.]/;
        const wholePattern: RegExp = /^[0-9]{0,5}(\.[0-9]?[0-9]?)?$/;
        let inputChar: string = String.fromCharCode(event.charCode);
        //index = this.getActualIndex(index);
        //this._logger.log(event);
        //this._logger.log(event.srcElement);
        //this._logger.log(event.srcElement.value);
        this._logger.log("keyPressListPrice : ", inputChar, event.charCode, event.srcElement.value);
        this._logger.log(pattern.test(inputChar), wholePattern.test(event.srcElement.value));
        if (!pattern.test(inputChar) || !wholePattern.test(event.srcElement.value)) {
            this._logger.log("in-valid...");
            event.preventDefault();
        } else {
            this._logger.log("valid...");
            if (!this.paginatedSkuDetails.skus[index].skuPrices[0].isSelected) {
                this.paginatedSkuDetails.skus[index].skuPrices[0].dailyLimit = "1";
                this.paginatedSkuDetails.skus[index].skuPrices[0].isSelected = true;
            }
        }
    }

    keyUpListPrice(event: any, index: number): void {

        //index = this.getActualIndex(index);
        const wholePattern: RegExp = /^[0-9]{0,5}(\.[0-9]?[0-9]?)?$/;
        //this._logger.log(event);
        //this._logger.log(event.srcElement);
        //this._logger.log(event.srcElement.value);
        // this.onPasteListPrice(event);
        this._logger.log("keyUpListPrice : ", event.charCode, event.srcElement.value);
        this._logger.log(wholePattern.test(event.srcElement.value));
        if (!wholePattern.test(event.srcElement.value)) {
            this._logger.log("in-valid...assigning new value");
            //event.srcElement.value = this.oldValidPrice;
            this.paginatedSkuDetails.skus[index].skuPrices[0].price = this.oldValidPrice;
            //event.preventDefault();
        } else {
            this._logger.log("valid...");
            this.oldValidPrice = event.srcElement.value;
        }
    }

    focusListPrice(event: any, index: number): void {
        this._logger.log("focusListPrice for index: ", index);
        this.oldValidPrice = this.paginatedSkuDetails.skus[index].skuPrices[0].price;
    }

    focusDailyLimit(event: any, index: number): void {
        this._logger.log("focusDailyLimit for index: ", index);
        this.oldValidLimit = this.paginatedSkuDetails.skus[index].skuPrices[0].dailyLimit;
    }

    keyPressDailyLimit(event: any, index: number): void {

        //index = this.getActualIndex(index);
        const pattern: RegExp = /[0-9]/;
        const wholePattern: RegExp = /^[0-9]{0,2}$/;
        let inputChar: string = String.fromCharCode(event.charCode);
        //this._logger.log(event);
        //this._logger.log(event.srcElement);
        //this._logger.log(event.srcElement.value);
        this._logger.log("keyPressDailyLimit : ", inputChar, event.charCode, event.srcElement.value);
        this._logger.log(pattern.test(inputChar), wholePattern.test(event.srcElement.value));
        if (!pattern.test(inputChar) || !wholePattern.test(event.srcElement.value)) {
            this._logger.log("in-valid...");
            event.preventDefault();
        } else {
            this._logger.log("valid...");
            this.paginatedSkuDetails.skus[index].skuPrices[0].isSelected = true;
        }
    }

    keyupDailyLimit(event: any, index: number) {
        //index = this.getActualIndex(index);
        let dailyLimit = this.paginatedSkuDetails.skus[index].skuPrices[0].dailyLimit;

        const pattern: RegExp = /^[0-9]*$/;
        this._logger.log("keyupDailyLimit : ", dailyLimit);
        if (null != dailyLimit) {
            if (pattern.test(dailyLimit)) {
                this._logger.log("valid...");
                this.oldValidLimit = event.srcElement.value;
            } else {
                this._logger.log("in-valid...");
                //this.paginatedSkuDetails.skus[index].skuPrices[0].isSelected = false;
                this.paginatedSkuDetails.skus[index].skuPrices[0].dailyLimit = this.oldValidLimit;
                // event.preventDefault();
            }
        }
    }

    changePriceLimitValidation(index: number): void {
        this._logger.log("changePriceLimitValidation - index from page " + index);
        //index = this.getActualIndex(index);
        //this._logger.log("changePriceLimitValidation - actual index  " + index);

        let softValidateSku = this.paginatedSkuDetails.skus[index].skuPrices[0];
        this._logger.log("changePriceLimitValidation - old price " + softValidateSku.price);
        softValidateSku.price = softValidateSku.price ? parseFloat(softValidateSku.price).toFixed(2) : "";
        this._logger.log("changePriceLimitValidation - new price " + softValidateSku.price);
        this.paginatedSkuDetails.skus[index].skuPrices[0].priceValid = true;
        if (parseFloat(softValidateSku.price) > 999.99 && !jQuery("#softValidationLimitPrice").hasClass('in')) {
            this.validateSku = softValidateSku;
            //this.index = index;
            jQuery("#saveSkuDetails").prop('disabled', true);
            this._logger.log("Opening validation popup...");
            jQuery("#softValidationLimitPrice").modal("show");
        } else {
            this._logger.log("softValidationLimitPrice is already open for: ", index);
        }
    }

    changeDailyLimitValidation(index: number): void {
        this._logger.log("changeDailyLimitValidation - index from page" + index);
        this.paginatedSkuDetails.skus[index].skuPrices[0].dailyLimitValid = true;
    }

    closeSoftValidationPopUp(ignoreValue: boolean): void {
        this._logger.log("closeSoftValidationPopUp - ignoreValue " + ignoreValue);
        if (!ignoreValue) {
            this._logger.log("Opening validation popup...");
            //this.softValidateSku.price = "";
            this.validateSku.price = "";
            this.validateSku.priceValid = false;
        }
        this._logger.log("closing validation popup...");
        jQuery("#softValidationLimitPrice").modal("hide");
        jQuery("#saveSkuDetails").prop('disabled', false);
        jQuery("#saveSkuClosePopUp").removeClass('disable-link');
    }

    //pagenation code start
    init() {
        this.currentIndex = 1;
        this.pageStart = 1;
        this.pages = 0;

        this.pageNumber = parseInt("" + (this.selectServiceDetail.skus.length / this.pageSize));
        if (this.selectServiceDetail.skus.length % this.pageSize != 0) {
            this.pageNumber++;
        }

        if (this.pageNumber < this.pages) {
            this.pages = this.pageNumber;

        }

        this.refreshItems();
        console.log("this.pageNumber :  " + this.pageNumber);
    }
    // FilterByName() {
    //   this.selectServiceDetail.skus = [];

    //   if (this.inputName != "") {
    //     this.selectServiceDetail.skus.forEach(element => {
    //       if (element.name.toUpperCase().indexOf(this.inputName.toUpperCase()) >= 0) {
    //         this.selectServiceDetail.skus.push(element);
    //       }
    //     });
    //   } else {
    //     this.selectServiceDetail.skus = this.selectServiceDetail.skus;
    //   }
    //   console.log(this.selectServiceDetail.skus);
    //   this.init();
    // }
    fillArray(): any {
        var obj = new Array();
        for (var index = this.pageStart; index < this.pageStart + this.pages; index++) {
            obj.push(index);
        }
        return obj;
    }
    //   refreshItems() {
    //     this.paginatedSkuDetails.skus = this.selectServiceDetail.skus.slice((this.currentIndex - 1) * this.pageSize, (this.currentIndex) * this.pageSize);
    //     this.curPageStartIndex=(this.currentIndex - 1) * (this.pageSize) + 1;
    //     this.curPageEndIndex=(this.currentIndex) * (this.pageSize);
    //     this._logger.log("this.paginatedSkuDetails lenth:"+this.paginatedSkuDetails.skus.length);
    //     if(this.curPageEndIndex > this.totalRecords){
    //       this.curPageEndIndex = this.totalRecords;
    //     }
    //     this.pagesIndex = this.fillArray();

    //   }

    refreshItems() {
        let nextStartIndex = this.endIndex;
        this.endIndex = this.endIndex + 10;
        if (this.endIndex > this.totalRecords) {
            this.endIndex = this.totalRecords;
        }

        this._logger.log(nextStartIndex, this.endIndex, this.totalRecords);

        //this._logger.log("Prev sku list: ", this.paginatedSkuDetails.skus);
        //this._logger.log("List to add: ", this.selectServiceDetail.skus.slice(nextStartIndex, this.endIndex));
        this.paginatedSkuDetails.skus = Array.prototype.concat(this.paginatedSkuDetails.skus, this.selectServiceDetail.skus.slice(nextStartIndex, this.endIndex));

        this.curPageStartIndex = (this.currentIndex - 1) * (this.pageSize) + 1;
        this.curPageEndIndex = (this.currentIndex) * (this.pageSize);
        this._logger.log("this.paginatedSkuDetails length: ", this.paginatedSkuDetails.skus.length);
        if (this.curPageEndIndex > this.totalRecords) {
            this.curPageEndIndex = this.totalRecords;
        }
        this.pagesIndex = this.fillArray();

    }


    prevPage() {
        if (this.currentIndex > 1) {
            this.currentIndex--;
        }
        if (this.currentIndex < this.pageStart) {
            this.pageStart = this.currentIndex;

        }
        this.refreshItems();
    }
    nextPage() {
        if (this.currentIndex < this.pageNumber) {
            this.currentIndex++;
        }
        if (this.currentIndex >= (this.pageStart + this.pages)) {
            this.pageStart = this.currentIndex - this.pages + 1;
        }

        this.refreshItems();
    }
    setPage(index: number) {
        this.currentIndex = index;
        this.refreshItems();
    }
    //pagination code end

    resetData() {
        this._logger.log("restting data");
        jQuery("#selectService").modal("hide");
        this.paginatedSkuDetails = new SelectServiceDetail();
        this.paginatedSkuDetails.skus = new Array<Sku>();
        this.endIndex = 0;
    }
    disableMouseRightClick(): boolean {
        return false;
    }
    getActualIndex(index: number): number {
        if (this.curPageEndIndex > 1) {
            return (this.currentIndex - 1) * 10 + index;
        } else {
            return index;
        }
    }

    selectedClass(columnNumber: number): string {
        return (columnNumber == this.sort.column) ? 'sort-' + this.sort.descending : '';
    }

    changeSorting(columnNumber: number): void {

        if (this.sort.column == columnNumber) {
            this.sort.descending = !this.sort.descending;
        } else {
            this.sort.column = columnNumber;
            this.sort.descending = false;
        }
        this.sortSKUTable(columnNumber, this.sort.descending ? -1 : 1);
    }

    convertSorting(): string {
        return this.sort.descending ? '-' + this.sort.column : this.sort.column;
    }

    sortSKUTable(columnNumber: number, sortOrder: number) {
        this._logger.log("sorting according to: ", columnNumber);

        if (this.paginatedSkuDetails.skus && this.paginatedSkuDetails.skus.length > 1) {

            this.paginatedSkuDetails.skus = this.paginatedSkuDetails.skus.sort((a, b) => {
                let compValue = -1;

                if (columnNumber == 0) {
                    // if (a.skuName && b.skuName) {
                    compValue = a.skuName.localeCompare(b.skuName);
                    // }
                } else if (columnNumber == 1) {
                    if (parseFloat(a.skuPrices[0].price)) {
                        if (parseFloat(b.skuPrices[0].price)) {
                            compValue = parseFloat(a.skuPrices[0].price) - parseFloat(b.skuPrices[0].price);
                        } else {
                            compValue = parseFloat(a.skuPrices[0].price) - 0;
                        }
                    }
                } else if (columnNumber == 2) {
                    if (parseInt(a.skuPrices[0].dailyLimit)) {
                        if (parseInt(b.skuPrices[0].dailyLimit)) {
                            compValue = parseInt(a.skuPrices[0].dailyLimit) - parseInt(b.skuPrices[0].dailyLimit);
                        } else {
                            compValue = parseInt(a.skuPrices[0].dailyLimit) - 0;
                        }
                    }
                } else {
                    this._logger.log("No matching sort column");
                }
                return compValue * sortOrder;
            });
        }

        this._logger.log("sorted array : ", this.paginatedSkuDetails.skus);
    }
}