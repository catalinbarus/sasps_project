import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminGenresListComponent } from './board-admin-genres-list.component';

describe('BoardAdminGenresListComponent', () => {
  let component: BoardAdminGenresListComponent;
  let fixture: ComponentFixture<BoardAdminGenresListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminGenresListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminGenresListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
