import { CustomCalendarEvent } from './calendar-event.modal';

export class Provider {
    id?: number;
    name: string;
    
    events?: Array<CustomCalendarEvent>;
    multipleEvents: Array<Array<any>>;

    firmId: number;
    
    personId?: number; // provider id
    
    type?: string
}