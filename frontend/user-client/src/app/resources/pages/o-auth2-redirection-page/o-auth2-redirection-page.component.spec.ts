import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OAuth2RedirectionPageComponent } from './o-auth2-redirection-page.component';

describe('OAuth2RedirectionPageComponent', () => {
  let component: OAuth2RedirectionPageComponent;
  let fixture: ComponentFixture<OAuth2RedirectionPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OAuth2RedirectionPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OAuth2RedirectionPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
