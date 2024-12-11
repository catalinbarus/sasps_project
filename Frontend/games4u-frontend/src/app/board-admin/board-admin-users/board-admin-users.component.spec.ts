import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminUsersComponent } from './board-admin-users.component';

describe('BoardAdminUsersComponent', () => {
  let component: BoardAdminUsersComponent;
  let fixture: ComponentFixture<BoardAdminUsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminUsersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
