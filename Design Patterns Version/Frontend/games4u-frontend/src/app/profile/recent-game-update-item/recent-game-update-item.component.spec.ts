import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecentGameUpdateItemComponent } from './recent-game-update-item.component';

describe('RecentGameUpdateItemComponent', () => {
  let component: RecentGameUpdateItemComponent;
  let fixture: ComponentFixture<RecentGameUpdateItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecentGameUpdateItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecentGameUpdateItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
