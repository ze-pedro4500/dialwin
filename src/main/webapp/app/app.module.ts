import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { DialwinSharedModule } from 'app/shared/shared.module';
import { DialwinCoreModule } from 'app/core/core.module';
import { DialwinAppRoutingModule } from './app-routing.module';
import { DialwinHomeModule } from './home/home.module';
import { DialwinEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';








@NgModule({
  imports: [
    NgbModule,
    BrowserModule,
    DialwinSharedModule,
    DialwinCoreModule,
    DialwinHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    DialwinEntityModule,
    DialwinAppRoutingModule
  ],
  declarations: [JhiMainComponent,
     NavbarComponent, 
     ErrorComponent, 
     PageRibbonComponent, 
     ActiveMenuDirective, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class DialwinAppModule {}