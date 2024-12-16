import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddOrEditDeveloperComponent } from './add-or-edit-developer.component';

describe('AddOrEditDeveloperComponent', () => {
  let component: AddOrEditDeveloperComponent;
  let fixture: ComponentFixture<AddOrEditDeveloperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddOrEditDeveloperComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddOrEditDeveloperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
