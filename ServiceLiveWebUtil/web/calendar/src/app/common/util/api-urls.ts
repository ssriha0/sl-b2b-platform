const baseUrl: string = '/calendarAction_';
const marketFrontend: string = "MarketFrontend";
export const getEvents: string = marketFrontend + baseUrl + 'getVendorEvents.action';
export const addEvent: string = marketFrontend + baseUrl + 'addNewEvent.action';
export const createEvent: string = baseUrl + 'createEvent.action';
export const acceptSO: string = marketFrontend + baseUrl + 'acceptSO.action';
export const rejectSO: string = marketFrontend + baseUrl + 'rejectSO.action';
export const releaseSO: string = baseUrl + 'releaseSO.action';
export const custRescheduleSO: string = baseUrl + 'custRescheduleSO.action';
export const providerRescheduleSO: string = baseUrl + 'providerRescheduleSO.action';
export const cancellationSO: string = baseUrl + 'cancellationSO.action';
export const assignProvider: string = baseUrl + 'assignProvider.action';

export const actionRedirectPage: string = marketFrontend + '/soDetailsController.action';