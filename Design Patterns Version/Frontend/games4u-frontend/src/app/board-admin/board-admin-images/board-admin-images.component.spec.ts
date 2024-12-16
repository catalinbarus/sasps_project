import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminImagesComponent } from './board-admin-images.component';

describe('BoardAdminImagesComponent', () => {
  let component: BoardAdminImagesComponent;
  let fixture: ComponentFixture<BoardAdminImagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminImagesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminImagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
