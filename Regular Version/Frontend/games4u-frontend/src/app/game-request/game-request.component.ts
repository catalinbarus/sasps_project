import { Component, OnInit, OnDestroy } from '@angular/core';
import { StorageService } from '../services/storage.service';
import { HttpClient } from '@angular/common/http';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-game-request',
  templateUrl: './game-request.component.html',
  styleUrls: ['./game-request.component.css']
})
export class GameRequestComponent implements OnInit, OnDestroy {

  currentUser: any;
  isLoggedIn: boolean;
  requestForm: FormGroup;

  constructor(
    private storageService: StorageService,
    private http: HttpClient,
    private toastr: ToastrService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {

    this.isLoggedIn = this.storageService.isLoggedIn();
    this.currentUser = this.storageService.getUser();

    this.initForm();
    
  }

  ngOnDestroy(): void {
    
  }

  initForm() {
    let gameName = '';
    let link = '';
    let details = '';
    
    this.requestForm = new FormGroup({
      name: new FormControl(gameName, Validators.required),
      link: new FormControl(link, Validators.required),
      details: new FormControl(details, Validators.required),
    });
  }

  onSubmit() {
    let newGameRequest = {
      "gameName" : this.requestForm.value['name'],
      "link" : this.requestForm.value['link'],
      "details" : this.requestForm.value['details'],
      "userId" : this.currentUser.id.toString()
    }

    this.toastr.success('A game request was made.', 'Success', {
      closeButton: true
    });
    this.router.navigate(['/home']);

    this.http.post<any>('http://localhost:8080/requests', newGameRequest).subscribe();
  }
}
