import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { Review } from 'src/app/models/review.model';
import { User } from 'src/app/models/user.model';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-details-review-item',
  templateUrl: './details-review-item.component.html',
  styleUrls: ['./details-review-item.component.css']
})
export class DetailsReviewItemComponent implements OnInit, OnDestroy {

  @Input()
  review: Review;

  reviewer: User;

  constructor(
    private storageService: StorageService
  ) {}

  ngOnInit(): void {
    this.storageService.fetchUserById(this.review.user_id).subscribe(
      {
        next: (data) => {
          this.reviewer = data;
        },
        error: (err) => {
          console.log(err);
        }
      }
    );
  }

  ngOnDestroy(): void {
    
  }

  formatPicture(convertedData: string): string {
    return 'data:image/png;base64,' + convertedData;
  }

  formatDate(timestamp: string) {
    const months = [
      "January", "February", "March", "April", "May", "June",
      "July", "August", "September", "October", "November", "December"
    ];
    
    const parts = timestamp.split(" ");
    const dateParts = parts[0].split("-");
    const day = dateParts[0];
    const month = months[parseInt(dateParts[1], 10) - 1];
    const year = dateParts[2];
    
    return `${day} ${month}, ${year}`;
  }
}
