import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminUsersItemComponent } from './board-admin-users-item.component';

describe('BoardAdminUsersItemComponent', () => {
  let component: BoardAdminUsersItemComponent;
  let fixture: ComponentFixture<BoardAdminUsersItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminUsersItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminUsersItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
