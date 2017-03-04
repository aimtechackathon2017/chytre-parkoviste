import { Injectable }    from '@angular/core';
import { Http } from '@angular/http';
import {Observable} from "rxjs/Rx";
import 'rxjs/add/operator/toPromise';

import { ParkingPlace } from './parking-place';

@Injectable()
export class ParkingLotService {
    private dataUrl = 'api/parkingPlaces';

    constructor(private http: Http) { }

    getDataPeriodically(): Observable<ParkingPlace[]> {
        return Observable
            .interval(10000)
            .flatMap(() => {
                return this.getData()
            });
    }

    getData(): Promise<ParkingPlace[]> {
        return this.http.get(this.dataUrl)
            .toPromise()
            .then(response => response.json().data as ParkingPlace[])
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}
