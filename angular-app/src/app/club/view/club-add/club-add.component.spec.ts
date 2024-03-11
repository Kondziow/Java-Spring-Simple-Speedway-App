import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClubAddComponent } from './club-add.component';

describe('ClubAddComponent', () => {
  let component: ClubAddComponent;
  let fixture: ComponentFixture<ClubAddComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClubAddComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ClubAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
