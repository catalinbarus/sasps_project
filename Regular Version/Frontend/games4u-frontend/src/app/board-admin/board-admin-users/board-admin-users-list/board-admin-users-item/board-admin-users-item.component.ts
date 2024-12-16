import { HttpClient } from '@angular/common/http';
import { Component, OnInit, Input, Renderer2 } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-board-admin-users-item',
  templateUrl: './board-admin-users-item.component.html',
  styleUrls: ['./board-admin-users-item.component.css']
})
export class BoardAdminUsersItemComponent implements OnInit {

  @Input()
  user: User;

  displayStyle = "none";

  openDeleteModal: boolean = false;
  openBanModal: boolean = false;

  constructor(
    private http: HttpClient, 
    private renderer: Renderer2,
    private userService: UserService,) { }

  ngOnInit(): void {
  }

  formatPicture(convertedData: string): string {
    return 'data:image/png;base64,' + convertedData;
  }

  openDeletePopup() {
    this.openDeleteModal = true;
    this.displayStyle = "block";
    this.renderer.addClass(document.body, 'blur');
    setTimeout(() => {
      this.renderer.addClass(document.querySelector('.modal'), 'in');
    }, 50);
  }
  
  closeDeletePopup() {
    this.displayStyle = "none";
    this.renderer.removeClass(document.querySelector('.modal'), 'in');
    setTimeout(() => {
      this.displayStyle = "none";
      this.renderer.removeClass(document.body, 'blur');
    }, 300);
    this.openDeleteModal = false;
  }

  openBanPopup() {
    this.openBanModal = true;
    this.displayStyle = "block";
    this.renderer.addClass(document.body, 'blur');
    setTimeout(() => {
      this.renderer.addClass(document.querySelector('.modal'), 'in');
    }, 50);
  }

  closeBanPopup() {
    this.displayStyle = "none";
    this.renderer.removeClass(document.querySelector('.modal'), 'in');
    setTimeout(() => {
      this.displayStyle = "none";
      this.renderer.removeClass(document.body, 'blur');
    }, 300);
    this.openBanModal = false;
  }

  onUserDelete() {
    this.http.delete('http://localhost:8080/users/' + this.user.id).subscribe();
    this.userService.deleteUser(this.user);
    this.closeDeletePopup();
  }
}
