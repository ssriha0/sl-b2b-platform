export class ServiceDayList{
    serviceDay:Array<ServiceDayResponseUIModal>;
    serviceRatePeriod:Array<ServiceRateCardReseponseUIModal>;
}
export class ServiceDayResponseUIModal {
    id: number;
    name: string;
}
export class ServiceRateCardReseponseUIModal {
    id: number;
    name: string;
}