import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminTrailersListComponent } from './board-admin-trailers-list.component';

describe('BoardAdminTrailersListComponent', () => {
  let component: BoardAdminTrailersListComponent;
  let fixture: ComponentFixture<BoardAdminTrailersListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminTrailersListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminTrailersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
