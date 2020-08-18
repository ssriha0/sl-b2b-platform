import {
  Component, Input, OnInit, animate, ElementRef, EventEmitter, keyframes, OnChanges, NgZone,
  Output, Renderer, SimpleChange, state, style, transition, trigger
} from '@angular/core';
import { LoggerUtil } from '../../common/util/logger';
import { TimeFilter, EventDetailFilter, WeekdaysFilter } from '../../common/modal/result-filter.model';
import { FormControl, Validators } from '@angular/forms';
import { Calendar } from '../../common/util/calendar';
import { addHours, addMinutes, getHours, startOfDay, startOfWeek, endOfDay, endOfWeek, startOfMonth, endOfMonth } from 'date-fns';
import * as moment from 'moment';
import { Provider } from '../../common/modal/provider.modal';

import { CustomCalendarEvent } from '../../common/modal/calendar-event.modal';

declare var jQuery: any;

interface DateFormatFunction {
  (date: Date): string;
}

interface ValidationResult {
  [key: string]: boolean;
}

@Component({
  selector: 'calendar-header',
  templateUrl: 'calendar-header.component.html'
})

export class CalendarHeaderComponent implements OnInit {
  @Input()
  selectedDate?: Date = new Date();

  selectedDateRange1?: Date = new Date();
  selectedDateRange2?: Date = new Date();

  @Input()
  selectedCalendar: string = 'Today';

  @Input()
  jobCount?: number = 10;

  windowList: Array<string> = ['day-window', 'week-window', 'month-window'];
  viewList: Array<string> = ['company', 'technician'];

  selectedWindow: string = this.windowList[1];
  selectedView: string = this.viewList[0];
  selectedEvent: CustomCalendarEvent;
  selectedSlot: number;
  providerList: Array<Provider>;

  private readonly DEFAULT_FORMAT = 'YYYY-MM-DD';
  private readonly DEFAULT_START_TIME = '08:00';
  private readonly DEFAULT_END_TIME = '20:00';

  private dateVal: Date;
  // two way bindings
  @Output() dateChange = new EventEmitter<Date>();

  @Input() get date(): Date { return this.dateVal; };
  set date(val: Date) {
    this.dateVal = val;
    this.dateChange.emit(val);
  }
  // api bindings
  @Input() disabled: boolean;
  @Input() accentColor: string;
  @Input() altInputStyle: boolean;
  @Input() dateFormat: string | DateFormatFunction;
  @Input() fontFamily: string;
  @Input() rangeStart: Date;
  @Input() rangeEnd: Date;
  // data
  @Input() placeholder: string = 'Select a date';
  @Input() inputText: string;
  // view logic
  @Input() showCalendar: boolean;
  @Input() cancelText: string = 'Cancel';
  @Input() weekStart: number = 0;
  // events
  @Output() onSelect = new EventEmitter<Date>();
  // time
  @Input() calendarDays: Array<number>;
  @Input() currentMonth: string;
  @Input() dayNames: Array<String> = ['S', 'M', 'T', 'W', 'T', 'F', 'S']; // Default order: firstDayOfTheWeek = 0
  @Input() hoveredDay: Date;
  @Input() months: Array<string>;
  dayNamesOrdered: Array<String>;
  calendar: Calendar;
  currentMonthNumber: number;
  currentYear: number;
  // animation
  animate: string;
  // colors
  colors: { [id: string]: string };
  // listeners
  clickListener: Function;
  // forms
  yearControl: FormControl;

  weekDayNames: Array<any>;
  hideWeekDays: Array<number> = [];
  eventDetailFilter: EventDetailFilter;
  timeFilter: TimeFilter;

  showLoader: boolean= false;

  constructor(private _logger: LoggerUtil, private renderer: Renderer, private elementRef: ElementRef) {

    this.dateFormat = this.DEFAULT_FORMAT;
    // view logic
    this.showCalendar = false;
    // colors
    this.colors = {
      'black': '#333333',
      'blue': '#1285bf',
      'lightGrey': '#f1f1f1',
      'grey': '#4b494c',
      'white': '#ffffff',
      'orange': '#e67308'
    };
    this.accentColor = this.colors['orange'];
    this.altInputStyle = false;
    // time
    this.updateDayNames();

    this.months = [
      'January', 'February', 'March', 'April', 'May', 'June', 'July',
      'August', 'September', 'October', 'November', ' December'
    ];
    // listeners
    this.clickListener = renderer.listenGlobal(
      'document',
      'click',
      (event: MouseEvent) => this.handleGlobalClick(event)
    );
    // form controls
    this.yearControl = new FormControl('', Validators.compose([
      Validators.required,
      Validators.maxLength(4),
      this.yearValidator,
      this.inRangeValidator.bind(this)
    ]));
    this.weekDayNames = [{ name: 'S', color: '#e67308' },
    { name: 'M', color: this.colors['orange'] },
    { name: 'T', color: this.colors['orange'] },
    { name: 'W', color: this.colors['orange'] },
    { name: 'T', color: this.colors['orange'] },
    { name: 'F', color: this.colors['orange'] },
    { name: 'S', color: this.colors['orange'] }];
    this.eventDetailFilter = new EventDetailFilter();
    this.timeFilter = new TimeFilter();
    this.timeFilter.start = this.DEFAULT_START_TIME;
    this.timeFilter.end = this.DEFAULT_END_TIME;
  }

