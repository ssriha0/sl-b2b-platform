import { ErrorResponse } from '../results-modal';
export class ProviderAddressValidation {
    error: ErrorResponse;
    result: Array<AddressValidation>;
}

export class AddressValidation {
    street_name: string;
    street_suffix: string;
    city_name: string;
    state_abbreviation: string;
    zipcode: string;
    plus4_code: string;
}