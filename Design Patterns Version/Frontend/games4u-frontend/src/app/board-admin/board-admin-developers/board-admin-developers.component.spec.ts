import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAdminDevelopersComponent } from './board-admin-developers.component';

describe('BoardAdminDevelopersComponent', () => {
  let component: BoardAdminDevelopersComponent;
  let fixture: ComponentFixture<BoardAdminDevelopersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAdminDevelopersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardAdminDevelopersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
