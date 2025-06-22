import { createDefaultPreset } from 'ts-jest';

const { transform } = createDefaultPreset();

/** @type {import('jest').Config} */
export default {
  testEnvironment: 'node',
  transform,
  extensionsToTreatAsEsm: ['.ts'],
  preset: 'ts-jest/presets/default-esm', // Important for ESM + TypeScript
  globals: {
    'ts-jest': {
      useESM: true,
    },
  },
};