import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { GameDeveloper } from 'src/app/models/game-developer.model';
import { PaginatedDevelopers } from 'src/app/models/paginated-developers.model';
import { DeveloperService } from 'src/app/services/developer.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-board-admin-developers',
  templateUrl: './board-admin-developers.component.html',
  styleUrls: ['./board-admin-developers.component.css']
})
export class BoardAdminDevelopersComponent implements OnInit {

  paginatedDevelopers: PaginatedDevelopers;
  developers: GameDeveloper[] = [];
  subscription: Subscription = new Subscription;
  page: number = 1;
  count: number = 0;
  pageSize: number = 5;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private gameDeveloperService: DeveloperService,
    private storageService: StorageService) {}

  ngOnInit(): void {

    this.retrieveDevelopers();
    this.subscription = this.gameDeveloperService.developersChanged.subscribe(
      (developers: GameDeveloper[]) => {
        this.developers = developers;
      }
    );
    
  }

  onNewDeveloper() {
    this.router.navigate(['add_developer'], { relativeTo: this.route });
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

  retrieveDevelopers() {
    const params = this.getRequestParams(this.page, this.pageSize);

    this.storageService.fetchDevelopersPaginated(params)
      .subscribe({
        next: (data) => {
          console.log(data);
          this.paginatedDevelopers = data;
          
          this.developers = this.paginatedDevelopers.entities;
          this.gameDeveloperService.setDevelopers(this.developers);
          this.count = this.paginatedDevelopers.totalItems;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

}
