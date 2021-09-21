import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/users.service';
import { Movie } from '../../movie.model';

@Component({
  selector: 'app-movies-item',
  templateUrl: './movies-item.component.html',
  styleUrls: ['./movies-item.component.css']
})
export class MoviesItemComponent implements OnInit {
  @Input() movie:Movie;
  constructor(private userService:UserService,private router:Router,private route:ActivatedRoute) { 
  }

  ngOnInit(): void {
  }
  onViewDetails(id:number){
    if(this.route.snapshot.params['id']!==undefined){
      // console.log('hello');
      this.router.navigate(['../',id],{relativeTo:this.route});

    }
    else{
      this.router.navigate([id],{relativeTo:this.route});
    }
    
  }

}
