export class CoverageAPIZipCodes{
      Code : string;
      City: string;
      State: string;
      Latitude: string;
      Longitude: string;
      County: string;
      Distance:string;
      vendorLocId:string;
      selected:boolean=false;
   
     constructor(
      Code : string,
      City: string,
      State: string,
      Latitude: string,
      Longitude: string,
      County: string,
      Distance:string,
      vendorLocId:string,
      selected:boolean


     ){
           this.Code=Code;
           this.City=City;
           this.State=State;
           this.Latitude=Latitude;
           this.County = County;
           this.Distance = Distance;
           this.vendorLocId=vendorLocId;
           this.selected=selected;
     }
}