import { Injectable }    from '@angular/core';
import {Http, Headers, RequestOptions} from '@angular/http';
import {Observable} from "rxjs/Rx";
import 'rxjs/add/operator/toPromise';

import { ParkingPlace } from './parking-place';

@Injectable()
export class ParkingLotService {
    private dataUrl = 'https://7j62gv0mfg.execute-api.eu-west-1.amazonaws.com/hacking/place/';

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
            .then(response => {
                console.log(response.json());
                let data = response.json().body;
                data.sort(function (a, b) { return a.place_id - b.place_id; });
                data as ParkingPlace[]
                }
            )
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}
