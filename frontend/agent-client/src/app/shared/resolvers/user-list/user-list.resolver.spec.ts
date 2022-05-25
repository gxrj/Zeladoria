import { TestBed } from '@angular/core/testing';

import { UserListResolver } from './user-list.resolver';

describe('UserListResolver', () => {
  let service: UserListResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserListResolver);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});