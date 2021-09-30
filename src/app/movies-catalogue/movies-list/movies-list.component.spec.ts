import { discardPeriodicTasks, fakeAsync, flush, flushMicrotasks, TestBed, tick } from "@angular/core/testing";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { HttpClientTestingModule } from '@angular/common/http/testing';
import {  MoviesService } from "../movies.service";
import { MoviesListComponent } from "./movies-list.component";
import { Movie } from "../movie.model";
import { of } from "rxjs";
import { MoviesItemComponent } from "./movies-item/movies-item.component";
import { RouterTestingModule } from "@angular/router/testing";
import { MatIconModule } from "@angular/material/icon";
import { UserService } from "src/app/users.service";
import { LoginCardComponent } from "src/app/login-card/login-card.component";
import { FormsModule } from "@angular/forms";
import { User } from "src/app/user.model";
import { MatCardModule } from "@angular/material/card";
import { CarouselModule, MDBBootstrapModule, WavesModule } from "angular-bootstrap-md";
import { MatPaginatorModule } from "@angular/material/paginator";



describe('MoviesListComponent',()=>{
      const movieServiceSpy = jasmine.createSpyObj('MoviesService', ['onFetchTopRatedMovies',
      'onFetchPopular','onFetchUpcoming','onFetchNowPlaying']);
      const userServiceSpy = jasmine.createSpyObj('UserService', ['getVal','login'])
      let fixture;
      let component;
    beforeEach(async () => {
        TestBed.configureTestingModule({
          imports:[
            MatSnackBarModule,
            HttpClientTestingModule,
            RouterTestingModule,
            MatIconModule,
            FormsModule,
            MatSnackBarModule,
            MatCardModule,
            MDBBootstrapModule.forRoot(),
            CarouselModule, WavesModule,
            MatPaginatorModule],
          declarations: [ MoviesListComponent,MoviesItemComponent, LoginCardComponent ],
          providers:[
              {provide:MoviesService, useValue:movieServiceSpy},
              {provide:UserService, useValue:userServiceSpy}
            ]
        })
        .compileComponents();
      });
    //   test the creation of the component successfully
    it('initializtion of component successfully',fakeAsync(()=>{
        fixture = TestBed.createComponent(MoviesListComponent);
        component = fixture.componentInstance;
        
        expect(component).toBeTruthy();
        flush()
    }));
    it('Fetching top rated movies successfully',fakeAsync(()=>{
      fixture = TestBed.createComponent(MoviesListComponent);

      component = fixture.componentInstance;
    
      let topMovies:Movie[]=[new Movie(1,false,[{id:1,name:"drama"}],"","en","top movies","brief overview",
      new Date(),2,1.2,90,"",[])];
      let popular:Movie[]=[new Movie(1,false,[{id:1,name:"drama"}],"","en","popular","brief overview",
      new Date(),2,1.2,90,"",[])];
      let upcoming:Movie[]=[new Movie(1,false,[{id:1,name:"drama"}],"","en","upcoming","brief overview",
      new Date(),2,1.2,90,"",[])];
      let nowplaying:Movie[]=[new Movie(1,false,[{id:1,name:"drama"}],"","en","now playing","brief overview",
      new Date(),2,1.2,90,"",[])];

      movieServiceSpy.onFetchTopRatedMovies.and.returnValue(of(topMovies));
      tick();

      movieServiceSpy.onFetchPopular.and.returnValue(of(popular));
      tick();

      movieServiceSpy.onFetchUpcoming.and.returnValue(of(upcoming));
      tick();

      movieServiceSpy.onFetchNowPlaying.and.returnValue(of(nowplaying));
      
      tick();
      fixture.detectChanges();
      // check on now playing movies
      const nowTitle:HTMLElement = fixture.debugElement.nativeElement.querySelector("#nowPlaying").querySelector('h2');      
      expect(component.nowPlayingMovies).toEqual(nowplaying); 
      expect(nowTitle.innerHTML).toContain('Now Playing')
      // check on popular
      const popTitle:HTMLElement = fixture.debugElement.nativeElement.querySelector("#popular").querySelector('h2');      
      expect(component.popularMovies).toEqual(popular); 
      expect(popTitle.innerHTML).toContain('Popular Movies');      
      // check top rated movies
      const title:HTMLElement = fixture.debugElement.nativeElement.querySelector("#topRated").querySelector('h2');      
      expect(component.topmovieList).toEqual(topMovies); 
      expect(title.innerHTML).toContain('Top Rated Movies'); 
      // check upcoming
      const upTitle:HTMLElement = fixture.debugElement.nativeElement.querySelector("#upcoming").querySelector('h2');      
      expect(component.upcomingMovies).toEqual(upcoming); 
      expect(upTitle.innerHTML).toContain('Upcoming Movies'); 
      flush();
      discardPeriodicTasks();
      
    }));
    it('data binded successfully to the child component',fakeAsync(()=>{
      fixture = TestBed.createComponent(MoviesListComponent);

      component = fixture.componentInstance;
    
      let topMovies:Movie[]=[new Movie(1,false,[{id:1,name:"drama"}],"","en","top movies","brief overview",
      new Date(),2,1.2,90,"",[])];
      let popular:Movie[]=[new Movie(1,false,[{id:1,name:"drama"}],"","en","popular","brief overview",
      new Date(),2,1.2,90,"",[])];
      let upcoming:Movie[]=[new Movie(1,false,[{id:1,name:"drama"}],"","en","upcoming","brief overview",
      new Date(),2,1.2,90,"",[])];
      let nowplaying:Movie[]=[new Movie(1,false,[{id:1,name:"drama"}],"","en","now playing","brief overview",
      new Date(),2,1.2,90,"",[])];

      movieServiceSpy.onFetchTopRatedMovies.and.returnValue(of(topMovies));
      tick();

      movieServiceSpy.onFetchPopular.and.returnValue(of(popular));
      tick();

      movieServiceSpy.onFetchUpcoming.and.returnValue(of(upcoming));
      tick();

      movieServiceSpy.onFetchNowPlaying.and.returnValue(of(nowplaying));
      
      tick();
      fixture.detectChanges();
      // check on top movies
      const title:HTMLElement = fixture.debugElement.nativeElement.querySelector("#item1").querySelector('h2');      
      expect(component.topmovieList).toEqual(topMovies); 
      expect(title.innerHTML).toEqual(topMovies[0].title);
      // check on popular
      const popTitle:HTMLElement = fixture.debugElement.nativeElement.querySelector("#item2").querySelector('h2');      
      expect(component.popularMovies).toEqual(popular); 
      expect(popTitle.innerHTML).toEqual(popular[0].title);      
      // check nowplaying
      const nowTitle:HTMLElement = fixture.debugElement.nativeElement.querySelector("#item3").querySelector('h2');      
      expect(component.nowPlayingMovies).toEqual(nowplaying); 
      expect(nowTitle.innerHTML).toEqual(nowplaying[0].title); 
      // check upcoming
      const upTitle:HTMLElement = fixture.debugElement.nativeElement.querySelector("#item4").querySelector('h2');      
      expect(component.upcomingMovies).toEqual(upcoming); 
      expect(upTitle.innerHTML).toEqual(upcoming[0].title); 
      flush();
      discardPeriodicTasks();

    }))
});

