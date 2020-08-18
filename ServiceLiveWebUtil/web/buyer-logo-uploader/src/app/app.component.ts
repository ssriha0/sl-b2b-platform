import { LogoService } from './logo.service';
import { DomSanitizer } from '@angular/platform-browser';
import { Component, AfterViewInit, Input, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { CustomFileModal, ErrorModal } from './models';
import { setTheme } from 'ngx-bootstrap/utils';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { createCustomElement } from '@angular/elements';
import { Ng2ImgMaxService } from 'ng2-img-max';

@Component({
  selector: 'buyer-logo-uploader',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements AfterViewInit {
  @Input() currentLogo = '';
  @Input() logoPath = '';
  errorpopUpModal: ErrorModal = new ErrorModal();
  @ViewChild('input') el: ElementRef;
  @ViewChild('errorModal') errorElem: ModalDirective;
  @ViewChild('uploadPicModal') uploadElem: ModalDirective;
  private response: any;
  private LOGO_WIDTH = 250;
  private LOGO_HEIGHT = 100;
  uploadedImage: Blob;
  imagePreview: any;


  constructor(
    private service: LogoService,
    private sanitizer: DomSanitizer,
    private renderer: Renderer2,
    private ng2ImgMax: Ng2ImgMaxService
  ) {
    setTheme('bs3');
  }

  ngAfterViewInit() {
    const externalLogoPath: string = document.querySelector('input[name="buyerLogoBaseURL"]').getAttribute('value');
    const externalLogo: string = document.querySelector('input[name="buyerSurveyDTO.buyerLogo"]').getAttribute('value');
    if (!this.logoPath) {
      this.logoPath = externalLogoPath;
    }

    if (!this.currentLogo) {
      this.currentLogo = externalLogo;
    }
  }

  openModalWithComponent() {
    this.uploadElem.show();
  }

  hideModalWithComponent() {
    this.resetAllData();
    this.uploadElem.hide();
  }

  private displayError() {
    console.log('displaying error popup', this.errorpopUpModal);
    this.errorElem.show();
  }

  sanitize(url: string) {
    return null != url && url.length > 0
      ? this.sanitizer.bypassSecurityTrustUrl(url)
      : '';
  }

  onImageChange(event) {
    const image = event.target.files[0];
    if (this.validate(image)) {
        this.resize(image);
    }
  }

  getImagePreview(file: Blob) {
    const reader: FileReader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      this.imagePreview = reader.result;
    };
  }

  resize(image: File) {
    if (image.type.toString() === 'image/gif') {
      this.service.resizeGIF(image, this.LOGO_WIDTH, this.LOGO_HEIGHT).subscribe(
        result => {
          this.uploadedImage = result;
          this.getImagePreview(this.uploadedImage);
        },
        error => {
          console.log('😢 Oh no!', error);
          this.resetAllData();
          this.errorpopUpModal.errors = ['Issue with resizing'];
          this.errorpopUpModal.errorHeadingMessage = 'Buyer Logo';
          this.displayError();
        }
      );
    } else {
      this.ng2ImgMax.resizeImage(image, this.LOGO_WIDTH, this.LOGO_HEIGHT).subscribe(
        result => {
          this.uploadedImage = result;
          this.getImagePreview(this.uploadedImage);
        },
        error => {
          console.log('😢 Oh no!', error);
          this.resetAllData();
          this.errorpopUpModal.errors = ['Issue with resizing'];
          this.errorpopUpModal.errorHeadingMessage = 'Buyer Logo';
          this.displayError();
        }
      );
    }
  }

  // compress(image: File) {
  //   this.ng2ImgMax.compressImage(image, 0.075).subscribe(
  //     result => {
  //       this.uploadedImage = new File([result], result.name);
  //       this.getImagePreview(this.uploadedImage);
  //     },
  //     error => {
  //       console.log('😢 Oh no!', error);
  //       this.resetLogoFile();
  //       this.resetAllData();
  //       this.errorpopUpModal.errors = ['Issue with compression'];
  //       this.errorpopUpModal.errorHeadingMessage = 'Buyer Logo';
  //       this.displayError();
  //     }
  //   );
  // }

  validate(logo: File): boolean {
    if (
      logo.type.toString() !== 'image/jpeg' &&
      logo.type.toString() !== 'image/jpg' &&
      logo.type.toString() !== 'image/gif' &&
      logo.type.toString() !== 'image/png'
    ) {
      this.errorpopUpModal.errors = [
        'Invalid file type. Please select valid file type.'
      ];
      this.errorpopUpModal.errorHeadingMessage = 'Buyer Logo';
      this.displayError();
      this.clearFileName();
      return false;
    }

    if (logo.size > 5242880) {
      this.errorpopUpModal.errors = [
        'Too big a file. Resize the logo to less than 5MB file size and try again.'
      ];
      this.errorpopUpModal.errorHeadingMessage = 'Buyer Logo';
      this.displayError();
      this.clearFileName();
      return false;
    }
    return true;
  }

  fileUpload() {
    console.log(this.uploadedImage);

    const customFile: CustomFileModal = new CustomFileModal();
    customFile.lastModified = JSON.stringify(this.uploadedImage['lastModifiedDate']);
    customFile.lastModifiedDate = JSON.stringify(this.uploadedImage['lastModifiedDate']);
    customFile.name = this.uploadedImage['name'];
    customFile.size = this.uploadedImage.size.toString();
    customFile.type = this.uploadedImage.type;
    customFile.webkitRelativePath = '';

    console.log(customFile);

    this.service
      .uploadFirmLogo([this.imagePreview], customFile)
      .subscribe(
        data => {
          console.log(data);
          this.resetAllData();

          this.response = JSON.parse(data);
          console.log(this.response);
          if (this.response.status === 'SUCCESS' || this.response.status === 'success') {
            const externalElem = document.querySelector('input[name="buyerSurveyDTO.buyerLogo"]');
            this.currentLogo = this.response.buyerLogoName;
            this.renderer.setAttribute(externalElem, 'value', this.currentLogo);
            console.log('image successfully saved');
            this.uploadElem.hide();
          } else {
            this.errorpopUpModal.errors = [this.response.message];
            this.errorpopUpModal.errorHeadingMessage = 'Buyer Logo';
            this.displayError();
          }
        },
        error => {
          console.log(error);
          this.resetAllData();
          this.errorpopUpModal.errors = ['Logo was not uploaded.'];
          this.errorpopUpModal.errorHeadingMessage = 'Buyer Logo';
          this.displayError();
        }
      );
  }

  resetFileData() {
    this.imagePreview = '';
    this.uploadedImage = null;
  }

  resetAllData(): void {
    this.clearFileName();
    this.resetFileData();
    // this.uploadElem.hide();
  }

  clearFileName() {
    this.el.nativeElement.value = '';
    this.imagePreview = null;
  }

  rotateMe(): void {}
}
