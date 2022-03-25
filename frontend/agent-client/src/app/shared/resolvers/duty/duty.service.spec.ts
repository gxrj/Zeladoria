import { TestBed } from '@angular/core/testing';

import { DutyResolver } from './duty.resolver';

describe('DutyResolver', () => {
  let resolver: DutyResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(DutyResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});