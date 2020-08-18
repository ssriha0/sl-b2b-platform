import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import {Input, } from '@angular/core';

import {ProviderZIPCodeCoverageService} from '../common/service/provider-zipcodecoverage.service';


import { WaitLoaderComponent } from '../common/component/wait-loader/wait-loader.component';
import {LoggerUtil} from '../common/util/logger.util';

import {DropDownZipCodes} from '../common/modal/provider-coverage/dropdown.zipcodes';
import { CoverageAPIZipCodes } from '../common/modal/provider-coverage/coverage.apizipcodes';
import { CoverageLocation } from '../common/modal/provider-profile/provider-details.modal';
import { ProviderProfileService } from '../common/service/provider-profile/provider-profile.service';
declare var jQuery: any;

@Component({
  selector: 'provider-coverage',
  template: require('./provider-coverage.component.html'),
  styles: [
    require('./provider-coverage.component.css')
  ],
  providers: [ProviderZIPCodeCoverageService, ProviderProfileService]
})
export class ProviderZIPCodeCoverageComponent implements OnInit {

  @Output() private reLoadDetailPage = new EventEmitter<boolean>();
  @Output() private errormessage = new EventEmitter<Array<string>>();
  @Output() private reload: EventEmitter<string> = new EventEmitter();
  @Input() private coverageRadius: number;
  @Input() private coverageZip: string;
  @Input() private locId: string;
  // @Output() private passCoverage: EventEmitter<CoverageLocation> = new EventEmitter();
  @Output() private passCoverage: EventEmitter<CoverageLocation[]> = new EventEmitter();
  @Output() private deleteCoverage: EventEmitter<CoverageLocation> = new EventEmitter();
  @Output() private showMessage: EventEmitter<string> = new EventEmitter();
  
  

  private initCoverageRadius: number;
  private showCoverageLoader: boolean = false;
  //private showCoverageLoader: string = "loading..";
  private isEditFlow: boolean = false;
  private apizipcodes: CoverageAPIZipCodes[];
  private number: string[];
  private zipCodesMessage: string = "No zip codes to display";
  filterSeletedApiZipCodes: CoverageAPIZipCodes[];
  filterUnSeletedApiZipCodes: CoverageAPIZipCodes[];
  coverageLocations:CoverageLocation[];
 

  //pagination declaration start

  pages: number = 4;
  pageSize: number = 50;
  pageNumber: number = 0;
  currentIndex: number = 1;
  paginatedApiZipCodes: CoverageAPIZipCodes[];
  pagesIndex: Array<number>;
  pageStart: number = 1;
  inputName: string = '';
  totalRecords:number;
  curPageStartIndex:number;
  curPageEndIndex:number;


  //pagination end



  constructor(private _providerZIPCodeCoverageService: ProviderZIPCodeCoverageService, private _proviverProfileService: ProviderProfileService, private _logger: LoggerUtil) {
  }
  ngOnInit() {
    this.showCoverageLoader = false;
  }

  increment() {
    if(this.coverageRadius <100){
     this.coverageRadius++;
    }
    
  }

  decrement() {
    if (this.coverageRadius > 0) {
      this.coverageRadius--;
    }
  }

  searchZipCodes() {
    this.resetZipCodes();
    this._logger.log("inside searchZipCodes");
    this.showCoverageLoader = true;
    //this.showCoverageLoader = "Loading zipcodes...";

    this._providerZIPCodeCoverageService.searchZipCodesByZipRange(this.coverageZip, this.coverageRadius, this.locId)
      .subscribe(dataList => {
        this.apizipcodes = JSON.parse(dataList).zipCodesData;
        let filterSeletedApiZipCodes = new Array<any>();
        let filterUnSeletedApiZipCodes = new Array<any>();
        this.apizipcodes.filter((item) => !item.selected).map(code => filterUnSeletedApiZipCodes.push(code));
        //add selected zipcodes first and unselected zipcodes last
        if(filterUnSeletedApiZipCodes.length>0){
         this.apizipcodes.filter((item) => item.selected).map(code => filterSeletedApiZipCodes.push(code));
         this.apizipcodes=filterSeletedApiZipCodes;

          for(let zipCode of filterUnSeletedApiZipCodes){
             this.apizipcodes.push(zipCode);
        }

        }
		this.totalRecords = this.apizipcodes.length;
        this.init();
        this._logger.log("in component coverage");
        this._logger.log("zip codes size which are retrieved from api and DB: "+this.apizipcodes.length)
        //this._logger.log(dataList);
        if (null == this.apizipcodes || this.apizipcodes.length <= 0) {
          this.zipCodesMessage = "Error occured while retrieving zip codes. Please contact system admin.";
        }
        this.showCoverageLoader = false;
        jQuery('#coverageZipModel').modal('handleUpdate');
      }, error => {
        this.showCoverageLoader = false;
      }
      );

    this.initCoverageRadius = this.coverageRadius;
  }
  //pagenation code start
  init() {
    this.currentIndex = 1;
    this.pageStart = 1;
    this.pages = 4;

    this.pageNumber = parseInt("" + (this.apizipcodes.length / this.pageSize));
    if (this.apizipcodes.length % this.pageSize != 0) {
      this.pageNumber++;
    }

    if (this.pageNumber < this.pages) {
      this.pages = this.pageNumber;

    }

    this.refreshItems();
    console.log("this.pageNumber :  " + this.pageNumber);
  }
  // FilterByName() {
  //   this.apizipcodes = [];

