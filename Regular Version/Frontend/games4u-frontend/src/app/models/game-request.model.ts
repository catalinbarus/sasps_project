export class GameRequest {

    public id: number;

    public gameName: string;

    public link: string;

    public details: string;

    public userId: string;

    public createdAt: string;

    constructor(id: number, gameName: string, link: string, details: string, userId: string, createdAt: string) {
        this.id = id;
        this.gameName = gameName;
        this.link = link;
        this.details = details;
        this.userId = userId;
        this.createdAt = createdAt;
    }
}