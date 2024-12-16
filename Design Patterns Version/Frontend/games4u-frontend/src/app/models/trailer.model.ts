export class Trailer {

    public id: number;

    public youtubeId: string;

    public gameId: number;

    constructor(id: number, youtubeId: string, gameId: number) {
        this.id = id;
        this.youtubeId = youtubeId;
        this.gameId = gameId;
    }
}