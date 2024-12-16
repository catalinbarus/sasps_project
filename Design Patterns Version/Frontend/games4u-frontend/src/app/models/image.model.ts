export class Image {

    public id: number;

    public name: string;

    public url: string;

    public type: string;

    public size: number;

    public gameId: number;

    public convertedData: string;

    constructor(id: number, name: string, url: string, type: string, size: number, gameId: number, convertedData: string) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
        this.gameId = gameId;
        this.convertedData = convertedData;
    }
}