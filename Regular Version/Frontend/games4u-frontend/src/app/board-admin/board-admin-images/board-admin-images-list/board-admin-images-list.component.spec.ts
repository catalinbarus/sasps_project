import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminImagesListComponent } from './board-admin-images-list.component';

describe('BoardAdminImagesListComponent', () => {
  let component: BoardAdminImagesListComponent;
  let fixture: ComponentFixture<BoardAdminImagesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminImagesListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminImagesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
