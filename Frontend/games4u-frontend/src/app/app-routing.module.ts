import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { BoardModeratorComponent } from './board-moderator/board-moderator.component';
import { BoardUserComponent } from './board-user/board-user.component';
import { GamesComponent } from './games/games.component';
import { BoardActionStartComponent } from './board-admin/board-action-start/board-action-start.component';
import { BoardAdminGamesComponent } from './board-admin/board-admin-games/board-admin-games.component';
import { AddOrEditGameComponent } from './board-admin/board-admin-games/add-or-edit-game/add-or-edit-game.component';
import { BoardAdminUsersComponent } from './board-admin/board-admin-users/board-admin-users.component';
import { BoardAdminGenresComponent } from './board-admin/board-admin-genres/board-admin-genres.component';
import { BoardAdminDevelopersComponent } from './board-admin/board-admin-developers/board-admin-developers.component';
import { BoardAdminPublishersComponent } from './board-admin/board-admin-publishers/board-admin-publishers.component';
import { AddOrEditDeveloperComponent } from './board-admin/board-admin-developers/add-or-edit-developer/add-or-edit-developer.component';
import { AddOrEditPublisherComponent } from './board-admin/board-admin-publishers/add-or-edit-publisher/add-or-edit-publisher.component';
import { GamesSearchComponent } from './home/games-search/games-search.component';
import { DetailedGameComponent } from './detailed-game/detailed-game.component';
import { DetailsComponent } from './detailed-game/details/details.component';
import { ReviewsComponent } from './detailed-game/reviews/reviews.component';
import { TrailersComponent } from './detailed-game/trailers/trailers.component';
import { BoardAdminImagesComponent } from './board-admin/board-admin-images/board-admin-images.component';
import { EditImagesComponent } from './board-admin/board-admin-images/edit-images/edit-images.component';
import { BoardAdminTrailersComponent } from './board-admin/board-admin-trailers/board-admin-trailers.component';
import { MyGameListComponent } from './my-game-list/my-game-list.component';
import { AllGamesListComponent } from './my-game-list/all-games-list/all-games-list.component';
import { CurrentlyPlayingListComponent } from './my-game-list/currently-playing-list/currently-playing-list.component';
import { FinishedListComponent } from './my-game-list/finished-list/finished-list.component';
import { DroppedListComponent } from './my-game-list/dropped-list/dropped-list.component';
import { PlanToPlayListComponent } from './my-game-list/plan-to-play-list/plan-to-play-list.component';
import { GameRequestComponent } from './game-request/game-request.component';
import { AboutComponent } from './about/about.component';
import { EditTrailersComponent } from './board-admin/board-admin-trailers/edit-trailers/edit-trailers.component';
import { BoardAdminRequestsComponent } from './board-admin/board-admin-requests/board-admin-requests.component';
import { UsersSearchComponent } from './home/users-search/users-search.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile/:id', component: ProfileComponent },
  { path: 'games', component: GamesSearchComponent},
  { path: 'users', component: UsersSearchComponent},
  { 
    path: 'games/:id', 
    component: DetailedGameComponent, 
    children: [
      { path: '', component: DetailsComponent },
      { path: 'details', component: DetailsComponent },
      { path: 'reviews', component: ReviewsComponent },
      { path: 'trailers', component: TrailersComponent },

    ]},
  { path: 'top_games', component: GamesComponent},
  { path: 'request_game', component: GameRequestComponent},
  { 
    path: 'gamelist/:id', 
    component: MyGameListComponent,
    children: [
      { path: '', component: AllGamesListComponent},
      { path: 'all_games', component: AllGamesListComponent},
      { path: 'currently_playing', component: CurrentlyPlayingListComponent},
      { path: 'finished', component: FinishedListComponent},
      { path: 'dropped', component: DroppedListComponent},
      { path: 'plan_to_play', component: PlanToPlayListComponent},
    ]},
  { path: 'user', component: BoardUserComponent },
  { path: 'mod', component: BoardModeratorComponent },
  { 
    path: 'admin', 
    component: BoardAdminComponent,
    children: [
      { path: '', component: BoardActionStartComponent },
      { path: 'manage_video_games', component: BoardAdminGamesComponent},
      { path: 'manage_video_games/edit_video_game', component: AddOrEditGameComponent},
      { path: 'manage_video_games/edit_video_game/:id', component: AddOrEditGameComponent},
      { path: 'manage_users', component: BoardAdminUsersComponent },
      { path: 'manage_genres', component: BoardAdminGenresComponent},
      { path: 'manage_developers', component: BoardAdminDevelopersComponent},
      { path: 'manage_developers/add_developer', component: AddOrEditDeveloperComponent},
      { path: 'manage_publishers', component: BoardAdminPublishersComponent},
      { path: 'manage_publishers/add_publisher', component: AddOrEditPublisherComponent},
      { path: 'manage_images', component: BoardAdminImagesComponent},
      { path: 'manage_images/:id', component: EditImagesComponent},
      { path: 'manage_trailers', component: BoardAdminTrailersComponent},
      { path: 'manage_trailers/:id', component: EditTrailersComponent},
      { path: 'manage_requests', component: BoardAdminRequestsComponent}
    ]
  },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
