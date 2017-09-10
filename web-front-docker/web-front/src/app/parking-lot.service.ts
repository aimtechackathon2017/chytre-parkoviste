import { Injectable }    from '@angular/core';
import {Http, Headers, RequestOptions} from '@angular/http';
import {Observable} from "rxjs/Rx";
import 'rxjs/add/operator/toPromise';

import { ParkingPlace } from './parking-place';

@Injectable()
export class ParkingLotService {
    private dataUrl = 'https://pnx23otuvk.execute-api.eu-central-1.amazonaws.com/hacking/place/';

    constructor(private http: Http) { }

    getDataPeriodically(): Observable<ParkingPlace[]> {
        return Observable
            .interval(5000)
            .flatMap(() => {
                return this.getData()
            });
    }

    getData(): Promise<ParkingPlace[]> {
        return this.http.get(this.dataUrl)
            .toPromise()
            .then(response => {
                let data = response.json();
                data.sort((a: any, b: any) => { return a.place_id - b.place_id; });
                console.log(data);
                return data as ParkingPlace[]
                
                }
            )
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}
