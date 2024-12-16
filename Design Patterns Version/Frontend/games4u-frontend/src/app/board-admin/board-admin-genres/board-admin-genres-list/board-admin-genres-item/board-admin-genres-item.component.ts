import { HttpClient } from '@angular/common/http';
import { Component, Input, Renderer2 } from '@angular/core';
import { Genre } from 'src/app/models/genre.model';
import { GenreService } from 'src/app/services/genre.service';

@Component({
  selector: 'app-board-admin-genres-item',
  templateUrl: './board-admin-genres-item.component.html',
  styleUrls: ['./board-admin-genres-item.component.css']
})
export class BoardAdminGenresItemComponent {

  @Input()
  genre: Genre;

  displayStyle = "none";

  status: string;

  errorMessage: string;

  constructor(
    private http: HttpClient, 
    private renderer: Renderer2,
    private genreService: GenreService
  ) {}

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

  onGenreDelete() {
    this.http.delete('http://localhost:8080/genres/' + this.genre.id)
        .subscribe({
            next: data => {
                this.status = 'Delete successful';
            },
            error: error => {
                this.errorMessage = error.message;
                console.error('There was an error!', error);
            }
        });
    this.genreService.deleteGenre(this.genre);
    this.closePopup();
  }

}