  ngOnInit() {
    this.updateDayNames();
    this.syncVisualsWithDate();
    this.onSelectDay(new Date());
    let pro : Provider = new Provider();
    pro.id = 5;
    pro.name = 'Malhar';
    //this.providerList = new Array<Provider>();
    //this.providerList.push(pro);
    //jQuery("#add-event-action").modal("show");
  }

  selectView(view: string) {
    this._logger.log('view changed ' + view);
    this.selectedView = view;
    if (!this.selectedWindow) {
      this.selectedWindow = this.windowList[0];
    }
  }

  selectWindow(window: string) {
    this._logger.log('view changed ' + window);
    this.selectedWindow = window;
    this.selectedCalendar = window;
    if (!this.selectedView) {
      this.selectedView = this.viewList[0];
    }
  }

  ngOnChanges(changes: { [propertyName: string]: SimpleChange }) {
    if ((changes['date'] || changes['dateFormat'])) {
      this.syncVisualsWithDate();
    }
    if (changes['firstDayOfTheWeek'] || changes['dayNames']) {
      this.updateDayNames();
    }
  }

  ngOnDestroy() {
    this.clickListener();
  }

  // -------------------------------------------------------------------------------- //
  // -------------------------------- State Management ------------------------------ //
  // -------------------------------------------------------------------------------- //
  /**
  * Closes the calendar and syncs visual components with selected or current date.
  * This way if a user opens the calendar with this month, scrolls to two months from now,
  * closes the calendar, then reopens it, it will open with the current month
  * or month associated with the selected date
  */
  closeCalendar(): void {
    this.showCalendar = false;
    this.syncVisualsWithDate();
  }

  /**
  * Sets the date values associated with the ui
  */
  private setCurrentValues(date: Date) {
    this.currentMonthNumber = date.getMonth();
    this.currentMonth = this.months[this.currentMonthNumber];

    this.currentYear = date.getFullYear();
    this.yearControl.setValue(this.currentYear);

    const calendarArray = this.calendar.monthDays(this.currentYear, this.currentMonthNumber);
    this.calendarDays = [].concat.apply([], calendarArray);
    this.calendarDays = this.filterInvalidDays(this.calendarDays);
  }

  /**
   * Update the day names order. The order can be modified with the firstDayOfTheWeek input, while 0 means that the
   * first day will be sunday.
   */
  private updateDayNames() {
    this.dayNamesOrdered = this.dayNames.slice(); // Copy DayNames with default value (weekStart = 0)
    if (this.weekStart < 0 || this.weekStart >= this.dayNamesOrdered.length) {
      // Out of range
      throw Error(`The weekStart is not in range between ${0} and ${this.dayNamesOrdered.length - 1}`)
    } else {
      this.calendar = new Calendar(this.weekStart);
      this.dayNamesOrdered = this.dayNamesOrdered.slice(this.weekStart, this.dayNamesOrdered.length)
        .concat(this.dayNamesOrdered.slice(0, this.weekStart)); // Append beginning to end
    }
  }

  /**
  * Visually syncs calendar and input to selected date or current day
  */
  syncVisualsWithDate(): void {
    if (this.date) {
      this.setInputText(this.date);
      this.setCurrentValues(this.date);
    } else {
      this.inputText = '';
      this.setCurrentValues(new Date());
    }
  }

  /**
  * Sets the currentMonth and creates new calendar days for the given month
  */
  setCurrentMonth(monthNumber: number): void {
    this.currentMonth = this.months[monthNumber];
    const calendarArray = this.calendar.monthDays(this.currentYear, this.currentMonthNumber);
    this.calendarDays = [].concat.apply([], calendarArray);
    this.calendarDays = this.filterInvalidDays(this.calendarDays);
  }

  /**
  * Sets the currentYear and FormControl value associated with the year
  */
  setCurrentYear(year: number): void {
    this.currentYear = year;
    this.yearControl.setValue(year);
  }

