import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { GamePublisher } from 'src/app/models/game-publisher.model';
import { PaginatedPublishers } from 'src/app/models/paginated-publishers.model';
import { PublisherService } from 'src/app/services/publisher.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-board-admin-publishers',
  templateUrl: './board-admin-publishers.component.html',
  styleUrls: ['./board-admin-publishers.component.css']
})
export class BoardAdminPublishersComponent implements OnInit {

  paginatedPublishers: PaginatedPublishers;
  publishers: GamePublisher[] = [];
  subscription: Subscription = new Subscription;
  page: number = 1;
  count: number = 0;
  pageSize: number = 5;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private gamePublisherService: PublisherService,
    private storageService: StorageService) {}

  ngOnInit(): void {

    this.retrievePublishers();
    this.subscription = this.gamePublisherService.publishersChanged.subscribe(
      (publishers: GamePublisher[]) => {
        this.publishers = publishers;
      }
    );
  }

  onNewPublisher() {
    this.router.navigate(['add_publisher'], { relativeTo: this.route });
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

  retrievePublishers() {
    const params = this.getRequestParams(this.page, this.pageSize);

    this.storageService.fetchPublishersPaginated(params)
      .subscribe({
        next: (data) => {
          console.log(data);
          this.paginatedPublishers = data;
          
          this.publishers = this.paginatedPublishers.entities;
          this.gamePublisherService.setPublishers(this.publishers);
          this.count = this.paginatedPublishers.totalItems;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

}
