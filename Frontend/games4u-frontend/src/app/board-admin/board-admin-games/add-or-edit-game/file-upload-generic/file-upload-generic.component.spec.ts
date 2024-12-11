import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FileUploadGenericComponent } from './file-upload-generic.component';

describe('FileUploadGenericComponent', () => {
  let component: FileUploadGenericComponent;
  let fixture: ComponentFixture<FileUploadGenericComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FileUploadGenericComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FileUploadGenericComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
