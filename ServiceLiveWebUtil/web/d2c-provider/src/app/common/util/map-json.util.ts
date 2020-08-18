export default class MapJsonUtils {
    strMapToJson(strMap) {
        return JSON.stringify(this.strMapToObj(strMap));
    }

    jsonToStrMap(jsonStr) {
        return this.objToStrMap(JSON.parse(jsonStr));
    }

    strMapToObj(strMap) {
        let array = new Array();
        for (let [k, v] of strMap) {
            let obj = Object.create(null);
            obj['key'] = k;
            obj['value'] = v;
            array.push(obj);
        }
        return array;
    }

    objToStrMap(obj) {
        let strMap = Object.create(null);
        for (let k of Object.keys(obj)) {
            strMap[k] = obj[k];
        }
        return strMap;
    }
}
