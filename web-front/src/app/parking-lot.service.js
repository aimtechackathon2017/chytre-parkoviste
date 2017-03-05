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
var http_1 = require('@angular/http');
var Rx_1 = require("rxjs/Rx");
require('rxjs/add/operator/toPromise');
var ParkingLotService = (function () {
    function ParkingLotService(http) {
        this.http = http;
        this.dataUrl = 'https://7j62gv0mfg.execute-api.eu-west-1.amazonaws.com/hacking/place/';
    }
    ParkingLotService.prototype.getDataPeriodically = function () {
        var _this = this;
        return Rx_1.Observable
            .interval(5000)
            .flatMap(function () {
            return _this.getData();
        });
    };
    ParkingLotService.prototype.getData = function () {
        return this.http.get(this.dataUrl)
            .toPromise()
            .then(function (response) {
            var data = JSON.parse(response.json().body);
            data.sort(function (a, b) { return a.place_id - b.place_id; });
            console.log(data);
            return data;
        })
            .catch(this.handleError);
    };
    ParkingLotService.prototype.handleError = function (error) {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    };
    ParkingLotService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], ParkingLotService);
    return ParkingLotService;
}());
exports.ParkingLotService = ParkingLotService;
//# sourceMappingURL=parking-lot.service.js.map