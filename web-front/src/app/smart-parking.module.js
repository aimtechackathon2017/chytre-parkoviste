"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var platform_browser_1 = require('@angular/platform-browser');
var http_1 = require('@angular/http');
var smart_parking_component_1 = require('./smart-parking.component');
var parking_lot_component_1 = require("./parking-lot.component");
var SmartParkingModule = (function () {
    function SmartParkingModule() {
    }
    SmartParkingModule = __decorate([
        core_1.NgModule({
            imports: [
                platform_browser_1.BrowserModule,
                http_1.HttpModule
            ],
            declarations: [smart_parking_component_1.SmartParkingComponent, parking_lot_component_1.ParkingLotComponent],
            bootstrap: [smart_parking_component_1.SmartParkingComponent]
        }), 
        __metadata('design:paramtypes', [])
    ], SmartParkingModule);
    return SmartParkingModule;
}());
exports.SmartParkingModule = SmartParkingModule;
//# sourceMappingURL=smart-parking.module.js.map