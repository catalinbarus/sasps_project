export class GamePublisher {

    public id: number;

    public name: string;

    public establishDate: string;

    constructor(id: number, name: string, establishDate: string) {
        this.id = id;
        this.name = name;
        this.establishDate = establishDate;
    }
}