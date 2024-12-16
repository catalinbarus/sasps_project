import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminTrailersComponent } from './board-admin-trailers.component';

describe('BoardAdminTrailersComponent', () => {
  let component: BoardAdminTrailersComponent;
  let fixture: ComponentFixture<BoardAdminTrailersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminTrailersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminTrailersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
