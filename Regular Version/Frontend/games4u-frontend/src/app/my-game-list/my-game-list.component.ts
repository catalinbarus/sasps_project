import { Component, OnInit, OnDestroy, ElementRef, Renderer2 } from '@angular/core';
import { AppComponent } from '../app.component';
import { StorageService } from '../services/storage.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { User } from '../models/user.model';
import { GameDetailActionItem } from '../models/game-detail-action-item.model';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-my-game-list',
  templateUrl: './my-game-list.component.html',
  styleUrls: ['./my-game-list.component.css']
})
export class MyGameListComponent implements OnInit, OnDestroy {

  currentUser: any;
  userOfGameList: User;
  isLoggedIn: boolean;
  userId: number;
  displayStyle = "none";
  listOfLoggedUser: boolean = false;

  primaryColor: string;
  secondaryColor: string;

  actions: GameDetailActionItem[] = [
    {
      name: "All Games",
      routeParam: "all_games"
    },
    {
      name: "Now Playing",
      routeParam: "currently_playing"
    },
    {
      name: "Finished",
      routeParam: "finished"
    },
    {
      name: "Dropped",
      routeParam: "dropped"
    },
    {
      name: "Plan to Play",
      routeParam: "plan_to_play"
    }
  ];

  selectedAction: GameDetailActionItem = this.actions[0];

  constructor(
    private appComponent: AppComponent,
    private storageService: StorageService,
    private route: ActivatedRoute,
    private router: Router,
    private elementRef: ElementRef,
    private renderer: Renderer2,
    private http: HttpClient) {}

  ngOnInit(): void {

    if (this.appComponent.showNavbar == true) {
      this.appComponent.showNavbar = false;
    }
    
    this.isLoggedIn = this.storageService.isLoggedIn();
    this.currentUser = this.storageService.getUser();

    this.route.params.subscribe((params: Params) => {
      this.userId = +params['id'];

      this.storageService.fetchUserById(this.userId).subscribe(
        {
          next: (data) => {
            this.userOfGameList = data;

            if (this.userOfGameList.id == this.currentUser.id) {
              this.listOfLoggedUser = true;
            }
            
            this.elementRef.nativeElement.ownerDocument
            .body.style.backgroundColor = this.userOfGameList.myGameListConfig.backgroundColor;

            this.primaryColor = this.userOfGameList.myGameListConfig.backgroundColor;
            this.secondaryColor = this.userOfGameList.myGameListConfig.textColor;
          },
          error: (err) => {
            console.log(err);
          }
        }
      );
    });
  }

  ngOnDestroy(): void {
    if (this.appComponent.showNavbar == false) {
      this.appComponent.showNavbar = true;
    }
  }

  itemSelected(action: GameDetailActionItem) {
    this.selectedAction = action;
  }

  openPopup() {

    this.displayStyle = "block";
    this.renderer.addClass(document.body, 'blur');
    setTimeout(() => {
      this.renderer.addClass(document.querySelector('.modal'), 'in');
    }, 50);
  }
  
  closePopup() {
    this.displayStyle = "none";
    this.renderer.removeClass(document.querySelector('.modal'), 'in');
    setTimeout(() => {
      this.displayStyle = "none";
      this.renderer.removeClass(document.body, 'blur');
    }, 300);
  }

  public alterPrimaryColor(event: string, data: any): void {
    //console.log(event, data);
    this.primaryColor = data;
  }

  public alterSecondaryColor(event: string, data: any): void {
    //console.log(event, data);
    this.secondaryColor = data;
  }

  updateListTheme() {
    console.log(this.primaryColor);
    console.log(this.secondaryColor);

    let gameListConfig = {
      "backgroundColor" : this.primaryColor,
      "textColor" : this.secondaryColor
    };

    this.http.put<any>('http://localhost:8080/game_list_configs/' + this.userOfGameList.myGameListConfig.id, gameListConfig).subscribe();

    this.closePopup();
    window.location.reload();
  }
}
