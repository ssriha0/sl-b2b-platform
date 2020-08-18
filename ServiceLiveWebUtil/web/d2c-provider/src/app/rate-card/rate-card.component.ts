/* inbuild libraries */
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ServiceOffered, ServiceOfferedOn, UpdateServiceOfferedOnVO } from '../common/modal/service-offered-detail/service-offered-detail.modal';
import { RateCardService } from '../common/service/rate-card.service';
import { LoggerUtil } from '../common/util/logger.util';
import { RateCardResponseModal, ServiceDayRatePeriod } from '../common/modal/rate-card/rate-card-response.modal';
import { ServiceDayResponseUIModal, ServiceRateCardReseponseUIModal, ServiceDayList } from '../common/modal/rate-card/rate-card-responseui.modal';
import { RateCardPriceResponseModal, RateCardPrice } from '../common/modal/rate-card/rate-card-price-response.modal';
import { RateCardPriceUIModal, RatePriceUIModal, ServiceRatePeriodUIVO, ServiceDayUIVO, RateCardEventEmmiter } from '../common/modal/rate-card/rate-card-price-responseui.modal';
import { ErrorModal } from '../common/modal/error.modal';
import { LookupOffersVO } from '../common/modal/provider-profile/provider-details.modal';
declare var jQuery: any;
@Component({
  selector: 'rate-card',
  template: require('./rate-card.component.html'),
  styles: [
    require('./rate-card.component.css')
  ],
  providers: [RateCardService]
})
export class RateCard implements OnInit {
  private serviceDayList: ServiceDayList = new ServiceDayList();
  private rateCardList: ServiceDayList = new ServiceDayList();
 // private primaryIndustryName: string;
  //private primaryIndustryId: string;
  private softValidateRateCard: RatePriceUIModal = new RatePriceUIModal();
  private oldValidPrice: string;
  private index: number;
  private rateCardPrice: string;
  private rateCardLoader: boolean;
  private rateCardPriceUIModal: RateCardPriceUIModal = new RateCardPriceUIModal();
  private serviceOffered = new ServiceOffered();
   @Output()
  private errormessage = new EventEmitter<ErrorModal>();

  @Output()
  private rateCardAdded = new EventEmitter<RateCardEventEmmiter>();

  constructor(private _rateCardService: RateCardService, private _logger: LoggerUtil) { }

  ngOnInit(): void {
    this._logger.log("Rate Card Component");
    let rateCardResponseModal: RateCardResponseModal;
    this.serviceDayList.serviceDay = new Array<ServiceDayResponseUIModal>();
    this.serviceDayList.serviceRatePeriod = new Array<ServiceRateCardReseponseUIModal>();
    this._rateCardService.getRateCardLookupDetails().subscribe(
      data => {
        this._logger.log(data);
        rateCardResponseModal = JSON.parse(data);
        this._logger.log(rateCardResponseModal);

        if (null != rateCardResponseModal && null != rateCardResponseModal.result && null != rateCardResponseModal.result.serviceDaysVOs && null != rateCardResponseModal.result.serviceRatePeriodVOs) {
          rateCardResponseModal.result.serviceDaysVOs.forEach(serDay => {
            let serviceDayResponseUIModal: ServiceDayResponseUIModal = new ServiceDayResponseUIModal();
            serviceDayResponseUIModal.id = serDay.id;
            serviceDayResponseUIModal.name = serDay.name;
            this.serviceDayList.serviceDay.push(serviceDayResponseUIModal);
          })

          rateCardResponseModal.result.serviceRatePeriodVOs.forEach(servicePeriod => {
            let serviceRateCardResponseUIModal: ServiceRateCardReseponseUIModal = new ServiceRateCardReseponseUIModal();
            serviceRateCardResponseUIModal.id = servicePeriod.id;
            serviceRateCardResponseUIModal.name = servicePeriod.name;
            this.serviceDayList.serviceRatePeriod.push(serviceRateCardResponseUIModal);
          })
        }
      });
  }

