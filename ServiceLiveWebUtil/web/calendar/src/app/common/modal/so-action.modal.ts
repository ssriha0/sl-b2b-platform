import { SOButtons } from '../../services/so-action-button.service';

export class SOActionModal {

    type?: SOButtons;
    name: string;
    methodName?: string;
    params?: Map<string, any>;
    soDetails?: any;

    constructor(type: SOButtons, name: string, methodName: string, params: Map<string, any>, soDetails: any) {
        this.type = type;
        this.name = name;
        this.methodName = methodName;
        this.params = params;
        this.soDetails = soDetails;
    }
}