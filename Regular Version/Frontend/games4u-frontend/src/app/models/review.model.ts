export class Review {

    public id: number;

    public review: string;

    public user_id: number;

    public game_id: number;

    public timestamp: string;

    constructor(id: number, review: string, user_id: number, game_id: number, timestamp: string) {
        this.id = id;
        this.review = review;
        this.user_id = user_id;
        this.game_id = game_id;
        this.timestamp = timestamp;
    }
}