import { Component, OnInit, ChangeDetectorRef, Input, Output, EventEmitter, ViewChild, ElementRef } from '@angular/core';
import {LoggerUtil} from '../../common/util/logger.util';
import { ProviderProfileService } from '../../common/service/provider-profile/provider-profile.service';
import {DomSanitizer} from '@angular/platform-browser';
import { JsonpModule } from '@angular/http';

import { CustomFileModal } from '../../common/modal/profile-image/profile-image.modal';
import { ErrorModal } from '../../common/modal/error.modal';
declare var jQuery: any;

@Component({
  selector: 'profile-image',
  template: require('./profile-image.component.html'),
  styles: [
    require('./profile-image.component.css')
  ]
})
export class ProfileImage implements OnInit {

  @Output()
  private imageReceived = new EventEmitter<boolean>();

  @Output()
  private reLoadDetailPage = new EventEmitter<boolean>();

  @Output()
  private errormessage = new EventEmitter<ErrorModal>();

  private imageData: string = "";
  private errorpopUpModal = new ErrorModal();
  @ViewChild('input') el: ElementRef;
  // public imageExist: boolean;
  public file_srcs: Array<string> = new Array<string>();
  private file: File;
  private files: FileList;
  private error: any;

  //private sanitizer:DomSanitizer;
  constructor(private _proviverProfileService: ProviderProfileService, private changeDetectorRef: ChangeDetectorRef, private _logger: LoggerUtil, private _sanitizer: DomSanitizer) {
    //   this.imageExist = false;
  }

  ngOnInit(): void {
    this.getFirmLogoWithoutLoaderOnUI();
  }
  // This is called when the user selects new files from the upload button
  fileChange(input: any) {
    this.files = input.files;
    this.file = this.files[0];
    if (this.file.type.toString() != "image/jpeg" && this.file.type.toString() != "image/jpg" && this.file.type.toString() != "image/gif" && this.file.type.toString() != "image/png") {
      this.errorpopUpModal.errors = ["Invalid file type. Please select valid file type."];
      this.errorpopUpModal.errorHeadingMessage = "Firm Logo";
      this.errormessage.emit(this.errorpopUpModal);
      this.clearFileName();
      return;
    }

    if (this.file.size > 5000000 || this.file.size > 5242880) {
      this.errorpopUpModal.errors = ["Too big a file. Resize the logo to less than 5MB file size and try again."];
      this.errorpopUpModal.errorHeadingMessage = "Firm Logo";
      this.errormessage.emit(this.errorpopUpModal);
      this.clearFileName();
      return;
    }
    this.readFiles(input.files);
  }

  readFiles(files: any, index = 0) {
    // Create the file reader
    let reader = new FileReader();

    // If there is a file
    if (index in files) {
      // Start reading this file
      this.readFile(files[index], reader, (result: any) => {
        // After the callback fires do:
        this.resetFileData();
        this.file_srcs.push(result);

        //this.readFiles(files, index+1);// Read the next file;
      });
    } else {
      // When all files are done This forces a change detection
      this.changeDetectorRef.detectChanges();
    }
  }


  readFile(file: any, reader: FileReader, callback: any) {
    // Set a callback funtion to fire after the file is fully loaded
    reader.onload = () => {
      // callback with the results
      callback(reader.result);
    }

    // Read the file
    reader.readAsDataURL(file);
  }


  fileUpload(input: any) {
    //this.reLoadDetailPage.emit(false);
    this.files = input.files;
    //this._logger.log(files[0]);
    this.file = this.files[0];
    this._logger.log(this.file);

    this.imageData = this.file_srcs[0];
    let customFile: CustomFileModal = new CustomFileModal();
    customFile.lastModified = JSON.stringify(this.file.lastModifiedDate);
    customFile.lastModifiedDate = this.file.lastModifiedDate;
    customFile.name = this.file.name;
    customFile.size = this.file.size.toString();
    customFile.type = this.file.type;
    customFile.webkitRelativePath = "";

    this._logger.log(customFile);

    this._proviverProfileService.uploadFirmLogo(this.file_srcs, customFile).subscribe(
      data => {
        this._logger.log(data);
        this.resetAllData();

        this.error = JSON.parse(data);
        this._logger.log(this.error);
        if (null != this.error.error) {
          this.getFirmLogo();
          // this.reLoadDetailPage.emit(false);
          this.errorpopUpModal.errors = [this.error.error]
          this.errorpopUpModal.errorHeadingMessage = "Firm Logo"
          this.errormessage.emit(this.errorpopUpModal);
        } else {
          //this.getFirmLogo();
          this._logger.log("image successfully saved");
        }
      }, error => {
        this.getFirmLogo();
        this._logger.log(error);
        //this.reLoadDetailPage.emit(false);
        this.resetAllData();
        this.errorpopUpModal.errors = ["Photo was not uploaded."];
        this.errorpopUpModal.errorHeadingMessage = "Firm Logo";
        this.errormessage.emit(this.errorpopUpModal);
      });
  }

  getFirmLogoWithoutLoaderOnUI() {
    let sendLoadingIndicator: boolean = false;
    this.getFirmLogo(sendLoadingIndicator);
  }

  getFirmLogo(sendLoadingIndicator: boolean = true) {
    this._proviverProfileService.fetchFirmLogo().subscribe(
      data => {
        this._logger.log("Received image.");
        this.imageData = JSON.parse(data).result;
        if (sendLoadingIndicator) {
          //this.reLoadDetailPage.emit(false);
        }
      }, error => {
        this._logger.log(error);
        if (sendLoadingIndicator) {
          //this.reLoadDetailPage.emit(false);
        }
      }, () => {
        this.imageReceived.emit(this.imageData && this.imageData.trim().length > 0 ? true : false);
      });
  }

  resetFileData() {
    this.file_srcs = new Array<string>();
    this.file = null;

  }

  resetAllData(): void {
    this.clearFileName();
    this.resetFileData();
  }

  clearFileName() {
    this.el.nativeElement.value = "";
    this.file_srcs = null;
  }

  sanitize(url: string) {
    return null != url && url.length > 0 ? this._sanitizer.bypassSecurityTrustUrl(url) : "";
  }

  rotateMe():void{
    
  }
}