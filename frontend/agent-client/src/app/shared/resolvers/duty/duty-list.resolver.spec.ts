import { TestBed } from '@angular/core/testing';

import { DutyListResolver } from './duty-list.resolver';

describe('DutyListResolver', () => {
  let resolver: DutyListResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(DutyListResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});