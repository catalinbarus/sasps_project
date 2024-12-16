import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllGameListItemComponent } from './all-game-list-item.component';

describe('AllGameListItemComponent', () => {
  let component: AllGameListItemComponent;
  let fixture: ComponentFixture<AllGameListItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllGameListItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllGameListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
