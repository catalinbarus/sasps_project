import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminGamesComponent } from './board-admin-games.component';

describe('BoardAdminGamesComponent', () => {
  let component: BoardAdminGamesComponent;
  let fixture: ComponentFixture<BoardAdminGamesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminGamesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminGamesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
