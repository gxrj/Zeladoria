import { TestBed } from '@angular/core/testing';

import { CallHistoryResolver } from './call-history.resolver';

describe('CallHistoryResolver', () => {
  let service: CallHistoryResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CallHistoryResolver);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