  getPriceOfRateCard(serviceOffered:ServiceOffered): void {
    jQuery("#softValidationLimitRateCardPrice").removeClass('in');
    jQuery("#softValidationLimitRateCardPrice").modal('hide');
    this.rateCardLoader = true;
    let rateCardPriceResponseModal: RateCardPriceResponseModal;
    this.serviceOffered=serviceOffered;
    
    this.rateCardPriceUIModal.rateCard = new Array<RatePriceUIModal>();

    this._rateCardService.getRateCardPrice(serviceOffered.primaryIndustryId).subscribe(
      data => {
        this._logger.log(data);
        rateCardPriceResponseModal = JSON.parse(data);
        this._logger.log(rateCardPriceResponseModal);
        let rateCard: Array<RatePriceUIModal> = new Array<RatePriceUIModal>(9);

        for (let index = 0; index < 9; index++) {

          rateCard[index] = new RatePriceUIModal();
          rateCard[index].price = "";
          rateCard[index].serviceDayVOs = new ServiceDayUIVO();
          rateCard[index].serviceRatePeriodVO = new ServiceRatePeriodUIVO();
          rateCard[index].serviceRatePeriodVO.id = (Math.floor((index / 3) + 1));
          rateCard[index].serviceDayVOs.id = (index % 3) + 1;
        }
        if (null != rateCardPriceResponseModal.result) {
          rateCardPriceResponseModal.result.forEach((setVal: RateCardPrice) => {
            let ratePriceUIModal: RatePriceUIModal = new RatePriceUIModal();
            ratePriceUIModal.vendorCategoryPriceId = setVal.vendorCategoryPriceId;
            ratePriceUIModal.price = setVal.price ? setVal.price.toFixed(2) : "";
            ratePriceUIModal.serviceDayVOs = new ServiceDayUIVO();
            ratePriceUIModal.serviceRatePeriodVO = new ServiceRatePeriodUIVO();

            ratePriceUIModal.serviceRatePeriodVO.id = setVal.serviceRatePeriodVO.id;
            ratePriceUIModal.serviceDayVOs.id = setVal.serviceDayVOs.id;
            if (1 == ratePriceUIModal.serviceRatePeriodVO.id) {
              rateCard[(ratePriceUIModal.serviceRatePeriodVO.id - 1) + (ratePriceUIModal.serviceDayVOs.id - 1)] = ratePriceUIModal;
            } else {
              rateCard[((ratePriceUIModal.serviceRatePeriodVO.id - 1) * 3) + (ratePriceUIModal.serviceDayVOs.id - 1)] = ratePriceUIModal;
            }
          })
        } else if (rateCardPriceResponseModal.error) {
          this._logger.log("Error while retriving rate card details");
          this.rateCardLoader = false;
        }
        else {
          this._logger.log("No record found for rate card");
          this.rateCardLoader = false;
        }
        this.rateCardPriceUIModal.rateCard = rateCard;
        this.rateCardLoader = false;
        //this._logger.log("for ui rate card", this.rateCardPriceUIModal.rateCard);
        //this._logger.log("end getPriceOfRateCard");
      });

  }

  saveRateCardPrice(): void {

    let rateCardPriceResponseModal: RateCardPriceResponseModal;
    let rateCard: Array<RatePriceUIModal> = new Array<RatePriceUIModal>();
    this.rateCardPriceUIModal.rateCard.forEach(checkPrice => {
      if (checkPrice.price && checkPrice.price.trim().length > 0) {
        rateCard.push(checkPrice);
      }
    });

    let rateCardEventEmmiter: RateCardEventEmmiter = new RateCardEventEmmiter();
    rateCardEventEmmiter.rateCardAdded = "update";
    rateCardEventEmmiter.primaryIndustryId = this.serviceOffered.primaryIndustryId;
    this.rateCardAdded.emit(rateCardEventEmmiter);

    this._rateCardService.saveRateCardPrice(this.serviceOffered.primaryIndustryId, rateCard).subscribe(
      data => {
        this._logger.log(data);
        rateCardPriceResponseModal = JSON.parse(data);
        if (null != rateCardPriceResponseModal.result) {
          let rateCardEventEmmiter: RateCardEventEmmiter = new RateCardEventEmmiter();
          if (rateCard.length > 0) {
            rateCardEventEmmiter.rateCardAdded = "true";
          } else {
            rateCardEventEmmiter.rateCardAdded = "false";
          }

          rateCardEventEmmiter.primaryIndustryId = this.serviceOffered.primaryIndustryId;
          this.rateCardAdded.emit(rateCardEventEmmiter);
        }
        if (null != rateCardPriceResponseModal.error) {
          this._logger.log("Error while saving ratecard details");
          let rateCardEventEmmiter: RateCardEventEmmiter = new RateCardEventEmmiter();
          rateCardEventEmmiter.rateCardAdded = "false";
          rateCardEventEmmiter.primaryIndustryId = this.serviceOffered.primaryIndustryId;
          this.rateCardAdded.emit(rateCardEventEmmiter);
        }

      });
  }

