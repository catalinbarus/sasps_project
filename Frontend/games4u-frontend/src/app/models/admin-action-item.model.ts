export class AdminActionItem {
    public name: string;
    public description: string;
    public imagePath: string;
    public routeParam: string;
  
    constructor(
      name: string,
      description: string,
      imagePath: string,
      routeParam: string
    ) {
      this.name = name;
      this.description = description;
      this.imagePath = imagePath;
      this.routeParam = routeParam;
    }
  }