  /**
  * Sets the visible input text
  */
  setInputText(date: Date): void {
    let inputText = "";
    const dateFormat: string | DateFormatFunction = this.dateFormat;
    if (dateFormat === undefined || dateFormat === null) {
      inputText = moment(date).format(this.DEFAULT_FORMAT);
    } else if (typeof dateFormat === 'string') {
      inputText = moment(date).format(dateFormat);
    } else if (typeof dateFormat === 'function') {
      inputText = dateFormat(date);
    }
    this.inputText = inputText;
  }

  // -------------------------------------------------------------------------------- //
  // --------------------------------- Click Handlers ------------------------------- //
  // -------------------------------------------------------------------------------- //
  /**
  * Sets the date values associated with the calendar.
  * Triggers animation if the month changes
  */
  onArrowClick(direction: string): void {
    const currentMonth: number = this.currentMonthNumber;
    let newYear: number = this.currentYear;
    let newMonth: number;
    // sets the newMonth
    // changes newYear is necessary
    if (direction === 'left') {
      if (currentMonth === 0) {
        newYear = this.currentYear - 1;
        newMonth = 11;
      } else {
        newMonth = currentMonth - 1;
      }
    } else if (direction === 'right') {
      if (currentMonth === 11) {
        newYear = this.currentYear + 1;
        newMonth = 0;
      } else {
        newMonth = currentMonth + 1;
      }
    }
    // check if new date would be within range
    let newDate = new Date(newYear, newMonth);
    let newDateValid: boolean;
    if (direction === 'left') {
      newDateValid = !this.rangeStart || newDate.getTime() >= this.rangeStart.getTime();
    } else if (direction === 'right') {
      newDateValid = !this.rangeEnd || newDate.getTime() <= this.rangeEnd.getTime();
    }

    if (newDateValid) {
      this.setCurrentYear(newYear);
      this.currentMonthNumber = newMonth;
      this.setCurrentMonth(newMonth);
      // this.triggerAnimation(direction);
    }
  }

  /**
   * Check if a date is within the range.
   * @param date The date to check.
   * @return true if the date is within the range, false if not.
   */
  isDateValid(date: Date): boolean {
    return (!this.rangeStart || date.getTime() >= this.rangeStart.getTime()) &&
      (!this.rangeEnd || date.getTime() <= this.rangeEnd.getTime());
  }

  /**
   * Filter out the days that are not in the date range.
   * @param calendarDays The calendar days
   * @return {Array} The input with the invalid days replaced by 0
   */
  filterInvalidDays(calendarDays: Array<number>): Array<number> {
    let newCalendarDays: Array<any> = new Array<Date>();
    calendarDays.forEach((day: number | Date) => {
      if (day === 0 || !this.isDateValid(<Date>day)) {
        newCalendarDays.push(0)
      } else {
        newCalendarDays.push(day)
      }
    });
    return newCalendarDays;
  }

  /**
  * Closes the calendar when the cancel button is clicked
  */
  onCancel(): void {
    this.closeCalendar();
  }

  /**
  * Toggles the calendar when the date input is clicked
  */
  onInputClick(): void {
    this.showCalendar = !this.showCalendar;
  }

  /**
  * Returns the font color for a day
  */
  onSelectDay(day: Date): void {
    if (this.isDateValid(day)) {
      this.date = day;
      this.selectedDate = this.date;
      this.selectedDateRange1 = this.selectedWindow == this.windowList[0] ?  this.date :  (this.selectedWindow == this.windowList[1] ? startOfWeek(this.date) : startOfMonth(this.date));
      this.selectedDateRange2 = this.selectedWindow == this.windowList[0] ?  this.date :  (this.selectedWindow == this.windowList[1] ? endOfWeek(this.date) : endOfMonth(this.date));
      this.onSelect.emit(day);
      this.showCalendar = !this.showCalendar;
    }
  }

  /**
  * Sets the current year and current month if the year from
  * yearControl is valid
  */
  onYearSubmit(): void {
    if (this.yearControl.valid && +this.yearControl.value !== this.currentYear) {
      this.setCurrentYear(+this.yearControl.value);
      this.setCurrentMonth(this.currentMonthNumber);
    } else {
      this.yearControl.setValue(this.currentYear);
    }
  }

