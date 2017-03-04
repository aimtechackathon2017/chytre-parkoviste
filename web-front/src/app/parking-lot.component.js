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
require('rxjs/add/operator/map');
var parking_lot_service_1 = require('./parking-lot.service');
var ParkingLotComponent = (function () {
    function ParkingLotComponent(parkingLotService) {
        this.parkingLotService = parkingLotService;
    }
    ParkingLotComponent.prototype.getData = function () {
        var _this = this;
        this.parkingLotService
            .getData()
            .then(function (parkingPlaces) { return _this.parkingPlaces = parkingPlaces; });
        this.parkingLotService
            .getDataPeriodically().subscribe(function (parkingPlaces) { return _this.parkingPlaces = parkingPlaces; });
    };
    ParkingLotComponent.prototype.ngOnInit = function () {
        this.getData();
    };
    ;
    ParkingLotComponent = __decorate([
        core_1.Component({
            selector: 'parking-lot',
            template: "<ul class=\"parking-places col-12\">\n                    <li *ngFor=\"let parkingPlace of parkingPlaces\"\n                        [class.occupied]=\"parkingPlace.occupied\"\n                        class=\"parking-place\">\n                         <div class=\"sign\">{{parkingPlace.id}}</div>\n                         <div class=\"status\">{{parkingPlace.occupied ? 'Occupied' : 'Free'}}</div>\n                         <div class=\"parking-inside\"></div>\n                    </li>\n               </ul>",
            providers: [parking_lot_service_1.ParkingLotService]
        }), 
        __metadata('design:paramtypes', [parking_lot_service_1.ParkingLotService])
    ], ParkingLotComponent);
    return ParkingLotComponent;
}());
exports.ParkingLotComponent = ParkingLotComponent;
//# sourceMappingURL=parking-lot.component.js.map