import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { BoardModeratorComponent } from './board-moderator/board-moderator.component';
import { BoardUserComponent } from './board-user/board-user.component';

import { httpInterceptorProviders } from './helpers/http.interceptor';
import { RouterModule } from '@angular/router';
import { GamesComponent } from './games/games.component';
import { BoardActionListComponent } from './board-admin/board-action-list/board-action-list.component';
import { BoardActionStartComponent } from './board-admin/board-action-start/board-action-start.component';
import { BoardActionItemComponent } from './board-admin/board-action-list/board-action-item/board-action-item.component';
import { BoardAdminGamesComponent } from './board-admin/board-admin-games/board-admin-games.component';
import { BoardAdminGamesListComponent } from './board-admin/board-admin-games/board-admin-games-list/board-admin-games-list.component';
import { BoardAdminGamesItemComponent } from './board-admin/board-admin-games/board-admin-games-list/board-admin-games-item/board-admin-games-item.component';
import { AddOrEditGameComponent } from './board-admin/board-admin-games/add-or-edit-game/add-or-edit-game.component';
import { CustomDateFormatPipe } from './shared/custom-date-format.pipe';
import { BoardAdminUsersComponent } from './board-admin/board-admin-users/board-admin-users.component';
import { BoardAdminUsersListComponent } from './board-admin/board-admin-users/board-admin-users-list/board-admin-users-list.component';
import { BoardAdminUsersItemComponent } from './board-admin/board-admin-users/board-admin-users-list/board-admin-users-item/board-admin-users-item.component';
import { YouTubePlayerModule } from '@angular/youtube-player';
import { BoardAdminGenresComponent } from './board-admin/board-admin-genres/board-admin-genres.component';
import { BoardAdminDevelopersComponent } from './board-admin/board-admin-developers/board-admin-developers.component';
import { BoardAdminPublishersComponent } from './board-admin/board-admin-publishers/board-admin-publishers.component';
import { BoardAdminGenresListComponent } from './board-admin/board-admin-genres/board-admin-genres-list/board-admin-genres-list.component';
import { BoardAdminGenresItemComponent } from './board-admin/board-admin-genres/board-admin-genres-list/board-admin-genres-item/board-admin-genres-item.component';
import { BoardAdminDevelopersListComponent } from './board-admin/board-admin-developers/board-admin-developers-list/board-admin-developers-list.component';
import { BoardAdminDevelopersItemComponent } from './board-admin/board-admin-developers/board-admin-developers-list/board-admin-developers-item/board-admin-developers-item.component';
import { AddOrEditDeveloperComponent } from './board-admin/board-admin-developers/add-or-edit-developer/add-or-edit-developer.component';
import { BoardAdminPublishersListComponent } from './board-admin/board-admin-publishers/board-admin-publishers-list/board-admin-publishers-list.component';
import { BoardAdminPublishersItemsComponent } from './board-admin/board-admin-publishers/board-admin-publishers-list/board-admin-publishers-items/board-admin-publishers-items.component';
import { AddOrEditPublisherComponent } from './board-admin/board-admin-publishers/add-or-edit-publisher/add-or-edit-publisher.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { FileUploadComponent } from './board-admin/board-admin-games/add-or-edit-game/file-upload/file-upload.component';
import { FileUploadGenericComponent } from './board-admin/board-admin-games/add-or-edit-game/file-upload-generic/file-upload-generic.component';
import { DropdownDirective } from './shared/dropdown.directive';
import { GamesSearchComponent } from './home/games-search/games-search.component';
import { GameItemComponent } from './home/games-search/game-item/game-item.component';
import { DetailedGameComponent } from './detailed-game/detailed-game.component';
import { DetailsComponent } from './detailed-game/details/details.component';
import { ReviewsComponent } from './detailed-game/reviews/reviews.component';
import { CommonModule } from '@angular/common';
import { TrailersComponent } from './detailed-game/trailers/trailers.component';
import { DetailsReviewItemComponent } from './detailed-game/details/details-review-item/details-review-item.component';
import { BoardAdminImagesComponent } from './board-admin/board-admin-images/board-admin-images.component';
import { BoardAdminImagesListComponent } from './board-admin/board-admin-images/board-admin-images-list/board-admin-images-list.component';
import { BoardAdminImagesItemComponent } from './board-admin/board-admin-images/board-admin-images-list/board-admin-images-item/board-admin-images-item.component';
import { EditImagesComponent } from './board-admin/board-admin-images/edit-images/edit-images.component';
import { BoardAdminTrailersComponent } from './board-admin/board-admin-trailers/board-admin-trailers.component';
import { MyGameListComponent } from './my-game-list/my-game-list.component';
import { AllGamesListComponent } from './my-game-list/all-games-list/all-games-list.component';
import { CurrentlyPlayingListComponent } from './my-game-list/currently-playing-list/currently-playing-list.component';
import { FinishedListComponent } from './my-game-list/finished-list/finished-list.component';
import { DroppedListComponent } from './my-game-list/dropped-list/dropped-list.component';
import { PlanToPlayListComponent } from './my-game-list/plan-to-play-list/plan-to-play-list.component';
import { AllGameListItemComponent } from './my-game-list/all-games-list/all-game-list-item/all-game-list-item.component';
import { ColorPickerModule } from 'ngx-color-picker';
import { CurrentlyPlayingListItemComponent } from './my-game-list/currently-playing-list/currently-playing-list-item/currently-playing-list-item.component';
import { GameRequestComponent } from './game-request/game-request.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { AboutComponent } from './about/about.component';
import { BoardAdminTrailersListComponent } from './board-admin/board-admin-trailers/board-admin-trailers-list/board-admin-trailers-list.component';
import { BoardAdminTrailersItemComponent } from './board-admin/board-admin-trailers/board-admin-trailers-list/board-admin-trailers-item/board-admin-trailers-item.component';
import { EditTrailersComponent } from './board-admin/board-admin-trailers/edit-trailers/edit-trailers.component';
import { BoardAdminRequestsComponent } from './board-admin/board-admin-requests/board-admin-requests.component';
import { BoardAdminRequestsListComponent } from './board-admin/board-admin-requests/board-admin-requests-list/board-admin-requests-list.component';
import { BoardAdminRequestsItemComponent } from './board-admin/board-admin-requests/board-admin-requests-list/board-admin-requests-item/board-admin-requests-item.component';
import { RecentGameUpdateItemComponent } from './profile/recent-game-update-item/recent-game-update-item.component';
import { RecentScoreItemComponent } from './profile/recent-score-item/recent-score-item.component';
import { RecentReviewItemComponent } from './profile/recent-review-item/recent-review-item.component';
import { ProfilePictureUploadComponent } from './profile/profile-picture-upload/profile-picture-upload.component';
import { UsersSearchComponent } from './home/users-search/users-search.component';
import { UserItemComponent } from './home/users-search/user-item/user-item.component';





