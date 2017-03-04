import { InMemoryDbService } from 'angular-in-memory-web-api';
export class InMemoryDataService implements InMemoryDbService {
    createDb() {
        let parkingPlaces = [
            {id: 1, occupied: true},
            {id: 2, occupied: false},
            {id: 3, occupied: false}
        ];
        return {parkingPlaces};
    }
}