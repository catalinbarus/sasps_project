export class MyGameListEntry {

    public id: number;

    public videoGameId: number;

    public userId: number;

    public myGameListStatusId: number;

    public timestamp: string;

    constructor(id: number, videoGameId: number, userId: number, myGameListStatusId: number, timestamp: string) {
        this.id = id;
        this.videoGameId = videoGameId;
        this.userId = userId;
        this.myGameListStatusId = myGameListStatusId;
        this.timestamp = timestamp;
    }
}