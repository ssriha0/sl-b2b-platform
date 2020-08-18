import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { CsatOnlyComponent } from './csat-only/csat-only.component';
import { SharedModule } from './shared/shared.module';
import { NpsOnlyComponent } from './nps-only/nps-only.component';
import { CsatPrioritizedComponent } from './csat-prioritized/csat-prioritized.component';
import { NpsPrioritizedComponent } from './nps-prioritized/nps-prioritized.component';
import { AppRoutingModule } from './app-routing.module';

@NgModule({
  declarations: [
    AppComponent,
    CsatOnlyComponent,
    NpsOnlyComponent,
    CsatPrioritizedComponent,
    NpsPrioritizedComponent
  ],
  imports: [
    BrowserModule,
    SharedModule.forRoot(),
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
