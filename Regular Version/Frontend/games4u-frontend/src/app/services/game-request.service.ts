import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Genre } from '../models/genre.model';
import { GameRequest } from '../models/game-request.model';

@Injectable({
    providedIn: 'root',
  })
export class GameRequestService {

    requestsChanged = new Subject<GameRequest[]>();

    constructor() {}

    private requests: GameRequest[] = [];

    setRequests(requests: GameRequest[]) {
        this.requests = requests;
        this.requestsChanged.next(this.requests.slice());
    }

    getRequests() {
        // Return a copy of the array, not a reference to the actual array
        return this.requests.slice();
    }

    changeRequestsStatus() {
        this.requestsChanged.next(this.requests.slice());
    }

    deleteRequest(request: GameRequest) {
        const deleteIdx = this.requests.indexOf(request);

        this.requests.splice(deleteIdx, 1);
        this.requestsChanged.next(this.requests.slice());
    }
}