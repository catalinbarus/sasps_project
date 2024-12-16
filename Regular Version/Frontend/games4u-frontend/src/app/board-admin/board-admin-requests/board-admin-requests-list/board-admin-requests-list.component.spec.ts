import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminRequestsListComponent } from './board-admin-requests-list.component';

describe('BoardAdminRequestsListComponent', () => {
  let component: BoardAdminRequestsListComponent;
  let fixture: ComponentFixture<BoardAdminRequestsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminRequestsListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminRequestsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
