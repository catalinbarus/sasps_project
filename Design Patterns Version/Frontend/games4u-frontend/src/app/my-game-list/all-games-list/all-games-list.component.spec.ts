import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllGamesListComponent } from './all-games-list.component';

describe('AllGamesListComponent', () => {
  let component: AllGamesListComponent;
  let fixture: ComponentFixture<AllGamesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllGamesListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllGamesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
