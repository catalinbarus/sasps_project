import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminGenresItemComponent } from './board-admin-genres-item.component';

describe('BoardAdminGenresItemComponent', () => {
  let component: BoardAdminGenresItemComponent;
  let fixture: ComponentFixture<BoardAdminGenresItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminGenresItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminGenresItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
