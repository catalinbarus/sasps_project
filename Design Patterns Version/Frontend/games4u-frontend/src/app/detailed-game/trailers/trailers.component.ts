import { Component, OnInit, OnDestroy } from '@angular/core';
import { Trailer } from 'src/app/models/trailer.model';
import { VideoGame } from 'src/app/models/video-game.model';
import { VideoGameService } from 'src/app/services/video-game.service';
import { DetailedGameComponent } from '../detailed-game.component';

@Component({
  selector: 'app-trailers',
  templateUrl: './trailers.component.html',
  styleUrls: ['./trailers.component.css']
})
export class TrailersComponent implements OnInit, OnDestroy {

  videoGame: VideoGame;
  trailers: Trailer[];

  constructor(
    private videoGameService: VideoGameService,
    private detailedGame: DetailedGameComponent
  ) {}

  ngOnInit(): void {
    this.detailedGame.selectedAction = this.detailedGame.actions[2];
    this.videoGame = this.videoGameService.getDetailedGame();
    this.trailers = this.videoGame.trailers;
  }

  ngOnDestroy(): void {
    
  }

}
