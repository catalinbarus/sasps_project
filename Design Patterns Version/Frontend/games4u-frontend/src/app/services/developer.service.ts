import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { GameDeveloper } from '../models/game-developer.model';

@Injectable({
    providedIn: 'root',
  })
export class DeveloperService {

    developersChanged = new Subject<GameDeveloper[]>();

    constructor() {}

    private developers: GameDeveloper[] = [];

    setDevelopers(developers: GameDeveloper[]) {
        this.developers = developers;
        this.developersChanged.next(this.developers.slice());
    }

    getDevelopers() {
        // Return a copy of the array, not a reference to the actual array
        return this.developers.slice();
    }

    deleteDevelopers(developer: GameDeveloper) {
        const deleteIdx = this.developers.indexOf(developer);

        this.developers.splice(deleteIdx, 1);
        this.developersChanged.next(this.developers.slice());
    }

    changeDevelopersStatus() {
        this.developersChanged.next(this.developers.slice());
    }

}