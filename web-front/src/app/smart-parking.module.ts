import {NgModule}      from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import { HttpModule }    from '@angular/http';

import {SmartParkingComponent}  from './smart-parking.component';
import {ParkingLotComponent} from "./parking-lot.component";

@NgModule({
    imports: [
        BrowserModule,
        HttpModule
    ],
    declarations: [SmartParkingComponent, ParkingLotComponent],
    bootstrap: [SmartParkingComponent]
})
export class SmartParkingModule {
}
