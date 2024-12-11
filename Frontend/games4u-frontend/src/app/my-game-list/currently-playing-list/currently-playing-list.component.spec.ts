import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrentlyPlayingListComponent } from './currently-playing-list.component';

describe('CurrentlyPlayingListComponent', () => {
  let component: CurrentlyPlayingListComponent;
  let fixture: ComponentFixture<CurrentlyPlayingListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CurrentlyPlayingListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CurrentlyPlayingListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
