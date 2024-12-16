import { HttpClient } from '@angular/common/http';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PublisherService } from 'src/app/services/publisher.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-add-or-edit-publisher',
  templateUrl: './add-or-edit-publisher.component.html',
  styleUrls: ['./add-or-edit-publisher.component.css']
})
export class AddOrEditPublisherComponent implements OnInit, OnDestroy {

  publisherForm: FormGroup;
  
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private storageService: StorageService,
    private gamePublisherService: PublisherService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  ngOnDestroy(): void {
    
  }

  initForm() {
    let publisherName = '';
    let establishDate = '';

    this.publisherForm = new FormGroup({
      name: new FormControl(publisherName, Validators.required),
      establish: new FormControl(establishDate, Validators.required),
    });
  }

  onSubmit() {
    let newPublisher = {
      "name" : this.publisherForm.value['name'],
      "establishDate" : this.formatDate(this.publisherForm.value['establish'])
    }

    this.http.post<any>('http://localhost:8080/publishers', newPublisher).subscribe(
      {
        next: data => {
          console.log(data);
        },
        error: err => {
          console.log("Error at adding new publisher.")
        }
      }
    );
    
    this.gamePublisherService.changePublishersStatus();
    this.router.navigate(['../'], { relativeTo: this.route });
  }

  onCancel() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }

  onResetForm() {
    this.publisherForm.reset();
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear().toString();
    return `${day}-${month}-${year}`;
  }
}
