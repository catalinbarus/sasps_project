import { HttpClient } from '@angular/common/http';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { GameDeveloper } from 'src/app/models/game-developer.model';
import { GamePublisher } from 'src/app/models/game-publisher.model';
import { Genre } from 'src/app/models/genre.model';
import { VideoGame } from 'src/app/models/video-game.model';
import { DeveloperService } from 'src/app/services/developer.service';
import { GenreService } from 'src/app/services/genre.service';
import { PublisherService } from 'src/app/services/publisher.service';
import { StorageService } from 'src/app/services/storage.service';
import { VideoGameService } from 'src/app/services/video-game.service';

@Component({
  selector: 'app-add-or-edit-game',
  templateUrl: './add-or-edit-game.component.html',
  styleUrls: ['./add-or-edit-game.component.css']
})
export class AddOrEditGameComponent implements OnInit, OnDestroy {

  gameForm: FormGroup;
  changesSaved = false;
  trailerURL: string;

  developers: GameDeveloper[] = [];
  developersSubscription: Subscription = new Subscription;

  publishers: GamePublisher[] = [];
  publishersSubscription: Subscription = new Subscription;

  genres: Genre[] = [];
  genresSubscription: Subscription = new Subscription;

  isGameAdded: boolean = false;
  id: number;
  editMode: boolean = false;
  gameToEdit: VideoGame;
  editGameSubscription: Subscription = new Subscription;

  currentGameName: string = '';
  currentDeveloperId: number;
  currentPublisherId: number;
  currentReleaseDate: string = '';
  currentGenreId: number;
  currentRating: string = '';
  currentDescription: string = '';

  trailerMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private storageService: StorageService,
    private gameDeveloperService: DeveloperService,
    private gamePublisherService: PublisherService,
    private genreService: GenreService,
    private videoGameService: VideoGameService,
    private http: HttpClient) { }

  ngOnInit(): void {

    this.route.params.subscribe((params: Params) => {
      this.id = +params['id'];
      this.editMode = params['id'] != null;
    });

    this.gameToEdit = this.videoGameService.getEditableVideoGame();
    this.editGameSubscription = this.videoGameService.editableVideoGameChanged.subscribe(
      (game: VideoGame) => {
        this.gameToEdit = game;
      }
    );

    this.storageService.fetchDevelopers().subscribe();
    this.developers = this.gameDeveloperService.getDevelopers();
    this.developersSubscription = this.gameDeveloperService.developersChanged.subscribe(
      (developers: GameDeveloper[]) => {
        this.developers = developers;
      }
    );

    this.storageService.fetchPublishers().subscribe();
    this.publishers = this.gamePublisherService.getPublishers();
    this.publishersSubscription = this.gamePublisherService.publishersChanged.subscribe(
      (publishers: GamePublisher[]) => {
        this.publishers = publishers;
      }
    );

    this.storageService.fetchGenres().subscribe();
    this.genres = this.genreService.getGenres();
    this.genresSubscription = this.genreService.genresChanged.subscribe(
      (genres: Genre[]) => {
        this.genres = genres;
      }
    );

    this.initForm();
  }

  initForm() {
    let gameName = '';
    let developerId;
    let publisherId;
    let releaseDate = '';
    let genreId;
    let rating = '';
    let description = '';
    
    if (this.editMode) {
      gameName = this.gameToEdit.name;
      developerId = this.gameToEdit.gameDeveloper.id;
      publisherId = this.gameToEdit.gamePublisher.id;
      releaseDate = this.formatEditableGameDate(this.gameToEdit.releaseDate);
      genreId = this.gameToEdit.genre.id;
      rating = this.gameToEdit.rating;
      description = this.gameToEdit.description;

    }

    this.gameForm = new FormGroup({
      name: new FormControl(gameName, Validators.required),
      developer: new FormControl(developerId, Validators.required),
      publisher: new FormControl(publisherId, Validators.required),
      release: new FormControl(releaseDate, Validators.required),
      genre: new FormControl(genreId, Validators.required),
      rating: new FormControl(rating, Validators.required),
      description: new FormControl(description, Validators.required)
    });
  }

  onSubmit() {
    let videoGame = {
      "name" : this.gameForm.value['name'],
      "developerId" : this.gameForm.value['developer'],
      "publisherId" : this.gameForm.value['publisher'],
      "releaseDate" : this.formatDate(this.gameForm.value['release']),
      "genreId" : this.gameForm.value['genre'],
      "rating" : this.gameForm.value['rating'],
      "description" : this.gameForm.value['description']
    }

    if (this.editMode) {
      this.http.put<any>('http://localhost:8080/games/' + this.gameToEdit.id, videoGame).subscribe();

      this.router.navigate(['/admin/manage_video_games']);
    } else {
      this.http.post<any>('http://localhost:8080/games', videoGame).subscribe(
        {
          next: data => {
            console.log(data);
            this.isGameAdded = true
          },
          error: err => {
            this.isGameAdded = false;
          }
        }
      );

      this.router.navigate(['../'], { relativeTo: this.route });
    }

    this.videoGameService.changeGamesStatus();
  }

  onCancel() {
    if (!this.editMode) {
      this.router.navigate(['../'], { relativeTo: this.route });
    } else {
      this.router.navigate(['/admin/manage_video_games']);
    }
  }
  
  onSaveChanges() {
    this.changesSaved = true;
    setTimeout(() => {
      this.changesSaved = false;
    }, 2000);
  }

  onResetForm() {
    this.gameForm.reset();
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear().toString();
    return `${day}-${month}-${year}`;
  }

  formatEditableGameDate(inputDate: string): string {
    const [day, month, year] = inputDate.split('-');
    const formattedDate = new Date(`${year}-${month}-${day}`);
    return formattedDate.toISOString().substring(0, 10);
  }

  extractVideoId(url: string) {
    const regex = /[?&]v=([^?&]+)/;
    const match = url.match(regex);
    return match ? match[1] : null;
  }

  onTrailerSubmit() {
    console.log(this.extractVideoId(this.trailerURL));

    if (this.extractVideoId(this.trailerURL) != null) {

      let trailer = {
        "youtubeId" : this.extractVideoId(this.trailerURL),
        "gameId" : this.gameToEdit.id,
      }

      this.http.post<any>('http://localhost:8080/trailers', trailer).subscribe(
        {
          next: data => {
            this.trailerMessage = "Trailer uploaded succesfully!";
          },
          error: err => {
            this.trailerMessage = "Failed to upload trailer.";
          }
        }
      );
    }
  }
  

  ngOnDestroy(): void {
    //console.log(typeof(this.gameForm.value['genre']));
  }

}
