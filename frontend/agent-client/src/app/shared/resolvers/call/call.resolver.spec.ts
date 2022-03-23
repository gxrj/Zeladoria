import { TestBed } from '@angular/core/testing';

import { CallResolver } from './call.resolver';

describe('CallResolver', () => {
  let resolver: CallResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(CallResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});