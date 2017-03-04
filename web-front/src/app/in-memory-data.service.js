"use strict";
var InMemoryDataService = (function () {
    function InMemoryDataService() {
    }
    InMemoryDataService.prototype.createDb = function () {
        var parkingPlaces = [
            { id: 1, occupied: true },
            { id: 2, occupied: false },
            { id: 3, occupied: false }
        ];
        return { parkingPlaces: parkingPlaces };
    };
    return InMemoryDataService;
}());
exports.InMemoryDataService = InMemoryDataService;
//# sourceMappingURL=in-memory-data.service.js.map