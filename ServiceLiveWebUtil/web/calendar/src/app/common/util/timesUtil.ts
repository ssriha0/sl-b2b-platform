export class TimeUtils{
    static getUTCTime(browderDateTime : Date) : number{
        return new Date(browderDateTime.getUTCFullYear(), browderDateTime.getUTCMonth(),
        browderDateTime.getUTCDate(),  browderDateTime.getUTCHours(), browderDateTime.getUTCMinutes(),
        browderDateTime.getUTCSeconds()).getTime();
    }
}