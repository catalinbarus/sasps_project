import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { AppComponent } from 'src/app/app.component';
import { User } from 'src/app/models/user.model';
import { FileUploadService } from 'src/app/services/file-upload.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-profile-picture-upload',
  templateUrl: './profile-picture-upload.component.html',
  styleUrls: ['./profile-picture-upload.component.css']
})
export class ProfilePictureUploadComponent implements OnInit, OnDestroy {

  @Input()
  userId: number;

  userOfProfile: User;

  selectedFiles?: FileList;
  currentFile?: File;
  progress = 0;
  message = '';

  fileInfos?: Observable<any>;

  constructor(
    private uploadService: FileUploadService,
    private appComponent: AppComponent,
    private storageService: StorageService) {}

  ngOnInit(): void {
    this.fileInfos = this.uploadService.getFiles();
  }

  ngOnDestroy(): void {
    
  }

  selectFile(event: any): void {
    this.selectedFiles = event.target.files;
  }

  upload(): void {
    this.progress = 0;

    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);

      if (file) {
        this.currentFile = file;

        this.uploadService.uploadProfilePicture(this.currentFile, this.userId).subscribe({
          next: (event: any) => {
            if (event.type === HttpEventType.UploadProgress) {
              this.progress = Math.round((100 * event.loaded) / event.total);
            } else if (event instanceof HttpResponse) {
              this.message = event.body.message;
              this.fileInfos = this.uploadService.getFiles();

              setTimeout(() => { 
                this.storageService.fetchUserById(this.userId).subscribe(
                  {
                    next: (data) => {
                      this.userOfProfile = data;
                      this.appComponent.user.profilePicture.convertedData = this.userOfProfile.profilePicture.convertedData;
                      this.storageService.saveUser(this.appComponent.user);
                      window.location.reload();
                    },
                    error: (err) => {
                      console.log(err);
                    }
                  }
                );
               }, 1500);
            }
          },
          error: (err: any) => {
            console.log(err);
            this.progress = 0;

            if (err.error && err.error.message) {
              this.message = err.error.message;
            } else {
              this.message = 'Could not upload the file!';
            }

            this.currentFile = undefined;
          },
        });
      }
      this.selectedFiles = undefined;
    }
  }

}
