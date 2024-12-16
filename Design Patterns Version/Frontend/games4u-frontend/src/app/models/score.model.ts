export class Score {

    public id: number;

    public score: number;

    public userId: number;

    public gameId: number;

    constructor(id: number, score: number, userId: number, gameId: number) {
        this.id = id;
        this.score = score;
        this.userId = userId;
        this.gameId = gameId;
    }
}