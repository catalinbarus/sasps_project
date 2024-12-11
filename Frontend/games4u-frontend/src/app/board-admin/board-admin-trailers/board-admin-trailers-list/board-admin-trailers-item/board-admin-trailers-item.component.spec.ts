import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminTrailersItemComponent } from './board-admin-trailers-item.component';

describe('BoardAdminTrailersItemComponent', () => {
  let component: BoardAdminTrailersItemComponent;
  let fixture: ComponentFixture<BoardAdminTrailersItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminTrailersItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminTrailersItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
