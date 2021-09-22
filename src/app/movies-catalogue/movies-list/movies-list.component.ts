import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { UserService } from 'src/app/users.service';
import { Movie } from '../movie.model';
import { MoviesService } from '../movies.service';

@Component({
  selector: 'app-movies-list',
  templateUrl: './movies-list.component.html',
  styleUrls: ['./movies-list.component.css']
})
export class MoviesListComponent implements OnInit {
   public movieList:Movie[];

  constructor(private movService:MoviesService,private userService:UserService) {}

  ngOnInit(): void {
    this.movService.onFetchTopRatedMovies().subscribe(movies=>{
      this.movieList = movies;
          
  });
  
  }

}
