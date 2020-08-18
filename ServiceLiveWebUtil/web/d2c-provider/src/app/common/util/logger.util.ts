import { Injectable } from "@angular/core";

@Injectable()
export class LoggerUtil {
    private _log: Function;

    constructor() {
        this._log = console.log;
        this.blockDefaultConsoleMethods();
    }

    public log(...args : any[]) {
        // Function.prototype.apply.call(this._log, console, args);
    }

    private blockDefaultConsoleMethods() {
        console.log = console.info = console.warn = console.error = () => null;
    }
}