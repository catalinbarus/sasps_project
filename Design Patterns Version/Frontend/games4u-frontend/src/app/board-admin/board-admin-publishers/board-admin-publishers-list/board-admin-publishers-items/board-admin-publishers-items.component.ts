import { HttpClient } from '@angular/common/http';
import { Component, Input, Renderer2 } from '@angular/core';
import { GamePublisher } from 'src/app/models/game-publisher.model';
import { PublisherService } from 'src/app/services/publisher.service';

@Component({
  selector: 'app-board-admin-publishers-items',
  templateUrl: './board-admin-publishers-items.component.html',
  styleUrls: ['./board-admin-publishers-items.component.css']
})
export class BoardAdminPublishersItemsComponent {

  @Input()
  publisher: GamePublisher;

  status: string;

  errorMessage: string;
  displayStyle = "none";

  constructor(
    private http: HttpClient, 
    private renderer: Renderer2,
    private publisherService: PublisherService
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

  onPublisherDelete() {
    this.http.delete('http://localhost:8080/publishers/' + this.publisher.id)
        .subscribe({
            next: data => {
                this.status = 'Delete successful';
            },
            error: error => {
                this.errorMessage = error.message;
                console.error('There was an error!', error);
            }
        });
    this.publisherService.deletePublisher(this.publisher);
    this.closePopup();
  }

}
