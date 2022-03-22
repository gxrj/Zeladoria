import { TestBed } from '@angular/core/testing';

import { DistrictResolver } from './district.resolver';

describe('DistrictResolver', () => {
  let resolver: DistrictResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(DistrictResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});