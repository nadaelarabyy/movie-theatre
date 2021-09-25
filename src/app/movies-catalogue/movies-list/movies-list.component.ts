import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
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
   public isFetching:boolean=true;

  constructor(private movService:MoviesService,
    private _snackbar:MatSnackBar) {}

  ngOnInit(): void {
    this.movService.onFetchTopRatedMovies().subscribe(movies=>{
      this.movieList = movies;
      this.isFetching=false;
          
  },error=>{
    this._snackbar.open(error["status_message"],"",{
      duration:2500,
      panelClass:["warning"]
    })

  });
  
  }

}
