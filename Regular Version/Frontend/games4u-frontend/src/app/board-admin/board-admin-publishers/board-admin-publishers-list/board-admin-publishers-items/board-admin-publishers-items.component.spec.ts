import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminPublishersItemsComponent } from './board-admin-publishers-items.component';

describe('BoardAdminPublishersItemsComponent', () => {
  let component: BoardAdminPublishersItemsComponent;
  let fixture: ComponentFixture<BoardAdminPublishersItemsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminPublishersItemsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminPublishersItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
