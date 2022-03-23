import { TestBed } from '@angular/core/testing';

import { UserInfoResolver } from './user-info.resolver';

describe('UserInfoResolver', () => {
  let resolver: UserInfoResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(UserInfoResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});