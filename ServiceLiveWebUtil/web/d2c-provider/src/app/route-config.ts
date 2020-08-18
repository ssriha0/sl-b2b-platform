
import { Routes } from '@angular/router';
import { RouterModule } from '@angular/router';

import {GetStarted} from './get-started/get-started.component';
import {ProviderProfile} from './provider-profile/provider-profile.component';
import {ServiceOfferedDetailComponent} from './service-offered/service-offered-detail.component';
import {SelectService} from './select-services/select-services.component';
import {ProviderZIPCodeCoverageComponent} from './provider-coverage/provider-coverage.component';

const appRoutes : Routes = [
    { path: '', component: GetStarted },
    // { path: '', component: ProviderProfile },
    { path: 'ServiceLiveWebUtil/d2c-provider', component: GetStarted },
    { path: 'MarketFrontend/providerPortalAction.action', component: GetStarted },
    { path: 'getStarted', component: GetStarted },
    { path: 'profile', component: ProviderProfile },
    { path: 'coverage', component: ProviderZIPCodeCoverageComponent },
    { path: 'serviceOffered', component: ServiceOfferedDetailComponent },
    { path: 'selectService', component: SelectService }
];

export const AppRoutingModule = RouterModule.forRoot(appRoutes);