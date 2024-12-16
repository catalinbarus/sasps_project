import { Component, OnInit, OnDestroy } from '@angular/core';
import { MyGameListEntry } from 'src/app/models/my-game-list-entry.model';
import { User } from 'src/app/models/user.model';
import { MyGameListComponent } from '../my-game-list.component';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-dropped-list',
  templateUrl: './dropped-list.component.html',
  styleUrls: ['./dropped-list.component.css']
})
export class DroppedListComponent implements OnInit, OnDestroy {

  userOfGameList: User;
  myGameListEntries: MyGameListEntry[];

  constructor(
    private myGameList: MyGameListComponent,
    private storageService: StorageService

  ) {}

  ngOnInit(): void {
    this.myGameList.selectedAction = this.myGameList.actions[3];
    this.userOfGameList = this.myGameList.userOfGameList;

    this.storageService.fetchGameListEntriesForUser(this.userOfGameList.id, 3)
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
