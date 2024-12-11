import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminPublishersListComponent } from './board-admin-publishers-list.component';

describe('BoardAdminPublishersListComponent', () => {
  let component: BoardAdminPublishersListComponent;
  let fixture: ComponentFixture<BoardAdminPublishersListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminPublishersListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminPublishersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
