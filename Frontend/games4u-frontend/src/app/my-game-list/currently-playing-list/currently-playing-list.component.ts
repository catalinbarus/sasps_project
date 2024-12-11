import { Component, OnInit, OnDestroy } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { MyGameListComponent } from '../my-game-list.component';
import { StorageService } from 'src/app/services/storage.service';
import { MyGameListEntry } from 'src/app/models/my-game-list-entry.model';

@Component({
  selector: 'app-currently-playing-list',
  templateUrl: './currently-playing-list.component.html',
  styleUrls: ['./currently-playing-list.component.css']
})
export class CurrentlyPlayingListComponent implements OnInit, OnDestroy {

  userOfGameList: User;
  myGameListEntries: MyGameListEntry[];

  constructor(
    private myGameList: MyGameListComponent,
    private storageService: StorageService

  ) {}

  ngOnInit(): void {
    this.myGameList.selectedAction = this.myGameList.actions[1];
    this.userOfGameList = this.myGameList.userOfGameList;

    this.storageService.fetchGameListEntriesForUser(this.userOfGameList.id, 1)
      .subscribe({
        next: (data) => {
          this.myGameListEntries = data;
        },
        error: (err) => {
          console.log(err);
        }
    });
  }

  ngOnDestroy(): void {
  }

}
