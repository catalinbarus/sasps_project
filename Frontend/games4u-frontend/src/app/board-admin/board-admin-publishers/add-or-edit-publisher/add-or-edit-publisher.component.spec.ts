import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddOrEditPublisherComponent } from './add-or-edit-publisher.component';

describe('AddOrEditPublisherComponent', () => {
  let component: AddOrEditPublisherComponent;
  let fixture: ComponentFixture<AddOrEditPublisherComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddOrEditPublisherComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddOrEditPublisherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
