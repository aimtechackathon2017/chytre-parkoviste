import {NgModule}      from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import { HttpModule }    from '@angular/http';

// Imports for loading & configuring the in-memory web api
import { InMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService }  from './in-memory-data.service';

import {SmartParkingComponent}  from './smart-parking.component';
import {ParkingLotComponent} from "./parking-lot.component";

@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        InMemoryWebApiModule.forRoot(InMemoryDataService)],
    declarations: [SmartParkingComponent, ParkingLotComponent],
    bootstrap: [SmartParkingComponent]
})
export class SmartParkingModule {
}
