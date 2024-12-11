import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminRequestsItemComponent } from './board-admin-requests-item.component';

describe('BoardAdminRequestsItemComponent', () => {
  let component: BoardAdminRequestsItemComponent;
  let fixture: ComponentFixture<BoardAdminRequestsItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminRequestsItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminRequestsItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
