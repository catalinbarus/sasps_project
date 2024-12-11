import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddOrEditGameComponent } from './add-or-edit-game.component';

describe('AddOrEditGameComponent', () => {
  let component: AddOrEditGameComponent;
  let fixture: ComponentFixture<AddOrEditGameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddOrEditGameComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddOrEditGameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
