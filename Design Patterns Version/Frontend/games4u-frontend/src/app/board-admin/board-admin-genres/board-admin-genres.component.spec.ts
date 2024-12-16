import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminGenresComponent } from './board-admin-genres.component';

describe('BoardAdminGenresComponent', () => {
  let component: BoardAdminGenresComponent;
  let fixture: ComponentFixture<BoardAdminGenresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminGenresComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminGenresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
