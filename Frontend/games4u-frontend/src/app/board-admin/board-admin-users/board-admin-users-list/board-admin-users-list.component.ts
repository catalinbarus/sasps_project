import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { PaginatedUsers } from 'src/app/models/paginated-users.model';
import { User } from 'src/app/models/user.model';
import { StorageService } from 'src/app/services/storage.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-board-admin-users-list',
  templateUrl: './board-admin-users-list.component.html',
  styleUrls: ['./board-admin-users-list.component.css']
})
export class BoardAdminUsersListComponent implements OnInit, OnDestroy {

  userName: string;
  paginatedUsers: PaginatedUsers;
  users: User[];
  subscription: Subscription = new Subscription;
  page: number = 1;
  count: number = 0;
  pageSize: number = 4;

  constructor(
    private userService: UserService, 
    private storageService: StorageService) { }

  ngOnInit(): void {

    this.retrieveUsers();
    this.subscription = this.userService.usersChanged.subscribe(
      (users: User[]) => {
        this.users = users;
      }
    );
  }

  ngOnDestroy(): void {
  }

  getRequestParams(userName: string, page: number, pageSize: number): any {
    let params: any = {};

    if (userName) {
      params[`userName`] = userName;
    }

    if (page) {
      params[`page`] = page - 1;
    }

    if (pageSize) {
      params[`size`] = pageSize;
    }

    return params;
  }

  retrieveUsers() {
    const params = this.getRequestParams(this.userName, this.page, this.pageSize);

    this.storageService.fetchUsersPaginated(params)
      .subscribe({
        next: (data) => {
          console.log(data);
          this.paginatedUsers = data;
          
          this.users = this.paginatedUsers.users;
          this.userService.setUsers(this.users);
          this.count = this.paginatedUsers.totalItems;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

  onSubmit() {
    this.page = 1;
    this.retrieveUsers();
  }

  handlePageChange(event: number): void {
    this.page = event;
    this.retrieveUsers();
  }

}
