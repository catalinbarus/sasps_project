import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminDevelopersListComponent } from './board-admin-developers-list.component';

describe('BoardAdminDevelopersListComponent', () => {
  let component: BoardAdminDevelopersListComponent;
  let fixture: ComponentFixture<BoardAdminDevelopersListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminDevelopersListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminDevelopersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