  changePriceLimitValidation(index: number): void {
    this._logger.log("changePriceLimitValidation - index from page " + index);

    this._logger.log(this.rateCardPriceUIModal.rateCard[index].price)

    this.oldValidPrice = '';
    this.softValidateRateCard.price = this.rateCardPriceUIModal.rateCard[index].price;
    this._logger.log("changePriceLimitValidation - old price " + this.softValidateRateCard.price);
    this.softValidateRateCard.price = this.softValidateRateCard.price ? parseFloat(this.softValidateRateCard.price).toFixed(2) : "";
    this._logger.log("changePriceLimitValidation - new price " + this.softValidateRateCard.price);
    this.rateCardPriceUIModal.rateCard[index].priceValid = true;
    if (parseFloat(this.softValidateRateCard.price) > 999.99 && !jQuery("#softValidationLimitRateCardPrice").hasClass('in')) {
      this.index = index;

      this.rateCardPrice = this.softValidateRateCard.price
      jQuery("#saveRateCard").prop('disabled', true);
      this._logger.log("Opening validation popup...");
      jQuery("#softValidationLimitRateCardPrice").modal("show");
    }
  }

  keyPressListPrice(event: any, index: number): void {
    const pattern: RegExp = /[0-9\.]/;
    const wholePattern: RegExp = /^[0-9]{0,5}(\.[0-9]?[0-9]?)?$/;
    let inputChar: string = String.fromCharCode(event.charCode);
    this._logger.log("keyPressListPrice : ", inputChar, event.charCode, event.srcElement.value);
    this._logger.log(pattern.test(inputChar), wholePattern.test(event.srcElement.value));
    if (!pattern.test(inputChar) || !wholePattern.test(event.srcElement.value)) {
      this._logger.log("in-valid...");
      event.preventDefault();
    } else {
      this._logger.log("valid...");

    }
  }

  keyUpListPrice(event: any, index: number): void {
    const wholePattern: RegExp = /^[0-9]{0,5}(\.[0-9]?[0-9]?)?$/;
    //this.onPasteListPrice(event);
    this._logger.log("keyUpListPrice : ", event.charCode, event.srcElement.value);
    this._logger.log(wholePattern.test(event.srcElement.value));
    if (!wholePattern.test(event.srcElement.value)) {
      this._logger.log("in-valid...assigning new value");
      this.rateCardPriceUIModal.rateCard[index].price = this.oldValidPrice;
    } else {
      this._logger.log("valid...");
      this.oldValidPrice = event.srcElement.value;
    }
  }

  focusListPrice(event: any, index: number): void {
    this._logger.log("focusListPrice for index: ", index);
    this.oldValidPrice = this.rateCardPriceUIModal.rateCard[index].price;
  }

  disableMouseRightClick(): boolean {
    return false;
  }

  closeSoftValidationPopUp(ignoreValue: boolean): void {
    this._logger.log("closeSoftValidationPopUp - ignoreValue " + ignoreValue);
    if (!ignoreValue) {
      this._logger.log("Opening validation popup...");
      //this.softValidateSku.price = "";
      this.rateCardPriceUIModal.rateCard[this.index].price = "";
      this.rateCardPriceUIModal.rateCard[this.index].priceValid = false;
    }
    this._logger.log("closing validation popup...");
    jQuery("#softValidationLimitRateCardPrice").modal("hide");
    jQuery("#saveRateCard").prop('disabled', false);

  }

 updateOffersOn(serviceOfferedOn: ServiceOfferedOn, serviceOffered: ServiceOffered) {
        this._logger.log("selected serviceOfferedOn details;");
        this._logger.log(serviceOfferedOn.offersOnFlag);
        this._logger.log(serviceOffered.primaryIndustryId);
        let updateServiceOfferedOnVO: UpdateServiceOfferedOnVO = new UpdateServiceOfferedOnVO();
        updateServiceOfferedOnVO.luOffersOnVOforUpdate = serviceOfferedOn;
        updateServiceOfferedOnVO.offersOnFlag = serviceOfferedOn.offersOnFlag;
        updateServiceOfferedOnVO.primaryIndustry = serviceOffered.primaryIndustryId;

        this._rateCardService.updateOffersOn(updateServiceOfferedOnVO).subscribe(data => {
            let reply: any = JSON.parse(data);
            this._logger.log("reply from server in updateOffersOn: ", reply);
            if (reply.error) {
                serviceOfferedOn.offersOnFlag = !serviceOfferedOn.offersOnFlag;

                let errorModalTemp: ErrorModal = new ErrorModal();
                errorModalTemp.errorHeadingMessage = "Offers on";
                errorModalTemp.errors = ['Error occured while saving Offers on.'];
                this.errormessage.emit(errorModalTemp);
            }
        }, error => {
            serviceOfferedOn.offersOnFlag = !serviceOfferedOn.offersOnFlag;

            let errorModalTemp: ErrorModal = new ErrorModal();
            errorModalTemp.errorHeadingMessage = "Offers on";
            errorModalTemp.errors = ['Error occured while saving Offers on.'];
            this.errormessage.emit(errorModalTemp);
            this._logger.log("error from server in updateOffersOn: ", error);
        });
    }

}