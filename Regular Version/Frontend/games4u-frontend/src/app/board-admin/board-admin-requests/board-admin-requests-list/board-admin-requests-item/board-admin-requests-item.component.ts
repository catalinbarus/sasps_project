import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit, Renderer2 } from '@angular/core';
import { GameRequest } from 'src/app/models/game-request.model';
import { User } from 'src/app/models/user.model';
import { GameRequestService } from 'src/app/services/game-request.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-board-admin-requests-item',
  templateUrl: './board-admin-requests-item.component.html',
  styleUrls: ['./board-admin-requests-item.component.css']
})
export class BoardAdminRequestsItemComponent implements OnInit {

  @Input()
  request: GameRequest;

  status: string;

  errorMessage: string;

  showButtons: boolean;

  userOfRequest: User;

  displayStyle = "none";
  displayStyleApprove = "none";

  constructor(
    private storageService: StorageService,
    private renderer: Renderer2,
    private http: HttpClient,
    private gameRequestService: GameRequestService
  ) {}

  ngOnInit(): void {
    this.showButtons = false;
    this.storageService.fetchUserById(+this.request.userId).subscribe(
      {
        next: (data) => {
          this.userOfRequest = data;
        },
        error: (err) => {
          console.log(err);
        }
      }
    );    
  }

  formatPicture(convertedData: string): string {
    return 'data:image/png;base64,' + convertedData;
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

  openPopupApprove() {
    this.displayStyleApprove = "block";
    this.renderer.addClass(document.body, 'blur');
    setTimeout(() => {
      this.renderer.addClass(document.querySelector('.modal'), 'in');
    }, 50);
  }
  
  closePopupApprove() {
    this.displayStyleApprove = "none";
    this.renderer.removeClass(document.querySelector('.modal'), 'in');
    setTimeout(() => {
      this.displayStyleApprove = "none";
      this.renderer.removeClass(document.body, 'blur');
    }, 300);
  }

  onRequestApprove() {
    this.http.delete('http://localhost:8080/requests/' + this.request.id).subscribe();
    this.gameRequestService.deleteRequest(this.request);
    this.closePopupApprove();
  }
}
