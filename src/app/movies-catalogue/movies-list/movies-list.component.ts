import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/users.service';
import { Movie } from '../movie.model';
import { MoviesService } from '../movies.service';

@Component({
  selector: 'app-movies-list',
  templateUrl: './movies-list.component.html',
  styleUrls: ['./movies-list.component.css']
})
export class MoviesListComponent implements OnInit {
   public topmovieList:Movie[];
   public popularMovies:Movie[];
   public nowPlayingMovies:Movie[];
   public upcomingMovies:Movie[];
   public isFetching:boolean=true;
   public movieName:string='';
   public movieGenre:string='';
   slides: any = [[]];
   slides2: any = [[]];
   slides3: any = [[]];
   slides4: any = [[]];
  constructor(private movService:MoviesService,
    private _snackbar:MatSnackBar,
    private router:Router, private route:ActivatedRoute) {}

  ngOnInit(): void {
    // ----------------------------Top Movies--------------------------------------
    this.movService.onFetchTopRatedMovies().subscribe(movies=>{
      this.topmovieList = movies;
      this.slides = this.chunk(this.topmovieList,4);
      this.isFetching=false;
          
  },error=>{
    this._snackbar.open(error["status_message"],"",{
      duration:2500,
      panelClass:["warning"]
    })

  });  
  // -------------------------Popular Movies------------------------------------------
  this.movService.onFetchPopular().subscribe(popMovies=>{
    this.popularMovies = popMovies;
    this.slides2 = this.chunk(this.popularMovies,4);    
  },error=>{
    this._snackbar.open(error["status_message"],"",{
      duration:2500,
      panelClass:["warning"]
    })

  })
  // -------------------------Now Playing---------------------------------------------
  this.movService.onFetchNowPlaying().subscribe(popMovies=>{
    this.nowPlayingMovies = popMovies;
    this.slides3 = this.chunk(this.nowPlayingMovies,4);    
  },error=>{
    this._snackbar.open(error["status_message"],"",{
      duration:2500,
      panelClass:["warning"]
    })

  })
  // ------------------------Up coming movies---------------------------------------
  this.movService.onFetchUpcoming().subscribe(popMovies=>{
    this.upcomingMovies = popMovies;
    this.slides4 = this.chunk(this.upcomingMovies,4);    
  },error=>{
    this._snackbar.open(error["status_message"],"",{
      duration:2500,
      panelClass:["warning"]
    })

  })


  
  }
  chunk(arr, chunkSize) {
    let R = [];
    for (let i = 0, len = arr.length; i < len; i += chunkSize) {
      R.push(arr.slice(i, i + chunkSize));
    }
    return R;
  }
  onViewMore(){
    this.router.navigate(['topRated'],{relativeTo:this.route})

  }
  

}
