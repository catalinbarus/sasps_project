import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTrailersComponent } from './edit-trailers.component';

describe('EditTrailersComponent', () => {
  let component: EditTrailersComponent;
  let fixture: ComponentFixture<EditTrailersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditTrailersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditTrailersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
