import { NgModule } from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { HttpModule, JsonpModule } from '@angular/http';

import { FormsModule } from '@angular/forms';

import {HttpRestAPIService} from './common/service/http-restapi.service';
import {DataStorageUtil} from './common/util/data-storage.util';
import {LoggerUtil} from './common/util/logger.util';

import { AppComponent } from './app.component';
import { AppRoutingModule }  from './route-config';

import {SLConsumerHeader} from './common/component/sl-consumer-header.component';
import {WaitLoaderComponent} from './common/component/wait-loader/wait-loader.component';

import {ProviderProfile} from './provider-profile/provider-profile.component';
import {ProfileImage} from './provider-profile/profile-image/profile-image.component';

import {ServiceOfferedDetailComponent} from './service-offered/service-offered-detail.component';
import {SelectService} from './select-services/select-services.component';

import {GetStarted} from './get-started/get-started.component';
import {ServiceCarousel} from './get-started/service-carousel/service-carousel.component';
import {ProviderZIPCodeCoverageComponent} from './provider-coverage/provider-coverage.component';
import { RateCard } from './rate-card/rate-card.component';

@NgModule({
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpModule,
        JsonpModule,
        FormsModule
    ],
    declarations: [
        AppComponent,

        ProviderProfile,
        SLConsumerHeader,
        ProfileImage,

        ServiceOfferedDetailComponent,
        RateCard,
        SelectService,
        GetStarted,
        ServiceCarousel,
        ProviderZIPCodeCoverageComponent,

        WaitLoaderComponent
    ],
    providers: [
        HttpRestAPIService,
        DataStorageUtil,
        LoggerUtil
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
