// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: true,
  SURVEY_TYPES: {
    CSAT: 'CSAT',
    NPS: 'NPS',
    CSAT_NPS: 'CSAT_NPS',
    NPS_CSAT: 'NPS_CSAT'
  },
  AUTH: {
    KEY: 'abecb0bcad7c7516f5850dcb381a127694c5d1a74f3761de06a0c58420f8157e2',
    SECRET: 'fecb0bcad7c75rt16f5850dcb381a127694c5d1a74f3761de0'
  },
  API_URL: 'https://api-prod.servicelive.com/public/survey/'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
