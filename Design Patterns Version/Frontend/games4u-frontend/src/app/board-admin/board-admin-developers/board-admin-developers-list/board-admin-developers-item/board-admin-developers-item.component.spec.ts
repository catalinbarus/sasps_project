import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminDevelopersItemComponent } from './board-admin-developers-item.component';

describe('BoardAdminDevelopersItemComponent', () => {
  let component: BoardAdminDevelopersItemComponent;
  let fixture: ComponentFixture<BoardAdminDevelopersItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminDevelopersItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminDevelopersItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
