export class MyGameListConfig {

    public id: number;

    public backgroundColor: string;

    public textColor: string;

    public userId: number;

    constructor(id: number, backgroundColor: string, textColor: string, userId: number) {
        this.id = id;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.userId = userId;
    }
}