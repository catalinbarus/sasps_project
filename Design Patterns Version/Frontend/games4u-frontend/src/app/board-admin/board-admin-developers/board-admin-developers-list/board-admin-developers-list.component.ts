import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Subscription } from 'rxjs';
import { GameDeveloper } from 'src/app/models/game-developer.model';
import { PaginatedDevelopers } from 'src/app/models/paginated-developers.model';
import { DeveloperService } from 'src/app/services/developer.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-board-admin-developers-list',
  templateUrl: './board-admin-developers-list.component.html',
  styleUrls: ['./board-admin-developers-list.component.css']
})
export class BoardAdminDevelopersListComponent implements OnInit, OnDestroy {

  paginatedDevelopers: PaginatedDevelopers;
  developers: GameDeveloper[] = [];
  subscription: Subscription = new Subscription;
  developerName: string;

  @Input()
  page: number;

  @Input()
  count: number;

  @Input()
  pageSize: number;

  constructor(
    private developerService: DeveloperService, 
    private storageService: StorageService) { }

  ngOnInit(): void {
    this.retrieveDevelopers();
    this.subscription = this.developerService.developersChanged.subscribe(
      (developers: GameDeveloper[]) => {
        this.developers = developers;
      }
    );
  }

  ngOnDestroy(): void {
    
  }

  onSubmit() {
    this.page = 1;
    this.retrieveDevelopers();
  }

  getRequestParams(developerName: string, page: number, pageSize: number): any {
    let params: any = {};

    if (developerName) {
      params[`developerName`] = developerName;
    }

    if (page) {
      params[`page`] = page - 1;
    }

    if (pageSize) {
      params[`size`] = pageSize;
    }

    return params;
  }

  retrieveDevelopers() {
    const params = this.getRequestParams(this.developerName, this.page, this.pageSize);

    this.storageService.fetchDevelopersPaginated(params)
      .subscribe({
        next: (data) => {
          console.log(data);
          this.paginatedDevelopers = data;
          
          this.developers = this.paginatedDevelopers.entities;
          this.developerService.setDevelopers(this.developers);
          this.count = this.paginatedDevelopers.totalItems;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

  handlePageChange(event: number): void {
    this.page = event;
    this.retrieveDevelopers();
  }

  changeItemCount(val: number) {
    this.count = val;
  }

  changeCurrentPage(val: number) {
    this.page = val;
  }

}