  //   if (this.inputName != "") {
  //     this.apizipcodes.forEach(element => {
  //       if (element.name.toUpperCase().indexOf(this.inputName.toUpperCase()) >= 0) {
  //         this.apizipcodes.push(element);
  //       }
  //     });
  //   } else {
  //     this.apizipcodes = this.apizipcodes;
  //   }
  //   console.log(this.apizipcodes);
  //   this.init();
  // }
  fillArray(): any {
    var obj = new Array();
    for (var index = this.pageStart; index < this.pageStart + this.pages; index++) {
      obj.push(index);
    }
    return obj;
  }
  refreshItems() {
    this.paginatedApiZipCodes = this.apizipcodes.slice((this.currentIndex - 1) * this.pageSize, (this.currentIndex) * this.pageSize);
    this.curPageStartIndex=(this.currentIndex - 1) * (this.pageSize) + 1;
    this.curPageEndIndex=(this.currentIndex) * (this.pageSize);
    if(this.curPageEndIndex > this.totalRecords){
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
  
  checkbox(selectedAPIZipCodes: any) {
    selectedAPIZipCodes.selected = (selectedAPIZipCodes.selected) ? true : false;
  }

  private getSelected() {
    return this.apizipcodes.filter((item) => item.selected);
     
  }
  private filterselectedZipCodes(){
   
    this.apizipcodes.filter((item) => item.selected).map(code => this.filterSeletedApiZipCodes.push(code));
  }
  private filterUnselectedZipCodes(){
    this.apizipcodes.filter((item) => !item.selected).map(code => this.filterUnSeletedApiZipCodes.push(code));
  }

  public saveRadiusAndSeletedZIPCodes() {
    // this.reLoadDetailPage.emit(false);
    this._logger.log("inside saveSeletedZIPCodes");

    this._logger.log("Edit Flow: " + this.isEditFlow);

    // service call for saving zip code details
    if (this.isEditFlow) {
      this.showMessage.emit("Updating zip codes...");
      this.saveSelectedZipCodes();
    } else {
      this.showMessage.emit("Saving coverage area and zip codes...");
      this.saveZipCoverageRadius();
      
    }
  }

  private saveSelectedZipCodes(): void {
    this._logger.log("service call saveSelectedZipCodes");
    // calling service to save selected zip code 
    let selected = new Array<any>();
    this.apizipcodes.filter((item) => item.selected).map(code => selected.push(code));

    // this._logger.log(this.apizipcodes);
    this._logger.log(selected);

    this._providerZIPCodeCoverageService.saveSeletedZIPCodes(selected, this.locId, this.initCoverageRadius)
      .subscribe(
      data => {
        this._logger.log('Response received');
        this._logger.log(this.isEditFlow);
        //this._logger.log(data);
        //this.apizipcodes = Array<CoverageAPIZipCodes>();
        this.resetZipCodes();
        //this.reLoadDetailPage.emit(false);
        //this.reloadPage();
         this.getCoverageAreas();
        // if(!this.isEditFlow ) {
        //   this.getCoverageAreas();
       
        // }else{
        //    this.showMessage.emit(null);
        // }
        // if(!this.isEditFlow) {
        // let coverageLocation:CoverageLocation = new CoverageLocation();
        // coverageLocation.coverageRadius=this.initCoverageRadius;
        // coverageLocation.zip=this.coverageZip;
        // coverageLocation.locnId = this.locId;

        // this.passCoverage.emit(coverageLocation);
        // }else{
        //    this.showMessage.emit(null);
        // }
      }, error => {
        this._logger.log('Error');
        //this.apizipcodes = Array<CoverageAPIZipCodes>();
        this.resetZipCodes();
        //this.reLoadDetailPage.emit(false);
        //this.reloadPage();
      });
  }

  validateCoverageRadius() {
    if (this.coverageRadius && this.coverageRadius < 0) {
      this.coverageRadius = 0;
    }
  }

  saveZipCoverageRadius(): void {
    this._logger.log("inside saveZipCoverageRadius");
    let jsonString: string;

    jsonString = JSON.stringify({ locId: this.locId, coverageRadius: this.initCoverageRadius });
    this._proviverProfileService.saveZipCoverageRadius(jsonString).subscribe(
      data => {
        this._logger.log(data);
        this.saveSelectedZipCodes();
      }, error => {
        this._logger.log(error);
        //this.reLoadDetailPage.emit(false);
      });

    // this.apizipcodes = Array<CoverageAPIZipCodes>();
    // this.reloadPage();
    // this.searchZipCodes();
  }

  
  editZipCode(coverageRadius: number, coverageZip: string, locId: string): void {
    this.isEditFlow = true;
    this.initCoverageRadius = this.coverageRadius;
    this._logger.log("inside editZipCode");

    // init variables
    this.showCoverageLoader = false;

    this.coverageRadius = coverageRadius;
    this.coverageZip = coverageZip;
    this.locId = locId;

    this.apizipcodes = Array<CoverageAPIZipCodes>();

    this.searchZipCodes();
  }

  createNewCoverage() {
    this.isEditFlow = false;
    this.initCoverageRadius = this.coverageRadius;
    // init variables
    this.showCoverageLoader = false;
    this.searchZipCodes();
  }
  resetZipCodes() {
     this.apizipcodes = null;
     this.pagesIndex = null;
     this.currentIndex=1;
     this.pageNumber=0;
  }

  reloadPage() {
    this.reload.emit('reload');
  }

  getCoverageAreas(){
    this._proviverProfileService.getCoverageAreas()
      .subscribe(dataList => {
         this.coverageLocations = JSON.parse(dataList)
         this._logger.log("inside getCoverageAreas");
         this._logger.log(this.coverageLocations);
         if(null!=this.coverageLocations && this.coverageLocations.length>0 ){
            this.passCoverage.emit(this.coverageLocations);
          }else{
           this.coverageLocations = new Array<CoverageLocation>();
           this.passCoverage.emit(this.coverageLocations);
          }
        
      }, error => {
        
      });

  }
}







