import { describe, it, expect } from '@jest/globals';
import { formatDate } from './utils';

describe('formatDate', () => {
  it('should format a valid date string', () => {
    const input = '2025-06-22T15:30:00';
    const result = formatDate(input);
    expect(result).toBe('06/22/2025 15:30');
  });
});