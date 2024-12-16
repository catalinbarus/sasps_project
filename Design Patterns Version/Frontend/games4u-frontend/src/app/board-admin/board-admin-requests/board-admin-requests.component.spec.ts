import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminRequestsComponent } from './board-admin-requests.component';

describe('BoardAdminRequestsComponent', () => {
  let component: BoardAdminRequestsComponent;
  let fixture: ComponentFixture<BoardAdminRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminRequestsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
