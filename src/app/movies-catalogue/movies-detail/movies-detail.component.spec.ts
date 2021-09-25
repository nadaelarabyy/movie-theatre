import { HttpClientTestingModule } from "@angular/common/http/testing";
import { fakeAsync, flush, TestBed, tick } from "@angular/core/testing";
import { MatChipsModule } from "@angular/material/chips";
import { MatIconModule } from "@angular/material/icon";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { RouterTestingModule } from "@angular/router/testing";
import { of } from "rxjs";
import { appRoutes } from "src/app/app.module";
import { ShortenPipe, TransformPipe } from "src/app/transform-pipe.pipe";
import { CastMember, Movie, Review } from "../movie.model";
import { MoviesItemComponent } from "../movies-list/movies-item/movies-item.component";
import { MoviesService } from "../movies.service";
import { MoviesDetailComponent } from "./movies-detail.component";

describe('MovieDetailComponeny',()=>{
    const movieServiceSpy:any = jasmine.createSpyObj('MoviesService', 
    ['onFetchMovieById','onFetchCastAndDirector','onFetchRecommendations',
    'onFetchMovieReviews'],{"id":278});
    // let movieServiceSpy;

    beforeEach(async ()=>{
        TestBed.configureTestingModule({
            imports:[
              HttpClientTestingModule,
              RouterTestingModule.withRoutes(appRoutes),
              MatIconModule,
              MatSnackBarModule,
              BrowserAnimationsModule,
              MatChipsModule],
            declarations: [MoviesDetailComponent,MoviesItemComponent,TransformPipe, ShortenPipe ],
            providers:[
                {provide:MoviesService, useValue:movieServiceSpy},
                  ]
          })
          .compileComponents();
    });
     //   test the creation of the component successfully
     it('initializtion of component successfully',fakeAsync(()=>{
        let fixture = TestBed.createComponent(MoviesDetailComponent);
        let component = fixture.componentInstance;
        
        expect(component).toBeTruthy();
        flush();
    }));
    // test fetching of movie details, recommendations, reviews
    it('Fetching a Movie by details including basic details, recommendations and user reviews',fakeAsync(()=>{
        const fixture = TestBed.createComponent(MoviesDetailComponent);
        const component = fixture.componentInstance;
        // movie detail
        let movieDetail = new Movie(278,false,[{id:1,name:"drama"}],"www.youtube.com","en","Fight Club",
        "Some overview",new Date(),2,1.2,94,"https://ichef.bbci.co.uk/news/976/cpsprodpb/A7E9/production/_118158924_gettyimages-507245091.jpg",[]);
    
        movieServiceSpy.onFetchMovieById.and.returnValue(of(movieDetail));
        // -------------------------------------------------------------------------------------------
        let recommendations:Movie[] = [new Movie(278,false,[{id:1,name:"drama"}],"www.youtube.com",
        "en","fight club","brief overview",new Date(),2,1.2,95,"https://ichef.bbci.co.uk/news/976/cpsprodpb/A7E9/production/_118158924_gettyimages-507245091.jpg",[])];
        movieServiceSpy.onFetchRecommendations.and.returnValue(of(recommendations));
        // -----------------------------------------------------------------------------------
        let cast:any =[new CastMember("brad pitt","fight club","https://ichef.bbci.co.uk/news/976/cpsprodpb/A7E9/production/_118158924_gettyimages-507245091.jpg"),"Denzel washiongton"];
        let directorMovie = cast[cast.length-1];
        
        movieServiceSpy.onFetchCastAndDirector.and.returnValue(of(cast));
        // -----------------------------------------------------------------------------------
        let reviews:Review[] = [new Review("some message","nada",new Date(),false)];
        movieServiceSpy.onFetchMovieReviews.and.returnValue(of(reviews));
        fixture.detectChanges();
        tick();
        // check about movie detail ==> an exampl feature title
        const title:HTMLElement = fixture.debugElement.nativeElement.querySelector('h2');
        expect(title.innerHTML).toEqual(movieDetail.title);
        // check about recommendation ==> an exampl movie recommendation title
        const recommend:HTMLElement = fixture.debugElement.nativeElement.querySelector("#movieItem").querySelector("h2");
        expect(recommend.innerHTML).toEqual(recommendations[0].title);
        // check about cast crew ===> an example feature director name
        const director:HTMLElement = fixture.debugElement.nativeElement.querySelector("#director")
        expect(director.innerHTML).toEqual(directorMovie);
        // check about user reviews ===> author name
        const reviewerName:HTMLElement = fixture.debugElement.nativeElement.querySelector("#reviewerName");
         expect(reviewerName.innerHTML).toEqual(reviews[0].author);
        flush();
    }))

});