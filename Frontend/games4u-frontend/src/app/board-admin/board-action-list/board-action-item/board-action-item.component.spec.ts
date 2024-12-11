import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardActionItemComponent } from './board-action-item.component';

describe('BoardActionItemComponent', () => {
  let component: BoardActionItemComponent;
  let fixture: ComponentFixture<BoardActionItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardActionItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardActionItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
