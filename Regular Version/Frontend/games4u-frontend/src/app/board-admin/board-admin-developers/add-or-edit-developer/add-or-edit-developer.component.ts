import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DeveloperService } from 'src/app/services/developer.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-add-or-edit-developer',
  templateUrl: './add-or-edit-developer.component.html',
  styleUrls: ['./add-or-edit-developer.component.css']
})
export class AddOrEditDeveloperComponent implements OnInit {

  developerForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private storageService: StorageService,
    private gameDeveloperService: DeveloperService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    let developerName = '';
    let establishDate = '';

    this.developerForm = new FormGroup({
      name: new FormControl(developerName, Validators.required),
      establish: new FormControl(establishDate, Validators.required),
    });
  }

  onSubmit() {
    let newDeveloper = {
      "name" : this.developerForm.value['name'],
      "establishDate" : this.formatDate(this.developerForm.value['establish'])
    }

    this.http.post<any>('http://localhost:8080/developers', newDeveloper).subscribe(
      {
        next: data => {
          console.log(data);
        },
        error: err => {
          console.log("Error at adding new developer.")
        }
      }
    );
    
    this.gameDeveloperService.changeDevelopersStatus();
    this.router.navigate(['../'], { relativeTo: this.route });


  }

  onCancel() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }

  onResetForm() {
    this.developerForm.reset();
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear().toString();
    return `${day}-${month}-${year}`;
  }

}
