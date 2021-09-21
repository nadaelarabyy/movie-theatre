import { Component, OnInit } from '@angular/core';
import { Movie } from '../movie.model';
import { MoviesService } from '../movies.service';

@Component({
  selector: 'app-movies-list',
  templateUrl: './movies-list.component.html',
  styleUrls: ['./movies-list.component.css']
})
export class MoviesListComponent implements OnInit {
   public movieList:Movie[];

  constructor(private movService:MoviesService) {}

  ngOnInit(): void {
    this.movService.onFetchTopRatedMovies().subscribe(movies=>{
      this.movieList = movies;
          
  });
  }

}
