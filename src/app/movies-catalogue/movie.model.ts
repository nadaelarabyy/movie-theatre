export class Movie{
    public id:number;
    public adult:boolean;
    public genres:any[];
    public homepage:string;
    public lang:string;
    public title:string;
    public overview:string;
    public releaseDate:Date;
    public voteCount:number;
    public popularity:number;
    public runtime:number;
    public imagePath:string;
    public production:string[];
    
    constructor(id:number,adult:boolean, genres:any[], homepage:string, lang:string,
         title:string, overview:string, releaseDate:Date,voteCount:number, popularity:number,runtime:number,
         imagePath:string,production:string[])
         {
             this.id=id;
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
             this.imagePath=imagePath;
             this.production = production;
            }

}
export class Review{
    public content:string;
    public author:string;
    public dateIssued:Date;
    public show:boolean;
    constructor(content:string,author:string,dateIssued:Date,show:boolean){
        this.content=content;
        this.author=author;
        this.dateIssued=dateIssued;
        this.show = show;
    }
}
export class CastMember{
    public name:string;
    public movieName:string;
    public image:string;
    constructor(name:string,movieName:string,image:string){
        this.name=name;
        this.movieName=movieName;
        this.image=image;
    }
}