@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardModeratorComponent,
    BoardUserComponent,
    GamesComponent,
    BoardActionListComponent,
    BoardActionStartComponent,
    BoardActionItemComponent,
    BoardAdminGamesComponent,
    BoardAdminGamesListComponent,
    BoardAdminGamesItemComponent,
    AddOrEditGameComponent,
    CustomDateFormatPipe,
    BoardAdminUsersComponent,
    BoardAdminUsersListComponent,
    BoardAdminUsersItemComponent,
    BoardAdminGenresComponent,
    BoardAdminDevelopersComponent,
    BoardAdminPublishersComponent,
    BoardAdminGenresListComponent,
    BoardAdminGenresItemComponent,
    BoardAdminDevelopersListComponent,
    BoardAdminDevelopersItemComponent,
    AddOrEditDeveloperComponent,
    BoardAdminPublishersListComponent,
    BoardAdminPublishersItemsComponent,
    AddOrEditPublisherComponent,
    FileUploadComponent,
    FileUploadGenericComponent,
    DropdownDirective,
    GamesSearchComponent,
    GameItemComponent,
    DetailedGameComponent,
    DetailsComponent,
    ReviewsComponent,
    TrailersComponent,
    DetailsReviewItemComponent,
    BoardAdminImagesComponent,
    BoardAdminImagesListComponent,
    BoardAdminImagesItemComponent,
    EditImagesComponent,
    BoardAdminTrailersComponent,
    MyGameListComponent,
    AllGamesListComponent,
    CurrentlyPlayingListComponent,
    FinishedListComponent,
    DroppedListComponent,
    PlanToPlayListComponent,
    AllGameListItemComponent,
    CurrentlyPlayingListItemComponent,
    GameRequestComponent,
    AboutComponent,
    BoardAdminTrailersListComponent,
    BoardAdminTrailersItemComponent,
    EditTrailersComponent,
    BoardAdminRequestsComponent,
    BoardAdminRequestsListComponent,
    BoardAdminRequestsItemComponent,
    RecentGameUpdateItemComponent,
    RecentScoreItemComponent,
    RecentReviewItemComponent,
    ProfilePictureUploadComponent,
    UsersSearchComponent,
    UserItemComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,
    YouTubePlayerModule,
    NgxPaginationModule,
    CommonModule,
    ColorPickerModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  exports: [RouterModule],

  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
