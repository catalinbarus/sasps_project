import { Component, OnInit, OnDestroy } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { StorageService } from 'src/app/services/storage.service';
import { MyGameListComponent } from '../my-game-list.component';

@Component({
  selector: 'app-all-games-list',
  templateUrl: './all-games-list.component.html',
  styleUrls: ['./all-games-list.component.css']
})
export class AllGamesListComponent implements OnInit, OnDestroy {

  userOfGameList: User;

  constructor(
    private storageService: StorageService,
    private myGameList: MyGameListComponent
  ) {}

  ngOnInit(): void {

    this.userOfGameList = this.myGameList.userOfGameList;
    
  }

  ngOnDestroy(): void {
    console.log(this.userOfGameList);
  }
}
