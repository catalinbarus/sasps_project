import { Component, OnInit, OnDestroy } from '@angular/core';
import { MyGameListEntry } from 'src/app/models/my-game-list-entry.model';
import { User } from 'src/app/models/user.model';
import { MyGameListComponent } from '../my-game-list.component';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-plan-to-play-list',
  templateUrl: './plan-to-play-list.component.html',
  styleUrls: ['./plan-to-play-list.component.css']
})
export class PlanToPlayListComponent implements OnInit, OnDestroy {

  userOfGameList: User;
  myGameListEntries: MyGameListEntry[];

  constructor(
    private myGameList: MyGameListComponent,
    private storageService: StorageService

  ) {}

  ngOnInit(): void {
    this.myGameList.selectedAction = this.myGameList.actions[4];
    this.userOfGameList = this.myGameList.userOfGameList;

    this.storageService.fetchGameListEntriesForUser(this.userOfGameList.id, 4)
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