  // -------------------------------------------------------------------------------- //
  // ----------------------------------- Listeners ---------------------------------- //
  // -------------------------------------------------------------------------------- //
  /**
  * Closes the calendar if a click is not within the datepicker component
  */
  handleGlobalClick(event: MouseEvent): void {
    const withinElement = this.elementRef.nativeElement.contains(event.target);
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.closeCalendar();
    }
  }

  // -------------------------------------------------------------------------------- //
  // ----------------------------------- Helpers ------------------------------------ //
  // -------------------------------------------------------------------------------- //
  /**
  * Returns the background color for a day
  */
  getDayBackgroundColor(day: Date): string {
    let color = this.colors['grey'];
    if (this.isChosenDay(day)) {
      color = this.accentColor;
    } /*else if (this.isCurrentDay(day)) {
      color = this.colors['lightGrey'];
    }*/
    return color;
  }

  /**
  * Returns the font color for a day
  */
  getDayFontColor(day: Date): string {
    let color = this.colors['white'];
    if (this.isChosenDay(day)) {
      color = this.colors['white'];
    }
    return color;
  }

  /**
  * Returns whether a day is the chosen day
  */
  isChosenDay(day: Date): boolean {
    if (day) {
      return this.date ? day.toDateString() === this.date.toDateString() : false;
    } else {
      return false;
    }
  }

  /**
  * Returns whether a day is the current calendar day
  */
  isCurrentDay(day: Date): boolean {
    if (day) {
      return day.toDateString() === new Date().toDateString();
    } else {
      return false;
    }
  }

  /**
  * Returns whether a day is the day currently being hovered
  */
  isHoveredDay(day: Date): boolean {
    return this.hoveredDay ? this.hoveredDay === day && !this.isChosenDay(day) : false;
  }

  /**
  * Triggers an animation and resets to initial state after the duration of the animation
  */
  triggerAnimation(direction: string): void {
    this.animate = direction;
    setTimeout(() => this.animate = 'reset', 185);
  }

  // -------------------------------------------------------------------------------- //
  // ---------------------------------- Validators ---------------------------------- //
  // -------------------------------------------------------------------------------- //
  /**
  * Validates that a value is within the 'rangeStart' and/or 'rangeEnd' if specified
  */
  inRangeValidator(control: FormControl): ValidationResult {
    const value = control.value;

    if (this.currentMonthNumber) {
      const tentativeDate = new Date(+value, this.currentMonthNumber);
      if (this.rangeStart && tentativeDate.getTime() < this.rangeStart.getTime()) {
        return { 'yearBeforeRangeStart': true };
      }
      if (this.rangeEnd && tentativeDate.getTime() > this.rangeEnd.getTime()) {
        return { 'yearAfterRangeEnd': true };
      }
      return null;
    }

    return { 'currentMonthMissing': true };
  }

  /**
  * Validates that a value is a number greater than or equal to 1970
  */
  yearValidator(control: FormControl): ValidationResult {
    const value = control.value;
    const valid = !isNaN(value) && value >= 1970 && Math.floor(value) === +value;
    if (valid) {
      return null;
    }
    return { 'invalidYear': true };
  }

  toggleFilterType(value: string) {
    this._logger.log("output:= " + value);
  }

  filterWeekDays(weekDayIndex: number) {
    if (this.hideWeekDays.indexOf(weekDayIndex) == -1) {
      this.hideWeekDays.push(weekDayIndex);
      this.weekDayNames[weekDayIndex].color = this.colors['grey'];
    } else {
      this.hideWeekDays = this.hideWeekDays.filter(weekDay => weekDay != weekDayIndex);
      this.weekDayNames[weekDayIndex].color = this.colors['orange'];
    }
    this._logger.log(this.hideWeekDays);
  }

  changeEventDetailsFilter() {
    this._logger.log(this.eventDetailFilter);
    this.eventDetailFilter = Object.create(this.eventDetailFilter);
  }

  changeTimeFilter() {
    //this._logger.log(this.timeFilter);
    this.timeFilter = Object.create(this.timeFilter);
  }

  openSoActionsPopup(event: CustomCalendarEvent) {
    // this._logger.log(event);
    this.selectedEvent = event;

    this._logger.log("new event in header: ", this.selectedEvent);
    jQuery("#so-action").modal("show");
  }

  openAddEventPopup(event: any) {
     this._logger.log("creating new event for: ", event);
     this.selectedSlot = event;
     //jQuery("#add-event-action").modal("show");
  }

  passProviderList(event : any){
    this.providerList = event;
  }

  actionCompleted(response: string) {
    this._logger.log("Reloading...");
    this.selectedDate = new Date(this.selectedDate.getTime() + 1);
  }

  addEventCompleted(response: string){
    this._logger.log("Reloding..");
    if(response == "success"){
      this.selectedDate = new Date(this.selectedDate.getTime() + 1);
    }
  }

  showLoaderOnDataFetch(showLoader: boolean) {
    this._logger.log("loader: ", showLoader);
    this.showLoader = showLoader;
  }
}