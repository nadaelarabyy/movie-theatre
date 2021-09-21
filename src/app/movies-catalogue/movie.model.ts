export class Movie{
    public adult:boolean;
    public genres:{id:number,name:string}[];
    public homepage:string;
    public lang:string;
    public title:string;
    public overview:string;
    public releaseDate:Date;
    public voteCount:number;
    public popularity:number;
    public runtime:number;
    
    constructor(adult:boolean, genres:{id:number,name:string}[], homepage:string, lang:string,
         title:string, overview:string, releaseDate:Date,voteCount:number, popularity:number,runtime:number)
         {
             this.adult=adult;
             this.genres=genres.slice();
             this.homepage=homepage;
             this.lang=lang;
             this.title=title;
             this.overview=overview;
             this.releaseDate=releaseDate;
             this.voteCount=voteCount;
             this.popularity=popularity;
             this.runtime=runtime;
            }

}