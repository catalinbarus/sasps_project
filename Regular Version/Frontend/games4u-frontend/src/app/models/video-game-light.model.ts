import { GameDeveloper } from "./game-developer.model";
import { GamePublisher } from "./game-publisher.model";
import { Genre } from "./genre.model";
import { Image } from "./image.model";
import { Review } from "./review.model";
import { Score } from "./score.model";
import { Trailer } from "./trailer.model";

export class VideoGameLight {

    public id: number;

    public name: string;

    public gameDeveloper: GameDeveloper;

    public gamePublisher: GamePublisher;

    public releaseDate: string;

    public genre: Genre;

    public rating: string;

    public description: string;

    public boxart: Image;

    public averageScore: string;

    public scores: Score[];

    public reviews: Review[];

    public trailers: Trailer[];

    constructor(
        id: number, 
        name: string, 
        gameDeveloper: GameDeveloper, 
        gamePublisher: GamePublisher, 
        releaseDate: string,
        genre: Genre,
        rating: string,
        description: string,
        boxart: Image,
        averageScore: string,
        scores: Score[],
        reviews: Review[],
        trailers: Trailer[]
    ) {
        this.id = id;
        this.name = name;
        this.gameDeveloper = gameDeveloper;
        this.gamePublisher = gamePublisher;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.rating = rating;
        this.description = description;
        this.boxart = boxart;
        this.averageScore = averageScore;
        this.scores = scores;
        this.reviews = reviews;
        this.trailers = trailers;
    }
}