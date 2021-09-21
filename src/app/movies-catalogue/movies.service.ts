import { Movie } from "./movie.model";

export class MoviesService{
    private movies:Movie[]=[
        new Movie(false,[{id:1,name:'drama'}],'A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.','en','Ad stra','some text',new Date(),
        1500,8.4,139),
        new Movie(false,[{id:1,name:'drama'}],'A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.','en','Ad stra','some text',new Date(),
        1500,8.4,139),
        new Movie(false,[{id:1,name:'drama'}],'A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.','en','Ad stra','some text',new Date(),
        1500,8.4,139),
        new Movie(false,[{id:1,name:'drama'}],'A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.','en','Ad stra','some text',new Date(),
        1500,8.4,139),
        new Movie(false,[{id:1,name:'drama'}],'A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.','en','Ad stra','some text',new Date(),
        1500,8.4,139)
    ];
    getMovies(){
        return this.movies.slice();
    }
    

}