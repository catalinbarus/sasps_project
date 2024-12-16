import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminImagesItemComponent } from './board-admin-images-item.component';

describe('BoardAdminImagesItemComponent', () => {
  let component: BoardAdminImagesItemComponent;
  let fixture: ComponentFixture<BoardAdminImagesItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminImagesItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminImagesItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
