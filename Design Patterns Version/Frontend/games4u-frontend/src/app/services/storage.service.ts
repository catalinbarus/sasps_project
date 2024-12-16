import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Genre } from '../models/genre.model';
import { map, tap } from 'rxjs/operators';
import { GenreService } from './genre.service';
import { User } from '../models/user.model';
import { VideoGame } from '../models/video-game.model';
import { VideoGameService } from './video-game.service';
import { GameDeveloper } from '../models/game-developer.model';
import { DeveloperService } from './developer.service';
import { GamePublisher } from '../models/game-publisher.model';
import { PublisherService } from './publisher.service';
import { UserService } from './user.service';
import { Observable } from 'rxjs';

const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  constructor(
    private http: HttpClient, 
    private genreService: GenreService, 
    private videoGameService: VideoGameService,
    private developerService: DeveloperService,
    private publisherService: PublisherService,
    private userService: UserService) {}

  clean(): void {
    window.localStorage.clear();
  }

  public saveUser(user: User): void {
    window.localStorage.removeItem(USER_KEY);
    window.localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): any {
    let userStr = window.localStorage.getItem(USER_KEY);

    if (userStr) {
      let user: User = new User(JSON.parse(userStr)["id"], JSON.parse(userStr)["username"], JSON.parse(userStr)["email"], JSON.parse(userStr)["roles"], JSON.parse(userStr)["profilePicture"], JSON.parse(userStr)["myGameListConfig"], JSON.parse(userStr)["myGameListEntries"]);
      return user;
    }

    return {};
  }

  public isLoggedIn(): boolean {
    const user = window.localStorage.getItem(USER_KEY);
    if (user) {
      return true;
    }

    return false;
  }

  public fetchGenres() {
    return this.http
    .get<Genre[]>('http://localhost:8080/genres')
    .pipe(
      map((genres) => {
        return genres.map((genre) => {
          return {
            ...genre,
          };
        });
      }),
      tap((genres) => {
        this.genreService.setGenres(genres);
      })
    );
  }

  public fetchGenresSearch(requestParam: string) {
    return this.http
    .get<Genre[]>('http://localhost:8080/genres?genreName=' + requestParam)
    .pipe(
      map((genres) => {
        return genres.map((genre) => {
          return {
            ...genre,
          };
        });
      }),
      tap((genres) => {
        this.genreService.setGenres(genres);
      })
    );
  }

  public fetchGenresPaginated(params: any): Observable<any> {
    return this.http.get<any>('http://localhost:8080/genres_paginated', { params });
  }

  public fetchVideoGames() {
    return this.http
    .get<VideoGame[]>('http://localhost:8080/games')
    .pipe(
      map((videoGames) => {
        return videoGames.map((videoGame) => {
          return {
            ...videoGame,
          };
        });
      }),
      tap((videoGames) => {
        this.videoGameService.setVideoGames(videoGames);
      })
    );
  }

  public fetchVideoGamesSearch(requestParam: string) {
    return this.http
    .get<VideoGame[]>('http://localhost:8080/games?gameName=' + requestParam)
    .pipe(
      map((videoGames) => {
        return videoGames.map((videoGame) => {
          return {
            ...videoGame,
          };
        });
      }),
      tap((videoGames) => {
        this.videoGameService.setVideoGames(videoGames);
      })
    );
  }

  public fetchVideoGamesPaginated(params: any): Observable<any> {
    return this.http.get<any>('http://localhost:8080/games', { params });
  }

  public fetchVideoGamesPaginatedLight(params: any): Observable<any> {
    return this.http.get<any>('http://localhost:8080/games_light', { params });
  }

  public fetchVideoGame(id: number): Observable<any> {
    return this.http
    .get<VideoGame>('http://localhost:8080/games/' + id)
    .pipe(
      map((videoGame) => {
        return {
          ...videoGame,
        };
      }),
      tap((videoGame) => {
        this.videoGameService.setDetailedVideoGame(videoGame);
      })
    );;
  }

  public fetchVideoGameById(id: number): Observable<any> {
    return this.http
    .get<VideoGame>('http://localhost:8080/games/' + id);
  }

  public fetchDevelopers() {
    return this.http
    .get<GameDeveloper[]>('http://localhost:8080/developers')
    .pipe(
      map((developers) => {
        return developers.map((developer) => {
          return {
            ...developer,
          };
        });
      }),
      tap((developers) => {
        this.developerService.setDevelopers(developers);
      })
    );
  }

  public fetchDevelopersSearch(requestParam: string) {
    return this.http
    .get<GameDeveloper[]>('http://localhost:8080/developers?developerName=' + requestParam)
    .pipe(
      map((developers) => {
        return developers.map((developer) => {
          return {
            ...developer,
          };
        });
      }),
      tap((developers) => {
        this.developerService.setDevelopers(developers);
      })
    );
  }

  public fetchDevelopersPaginated(params: any): Observable<any> {
    return this.http.get<any>('http://localhost:8080/developers_paginated', { params });
  }

  public fetchMostRecentReviews(params: any, gameId: number): Observable<any> {
    return this.http.get<any>('http://localhost:8080/reviews/games/' + gameId, { params });
  }

  public fetchUserScoreForGame(userId: number, gameId: number): Observable<any> {
    return this.http.get<any>('http://localhost:8080/scores/filter?user_id=' + userId + '&game_id=' + gameId);
  }

  public fetchUserReviewForGame(userId: number, gameId: number): Observable<any> {
    return this.http.get<any>('http://localhost:8080/reviews/filter?user_id=' + userId + '&game_id=' + gameId);
  }

  public fetchGameListEntriesForUser(userId: number, statusId: number): Observable<any> {
    return this.http.get<any>('http://localhost:8080/game_list_entries/filter?user_id=' + userId + '&status_id=' + statusId);
  }

  public fetchGameListEntryOfUser(userId: number, gameId: number): Observable<any> {
    return this.http.get<any>('http://localhost:8080/game_list_entries/filter_by_game?user_id=' + userId + '&game_id=' + gameId);
  }

  public fetchPublishers() {
    return this.http
    .get<GamePublisher[]>('http://localhost:8080/publishers')
    .pipe(
      map((publishers) => {
        return publishers.map((publisher) => {
          return {
            ...publisher,
          };
        });
      }),
      tap((publishers) => {
        this.publisherService.setPublishers(publishers);
      })
    );
  }

  public fetchPublishersSearch(requestParam: string) {
    return this.http
    .get<GamePublisher[]>('http://localhost:8080/publishers?publisherName=' + requestParam)
    .pipe(
      map((publishers) => {
        return publishers.map((publisher) => {
          return {
            ...publisher,
          };
        });
      }),
      tap((publishers) => {
        this.publisherService.setPublishers(publishers);
      })
    );
  }

  public fetchPublishersPaginated(params: any): Observable<any> {
    return this.http.get<any>('http://localhost:8080/publishers_paginated', { params });
  }

  public fetchUsers() {
    return this.http
    .get<User[]>('http://localhost:8080/users')
    .pipe(
      map((users) => {
        return users.map((user) => {
          return {
            ...user,
          };
        });
      }),
      tap((users) => {
        this.userService.setUsers(users);
      })
    );
  }

  public fetchUserById(id: number): Observable<any> {
    return this.http
    .get<User>('http://localhost:8080/users/' + id)
  };

  public fetchUsersSearch(requestParam: string) {
    return this.http
    .get<User[]>('http://localhost:8080/users?userName=' + requestParam)
    .pipe(
      map((users) => {
        return users.map((user) => {
          return {
            ...user,
          };
        });
      }),
      tap((users) => {
        this.userService.setUsers(users);
      })
    );
  }

  public fetchUsersPaginated(params: any): Observable<any> {
    return this.http.get<any>('http://localhost:8080/users_paginated', { params });
  }

  public fetchGameRequestsPaginated(params: any): Observable<any> {
    return this.http.get<any>('http://localhost:8080/requests', { params });
  }

  public fetchGameRecommendationsForUser(params: any, userId: number): Observable<any> {
    return this.http.get<any>('http://localhost:8080/games/recommendations/' + userId, { params });
  }

  public fetchMeanScoreForUser(userId: number): Observable<any> {
    return this.http.get<any>('http://localhost:8080/scores/mean/' + userId);
  }

  public fetchRecentGameUpdatesForUser(params: any, userId: number): Observable<any> {
    return this.http.get<any>('http://localhost:8080/game_list_entries/recent_updates?user_id=' + userId, { params });
  }

  public fetchRecentScoresForUser(userId: number): Observable<any> {
    return this.http.get<any>('http://localhost:8080/scores/recent_scores/' + userId);
  }

  public fetchRecentReviewsForUser(userId: number): Observable<any> {
    return this.http.get<any>('http://localhost:8080/reviews/users/' + userId);
  }
}