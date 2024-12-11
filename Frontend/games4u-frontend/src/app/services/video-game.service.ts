import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { VideoGame } from '../models/video-game.model';
import { Image } from '../models/image.model';
import { Trailer } from '../models/trailer.model';
import { VideoGameLight } from '../models/video-game-light.model';

@Injectable({
    providedIn: 'root',
  })
export class VideoGameService {

    videoGamesChanged = new Subject<VideoGame[]>();
    videoGamesLightChanged = new Subject<VideoGameLight[]>();
    editableVideoGameChanged = new Subject<VideoGame>();
    searchedGameChanged = new Subject<string>;
    detailedVideoGameChanged = new Subject<VideoGame>();
    videoGameImagesChanged = new Subject<Image[]>();
    videoGameTrailersChanged = new Subject<Trailer[]>();
    

    constructor() {}

    private videoGames: VideoGame[] = [];
    private videoGamesLight: VideoGameLight[] = [];
    private editGame: VideoGame;
    private searchedGameName: string;
    private detailedVideoGame: VideoGame;
    private videoGameImages: Image[];
    private videoGameTrailers: Trailer[];

    setVideoGames(videoGames: VideoGame[]) {
        this.videoGames = videoGames;
        this.videoGamesChanged.next(this.videoGames.slice());
    }

    setVideoGamesLight(videoGames: VideoGameLight[]) {
        this.videoGamesLight = videoGames;
        this.videoGamesLightChanged.next(this.videoGamesLight.slice());
    }

    setEditableVideoGame(videoGame: VideoGame) {
        this.editGame = videoGame;
        this.editableVideoGameChanged.next(this.editGame);
    }

    setSearchedGame(gameName: string) {
        this.searchedGameName = gameName;
    }

    setDetailedVideoGame(videoGame: VideoGame) {
        this.detailedVideoGame = videoGame;
        this.detailedVideoGameChanged.next(this.detailedVideoGame);
    }

    setVideoGameImages(images: Image[]) {
        this.videoGameImages = images;
        this.videoGameImagesChanged.next(this.videoGameImages.slice());
    }

    setVideoGameTrailers(trailers: Trailer[]) {
        this.videoGameTrailers = trailers;
        this.videoGameTrailersChanged.next(this.videoGameTrailers.slice());
    }

    getVideoGames() {
        // Return a copy of the array, not a reference to the actual array
        return this.videoGames.slice();
    }

    getEditableVideoGame() {
        return this.editGame;
    }

    getSearchedGame() {
        return this.searchedGameName;
    }

    getDetailedGame() {
        return this.detailedVideoGame;
    }

    getVideoGameImages() {
        return this.videoGameImages;
    }

    getVideoGameTrailers() {
        return this.videoGameTrailers;
    }

    deleteVideoGame(game: VideoGame) {
        const deleteIdx = this.videoGames.indexOf(game);

        this.videoGames.splice(deleteIdx, 1);
        this.videoGamesChanged.next(this.videoGames.slice());
    }

    deleteVideoGameImage(image: Image) {
        const deleteIdx = this.videoGameImages.indexOf(image);

        this.videoGameImages.splice(deleteIdx, 1);
        this.videoGameImagesChanged.next(this.videoGameImages.slice());
    }

    changeGamesStatus() {
        this.videoGamesChanged.next(this.videoGames.slice());
    }

    changeEditGameStatus() {
        this.editableVideoGameChanged.next(this.editGame);
    }

    changeSearchedGameStatus() {
        this.searchedGameChanged.next(this.searchedGameName);
    }

    changeDetailedGameStatus() {
        this.detailedVideoGameChanged.next(this.detailedVideoGame);
    }

    changeVideoGameImagesStatus() {
        this.videoGameImagesChanged.next(this.videoGameImages);
    }

    changeVideoGameTrailersStatus() {
        this.videoGameTrailersChanged.next(this.videoGameTrailers);
    }
}