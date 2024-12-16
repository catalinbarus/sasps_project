import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { GamePublisher } from '../models/game-publisher.model';

@Injectable({
    providedIn: 'root',
  })
export class PublisherService {

    publishersChanged = new Subject<GamePublisher[]>();

    constructor() {}

    private publishers: GamePublisher[] = [];

    setPublishers(publishers: GamePublisher[]) {
        this.publishers = publishers;
        this.publishersChanged.next(this.publishers.slice());
    }

    getPublishers() {
        // Return a copy of the array, not a reference to the actual array
        return this.publishers.slice();
    }

    deletePublisher(publisher: GamePublisher) {
        const deleteIdx = this.publishers.indexOf(publisher);

        this.publishers.splice(deleteIdx, 1);
        this.publishersChanged.next(this.publishers.slice());
    }

    changePublishersStatus() {
        this.publishersChanged.next(this.publishers.slice());
    }
}