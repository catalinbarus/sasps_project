import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardActionStartComponent } from './board-action-start.component';

describe('BoardActionStartComponent', () => {
  let component: BoardActionStartComponent;
  let fixture: ComponentFixture<BoardActionStartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardActionStartComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardActionStartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
