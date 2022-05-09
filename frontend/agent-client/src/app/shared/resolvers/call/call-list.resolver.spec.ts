import { TestBed } from '@angular/core/testing';

import { CallListResolver } from './call-list.resolver';

describe('CallListResolver', () => {
  let resolver: CallListResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(CallListResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});