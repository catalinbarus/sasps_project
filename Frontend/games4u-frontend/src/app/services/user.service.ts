import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { User } from '../models/user.model';

const API_URL = 'http://localhost:8080/api/test/';

@Injectable({
  providedIn: 'root',
})
export class UserService {

  usersChanged = new Subject<User[]>();
  currentUserChanged = new Subject<User>();

  constructor(private http: HttpClient) {}

  private users: User[] = [];
  private currentUser: User;

  setUsers(users: User[]) {
      this.users = users;
      this.usersChanged.next(this.users.slice());
  }

  setCurrentUser(user: User) {
    this.currentUser = user;
    //this.currentUserChanged.next(this.currentUser);
}

  getUsers() {
      // Return a copy of the array, not a reference to the actual array
      return this.users.slice();
  }

  getCurrentUser() {
    return this.currentUser;
  }

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all', { responseType: 'text' });
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }
  
  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin', { responseType: 'text' });
  }

  deleteUser(user: User) {
    const deleteIdx = this.users.indexOf(user);

    this.users.splice(deleteIdx, 1);
    this.usersChanged.next(this.users.slice());
  }
}