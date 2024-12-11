import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminPublishersComponent } from './board-admin-publishers.component';

describe('BoardAdminPublishersComponent', () => {
  let component: BoardAdminPublishersComponent;
  let fixture: ComponentFixture<BoardAdminPublishersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminPublishersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminPublishersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
