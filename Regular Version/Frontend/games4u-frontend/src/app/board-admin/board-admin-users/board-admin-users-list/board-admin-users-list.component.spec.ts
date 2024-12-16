import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminUsersListComponent } from './board-admin-users-list.component';

describe('BoardAdminUsersListComponent', () => {
  let component: BoardAdminUsersListComponent;
  let fixture: ComponentFixture<BoardAdminUsersListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminUsersListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminUsersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
