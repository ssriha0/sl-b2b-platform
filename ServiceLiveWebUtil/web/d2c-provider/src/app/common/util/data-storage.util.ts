import { Injectable } from '@angular/core';

@Injectable()
export class DataStorageUtil {
    private storage : any;

    cleanStorage() {
        this.storage = null;
    }

    setStorage(storage : any) {
        this.storage = storage;
    }

    getStorage() : any {
        return this.storage;
    }
}