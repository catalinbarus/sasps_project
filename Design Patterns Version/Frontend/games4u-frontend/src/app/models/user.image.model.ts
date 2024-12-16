export class UserImage {

    public name: string;

    public url: string;

    public type: string;

    public size: number;

    public userId: number;

    public convertedData: string;

    constructor(name: string, url: string, type: string, size: number, userId: number, convertedData: string) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
        this.userId = userId;
        this.convertedData = convertedData;
    }
}