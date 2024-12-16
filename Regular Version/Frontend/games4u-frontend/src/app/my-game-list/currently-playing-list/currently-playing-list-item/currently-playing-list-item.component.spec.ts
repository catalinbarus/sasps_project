import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrentlyPlayingListItemComponent } from './currently-playing-list-item.component';

describe('CurrentlyPlayingListItemComponent', () => {
  let component: CurrentlyPlayingListItemComponent;
  let fixture: ComponentFixture<CurrentlyPlayingListItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CurrentlyPlayingListItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CurrentlyPlayingListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
