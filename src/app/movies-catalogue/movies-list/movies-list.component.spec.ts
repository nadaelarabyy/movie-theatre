import { fakeAsync, TestBed, tick } from "@angular/core/testing";
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



describe('MoviesListComponent',()=>{
      const movieServiceSpy = jasmine.createSpyObj('MoviesService', ['onFetchTopRatedMovies']);
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
            MatSnackBarModule],
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
    }));
    it('Fetching top rated movies successfully',fakeAsync(()=>{
      const fixture = TestBed.createComponent(MoviesListComponent);

      const component = fixture.componentInstance;
    
      let response:Movie[]=[new Movie(1,false,[{id:1,name:"drama"}],"","en","fight club","brief overview",
      new Date(),2,1.2,90,"",[])];
      movieServiceSpy.onFetchTopRatedMovies.and.returnValue(of(response));
      fixture.detectChanges();
      tick();
      const title:HTMLElement = fixture.debugElement.nativeElement.querySelector("#item").querySelector('h2');
      expect(component.movieList).toEqual(response); 
      expect(title.innerHTML).toEqual(response[0].title);
      
    }));
    it('data binded successfully to the child component',fakeAsync(()=>{
      const fixture = TestBed.createComponent(MoviesListComponent);

      const component = fixture.componentInstance;
    
      let response:Movie[]=[new Movie(1,false,[{id:1,name:"drama"}],"","en","fight club","brief overview",
      new Date(),2,1.2,90,"",[])];
      movieServiceSpy.onFetchTopRatedMovies.and.returnValue(of(response));
      fixture.detectChanges();
      tick();
      const title:HTMLElement = fixture.debugElement.nativeElement.querySelector("#item").querySelector('h2');
      expect(title.innerHTML).toEqual(response[0].title);

    }))
});

