import { Component, Input } from '@angular/core';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-user-item',
  templateUrl: './user-item.component.html',
  styleUrls: ['./user-item.component.css']
})
export class UserItemComponent {

  @Input()
  user: User;

  formatPicture(convertedData: string): string {
    return 'data:image/png;base64,' + convertedData;
  }
}
