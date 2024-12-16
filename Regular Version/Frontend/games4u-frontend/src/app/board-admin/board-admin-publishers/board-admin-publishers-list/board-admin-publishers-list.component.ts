import { Component, Input } from '@angular/core';
import { Subscription } from 'rxjs';
import { GamePublisher } from 'src/app/models/game-publisher.model';
import { PaginatedPublishers } from 'src/app/models/paginated-publishers.model';
import { PublisherService } from 'src/app/services/publisher.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-board-admin-publishers-list',
  templateUrl: './board-admin-publishers-list.component.html',
  styleUrls: ['./board-admin-publishers-list.component.css']
})
export class BoardAdminPublishersListComponent {

  paginatedPublishers: PaginatedPublishers;
  publishers: GamePublisher[] = [];
  subscription: Subscription = new Subscription;
  publisherName: string;

  @Input()
  page: number;

  @Input()
  count: number;

  @Input()
  pageSize: number;

  constructor(
    private publisherService: PublisherService, 
    private storageService: StorageService) { }

  ngOnInit(): void {
    this.retrievePublishers();
    this.subscription = this.publisherService.publishersChanged.subscribe(
      (publishers: GamePublisher[]) => {
        this.publishers = publishers;
      }
    );
  }

  ngOnDestroy(): void {
    
  }

  onSubmit() {
    this.page = 1;
    this.retrievePublishers();
  }

  getRequestParams(publisherName: string, page: number, pageSize: number): any {
    let params: any = {};

    if (publisherName) {
      params[`publisherName`] = publisherName;
    }

    if (page) {
      params[`page`] = page - 1;
    }

    if (pageSize) {
      params[`size`] = pageSize;
    }

    return params;
  }

  retrievePublishers() {
    const params = this.getRequestParams(this.publisherName, this.page, this.pageSize);

    this.storageService.fetchPublishersPaginated(params)
      .subscribe({
        next: (data) => {
          console.log(data);
          this.paginatedPublishers = data;
          
          this.publishers = this.paginatedPublishers.entities;
          this.publisherService.setPublishers(this.publishers);
          this.count = this.paginatedPublishers.totalItems;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

  handlePageChange(event: number): void {
    this.page = event;
    this.retrievePublishers();
  }
}
