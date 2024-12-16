export class GameDetailActionItem {
    public name: string;
    public routeParam: string;
  
    constructor(
      name: string,
      routeParam: string
    ) {
      this.name = name;
      this.routeParam = routeParam;
    }
  }