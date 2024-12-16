import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminGamesListComponent } from './board-admin-games-list.component';

describe('BoardAdminGamesListComponent', () => {
  let component: BoardAdminGamesListComponent;
  let fixture: ComponentFixture<BoardAdminGamesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminGamesListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminGamesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
