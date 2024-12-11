import { MyGameListConfig } from "./my-game-list-config.model";
import { MyGameListEntry } from "./my-game-list-entry.model";
import { Role } from "./role.model";
import { UserImage } from "./user.image.model";

export class User {

    public id: number;

    public username: string;

    public email: string;

    public roles: Role[];

    public profilePicture: UserImage;

    public myGameListConfig: MyGameListConfig;

    public myGameListEntries: MyGameListEntry[];

    constructor(id: number, username: string, email: string, roles: Role[], profilePicture: UserImage, myGameListConfig: MyGameListConfig, myGameListEntries: MyGameListEntry[]) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.profilePicture = profilePicture;
        this.myGameListConfig = myGameListConfig;
        this.myGameListEntries = myGameListEntries;
    }

}