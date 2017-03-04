import {Component} from '@angular/core';
import 'rxjs/add/operator/map';

import { ParkingLotService } from './parking-lot.service';

import { ParkingPlace } from './parking-place';

@Component({
    selector: 'parking-lot',
    template: `<ul class="parking-places col-12">
                    <li *ngFor="let parkingPlace of parkingPlaces"
                        [class.occupied]="!parkingPlace.available"
                        class="parking-place">
                         <div class="sign">{{parkingPlace.place_id}}</div>
                         <div class="status">{{parkingPlace.available ? 'Free' : 'Occupied'}}</div>
                         <div class="parking-inside"></div>
                    </li>
               </ul>`,
    providers: [ParkingLotService]
})
export class ParkingLotComponent {

    parkingPlaces: ParkingPlace[];
    constructor(private parkingLotService: ParkingLotService) {}

    getData(): void {
        this.parkingLotService
            .getData()
            .then(parkingPlaces => this.parkingPlaces = parkingPlaces);
        this.parkingLotService
            .getDataPeriodically().subscribe(
            parkingPlaces => this.parkingPlaces = parkingPlaces
        );
    }

    ngOnInit(): void {
        this.getData();
    };

}
