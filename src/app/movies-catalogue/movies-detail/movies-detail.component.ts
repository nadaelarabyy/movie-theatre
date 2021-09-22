import { Component, DoCheck, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { min } from 'rxjs/operators';
import { UserService } from 'src/app/users.service';
import { CastMember, Movie, Review } from '../movie.model';
import { MoviesService } from '../movies.service';

@Component({
  selector: 'app-movies-detail',
  templateUrl: './movies-detail.component.html',
  styleUrls: ['./movies-detail.component.css']
})
export class MoviesDetailComponent implements OnInit {
  id:number;
  public movieDetails:Movie=new Movie(0,false,[],'','','','',new Date(),0,0,0,'',[]);
  public cast_members!:CastMember[];
  public director!:string;
  public reviews:Review[];
  public stars:CastMember[]=[];
  public recommendations:Movie[]=[];
  public empty:boolean=false;
  constructor(private userService:UserService,
    private movService:MoviesService,
    private router:Router,
    private route:ActivatedRoute) { 
  }
  
  ngOnInit(): void {
    this.route.params.subscribe(routeParams=>{
      this.movService.onFetchMovieById(routeParams.id).subscribe(movie=>{
        this.movieDetails = movie;
      });
      this.movService.onFetchCastAndDirector(routeParams.id).subscribe(list=>{
        this.director = list.pop();
        this.cast_members = list.slice();
        let num:number = list.length;
        this.stars = list.splice(0,this.getMin(4,num));
        console.log(this.stars)
      });
      this.movService.onFetchRecommendations(routeParams.id).subscribe((list)=>{
        this.recommendations = list.slice();
      });
      this.movService.onFetchMovieReviews(routeParams.id).subscribe((list)=>{
        this.reviews = list;
        console.log(this.reviews);
        if(this.reviews.length==0)
          this.empty=true;
      });



    });
  }
getMin(x:number,y:number){
  return x>=y?y:x;
}

}
