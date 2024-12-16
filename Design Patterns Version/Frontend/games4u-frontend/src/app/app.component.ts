import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { StorageService } from './services/storage.service';
import { AuthService } from './services/auth.service';
import { EventBusService } from './shared/event-bus.service';
import { Router } from '@angular/router';
import { User } from './models/user.model';
import { UserService } from './services/user.service';
import { VideoGameService } from './services/video-game.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username?: string;
  eventBusSub?: Subscription;
  user: any;
  gameName: string;
  showNavbar: boolean = true;

  constructor(
    private storageService: StorageService,
    private authService: AuthService,
    private userService: UserService,
    private eventBusService: EventBusService,
    private router: Router,
    private videoGameService: VideoGameService
  ) {}

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();
    console.log(this.isLoggedIn);
    console.log(window.localStorage);

    if (this.isLoggedIn) {
      this.user = this.storageService.getUser();
      this.roles = this.user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

      this.username = this.user.username;
    }

    this.eventBusSub = this.eventBusService.on("logout", () => {
      this.logout();
    });
  }

  logout(): void {

    this.authService.logout().subscribe({
      next: res => {
        console.log(res);
      },
      error: err => {
        console.log(err);
      }
    });

    this.storageService.clean();
    window.location.reload();
    this.router.navigateByUrl('/home');
  }

  formatPicture(convertedData: string): string {
    return 'data:image/png;base64,' + convertedData;
  }

  onGameSearch() {
    this.videoGameService.setSearchedGame(this.gameName);
    this.videoGameService.changeSearchedGameStatus();
    this.router.navigate(['games']);
  }
}