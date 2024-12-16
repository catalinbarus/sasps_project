import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, Output, Renderer2 } from '@angular/core';
import { Router } from '@angular/router';
import { GameDeveloper } from 'src/app/models/game-developer.model';
import { DeveloperService } from 'src/app/services/developer.service';

@Component({
  selector: 'app-board-admin-developers-item',
  templateUrl: './board-admin-developers-item.component.html',
  styleUrls: ['./board-admin-developers-item.component.css']
})
export class BoardAdminDevelopersItemComponent {

  @Input()
  developer: GameDeveloper;

  @Input()
  count: number;

  @Input()
  page: number;

  status: string;

  errorMessage: string;
  displayStyle = "none";

  constructor(
    private http: HttpClient, 
    private renderer: Renderer2,
    private developerService: DeveloperService,
    private router: Router
  ) { }

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

  onDeveloperDelete() {
    this.http.delete('http://localhost:8080/developers/' + this.developer.id)
        .subscribe({
            next: data => {
                this.status = 'Delete successful';
            },
            error: error => {
                this.errorMessage = error.message;
                console.error('There was an error!', error);
            }
        });
    this.closePopup();
    this.setItemCount();
    this.setCurrentPage();
    this.developerService.deleteDevelopers(this.developer);
  }

  @Output() onDeleteCount = new EventEmitter<number>();
  setItemCount() {
    this.onDeleteCount.emit(this.count - 1);
  }

  @Output() onDeletePage = new EventEmitter<number>();
  setCurrentPage() {
    this.onDeletePage.emit(this.page - 1);
  }
}
