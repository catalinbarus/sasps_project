import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminGamesItemComponent } from './board-admin-games-item.component';

describe('BoardAdminGamesItemComponent', () => {
  let component: BoardAdminGamesItemComponent;
  let fixture: ComponentFixture<BoardAdminGamesItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminGamesItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminGamesItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
