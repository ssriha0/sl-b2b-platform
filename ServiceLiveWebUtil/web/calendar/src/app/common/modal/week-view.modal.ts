import { CustomCalendarEvent } from './calendar-event.modal';


export class WeekViewModal {
    events:Array<CustomCalendarEvent>;
    date: Date;
    dateString: string;
    dayOfWeek: number;
}