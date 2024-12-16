import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardActionListComponent } from './board-action-list.component';

describe('BoardActionListComponent', () => {
  let component: BoardActionListComponent;
  let fixture: ComponentFixture<BoardActionListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardActionListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardActionListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
