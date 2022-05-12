import { TestBed } from '@angular/core/testing';

import { AttendanceListResolver } from './attendance-list.resolver';

describe('AttendanceListResolver', () => {
  let service: AttendanceListResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AttendanceListResolver);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});