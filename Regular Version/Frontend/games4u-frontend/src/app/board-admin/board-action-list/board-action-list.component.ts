import { Component, OnInit } from '@angular/core';
import { AdminActionItem } from 'src/app/models/admin-action-item.model';

@Component({
  selector: 'app-board-action-list',
  templateUrl: './board-action-list.component.html',
  styleUrls: ['./board-action-list.component.css']
})
export class BoardActionListComponent implements OnInit {

  actions: AdminActionItem[] = [
    {
      name: "Users",
      description: "Manage current users.",
      imagePath: "../../../assets/user.png",
      routeParam: "manage_users"
    },
    {
      name: "Games",
      description: "View all current video games in the database.",
      imagePath: "../../../assets/video_game_logo.png",
      routeParam: "manage_video_games"
    },
    {
      name: "Genres",
      description: "Manage current genres.",
      imagePath: "../../../assets/genre.png",
      routeParam: "manage_genres"
    },
    {
      name: "Developers",
      description: "Manage current developer companies.",
      imagePath: "../../../assets/developer.png",
      routeParam: "manage_developers"
    },
    {
      name: "Publishers",
      description: "Manage current publishers.",
      imagePath: "../../../assets/publisher.png",
      routeParam: "manage_publishers"
    },
    {
      name: "Images",
      description: "Manage current images.",
      imagePath: "../../../assets/image.png",
      routeParam: "manage_images"
    },
    {
      name: "Trailers",
      description: "Manage current trailers.",
      imagePath: "../../../assets/trailer.png",
      routeParam: "manage_trailers"
    },
    {
      name: "Requests",
      description: "Manage current game requests.",
      imagePath: "../../../assets/request.png",
      routeParam: "manage_requests"
    }
  ];

  constructor() { }

  ngOnInit(): void {
  }

}
