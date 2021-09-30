import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { Observable } from 'rxjs';
import { Movie } from '../movie.model';
import { MoviesService } from '../movies.service';

@Component({
  selector: 'app-movies-top-list',
  templateUrl: './movies-top-list.component.html',
  styleUrls: ['./movies-top-list.component.css']
})
export class MoviesTopListComponent implements OnInit {
  public isFetching:boolean=false;
  public movieList:Movie[]=[];
  moviesArray: any[] = []
  defaultRecords: any = 20;
  pageEvent :any;
  totalResults:number= 0;
  genres = [];
  genreSelected:string="None";

  constructor(private movieService:MoviesService) { }

  ngOnInit(): void {
    this.movieService.onFetchTopRatedMoviesPages().subscribe(movies=>{
      this.movieList = movies["data"];      
      this.totalResults = movies["total_results"];
      this.moviesArray = this.movieList.slice(0,this.defaultRecords);
      
    });
    this.movieService.onFetchGenres().subscribe(genres=>{
      // console.log(genres)
      this.genres = genres;
    })
  }
  onPaginateChange(data) {
    const currentPage = (data.pageIndex)+1;
    
    this.movieService.onFetchTopRatedMoviesPages(currentPage).subscribe(movies=>{
      this.movieList = movies["data"];
      this.moviesArray = this.movieList.slice(0,data.pageSize);
      
    })
    
  }

  

}
