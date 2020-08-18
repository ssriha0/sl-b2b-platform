import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';
import { BrowserModule }  from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { HttpRestAPIService } from './services/http-rest-api.service';
import { SOActionService } from './services/so-action-button.service';


import { LoggerUtil } from './common/util/logger';
import { DataStorageUtil } from './common/util/data-storage';

import { AppComponent } from './app.component';

import { CalendarHeaderComponent } from './pages/calendar-header/calendar-header.component';
import { SOActionPopupComponent } from './pages/so-action-popup/so-action-popup.component';
import { DayViewComponent } from './pages/day-view-calendar/day-view.component';
import { WeekViewComponent } from './pages/week-view-calendar/week-view.component';
import { MonthViewComponent } from './pages/month-view-calendar/Month-view.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { AddEventPopupComponent } from './pages/add-event-popup/add-event-popup.component';
import { LoaderComponent } from './pages/loader/loader.component';

@NgModule({
  imports: [
    HttpModule,
    BrowserModule,
    FormsModule,
    BrowserAnimationsModule
  ],
  declarations: [
    AppComponent,
    CalendarHeaderComponent,
    DayViewComponent,
    WeekViewComponent,
    MonthViewComponent,
    PageNotFoundComponent,
    SOActionPopupComponent,
    AddEventPopupComponent,
    LoaderComponent
  ],
  providers: [
    SOActionService,
    HttpRestAPIService,
    LoggerUtil,
    DataStorageUtil
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
