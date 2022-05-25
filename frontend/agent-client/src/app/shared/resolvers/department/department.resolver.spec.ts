import { TestBed } from '@angular/core/testing';

import { DepartmentResolver } from './department.resolver';

describe('DepartmentResolver', () => {
  let service: DepartmentResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DepartmentResolver);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});