import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { GameRequest } from 'src/app/models/game-request.model';
import { PaginatedGameRequests } from 'src/app/models/paginated-game-request.model';
import { GameRequestService } from 'src/app/services/game-request.service';
import { StorageService } from 'src/app/services/storage.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-board-admin-requests-list',
  templateUrl: './board-admin-requests-list.component.html',
  styleUrls: ['./board-admin-requests-list.component.css']
})
export class BoardAdminRequestsListComponent implements OnInit, OnDestroy {

  paginatedRequests: PaginatedGameRequests;
  requests: GameRequest[];
  subscription: Subscription = new Subscription;
  isLoggedIn: boolean = false;
  gameName: string;

  @Input()
  page: number;

  @Input()
  count: number;

  @Input()
  pageSize: number;

  constructor(private userService: UserService, 
    private gameRequestService: GameRequestService,
    private storageService: StorageService) { }

  ngOnInit(): void {

    this.retrieveRequests();
    this.subscription = this.gameRequestService.requestsChanged.subscribe(
      (requests: GameRequest[]) => {
        this.requests = requests;
      }
    );
  }

  ngOnDestroy(): void {
    
  }

  onSubmit() {
    this.page = 1;
    this.retrieveRequests();
  }

  getRequestParams(gameName: string, page: number, pageSize: number): any {
    let params: any = {};

    if (gameName) {
      params[`gameName`] = gameName;
    }

    if (page) {
      params[`page`] = page - 1;
    }

    if (pageSize) {
      params[`size`] = pageSize;
    }

    return params;
  }

  retrieveRequests() {
    const params = this.getRequestParams(this.gameName, this.page, this.pageSize);

    this.storageService.fetchGameRequestsPaginated(params)
      .subscribe({
        next: (data) => {
          console.log(data);
          this.paginatedRequests = data;
          
          this.requests = this.paginatedRequests.gameRequests;
          this.gameRequestService.setRequests(this.requests);
          this.count = this.paginatedRequests.totalItems;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

  handlePageChange(event: number): void {
    this.page = event;
    this.retrieveRequests();
  }

}
