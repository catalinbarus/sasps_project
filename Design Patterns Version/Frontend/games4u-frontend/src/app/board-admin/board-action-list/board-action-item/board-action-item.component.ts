import { Component, Input, OnInit } from '@angular/core';
import { AdminActionItem } from 'src/app/models/admin-action-item.model';

@Component({
  selector: 'app-board-action-item',
  templateUrl: './board-action-item.component.html',
  styleUrls: ['./board-action-item.component.css']
})
export class BoardActionItemComponent implements OnInit {

  @Input()
  action: AdminActionItem;

  constructor() { }

  ngOnInit(): void {
  }

}
