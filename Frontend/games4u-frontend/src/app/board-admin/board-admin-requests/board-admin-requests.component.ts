import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { GameRequest } from 'src/app/models/game-request.model';
import { PaginatedGameRequests } from 'src/app/models/paginated-game-request.model';
import { GameRequestService } from 'src/app/services/game-request.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-board-admin-requests',
  templateUrl: './board-admin-requests.component.html',
  styleUrls: ['./board-admin-requests.component.css']
})
export class BoardAdminRequestsComponent implements OnInit {

  paginatedRequests: PaginatedGameRequests;
  requests: GameRequest[];
  subscription: Subscription = new Subscription;
  page: number = 1;
  count: number = 0;
  pageSize: number = 4;

  constructor(
    private storageService: StorageService,
    private gameRequestService: GameRequestService
  ) {}

  ngOnInit(): void {
    
  }

  getRequestParams(page: number, pageSize: number): any {
    let params: any = {};

    if (page) {
      params[`page`] = page - 1;
    }

    if (pageSize) {
      params[`size`] = pageSize;
    }

    return params;
  }

  retrieveRequests() {
    const params = this.getRequestParams(this.page, this.pageSize);

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

}
