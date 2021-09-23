import { HttpClientTestingModule } from "@angular/common/http/testing";
import { fakeAsync, flush, TestBed, tick } from "@angular/core/testing";
import { MatIconModule } from "@angular/material/icon";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ActivatedRoute, Router } from "@angular/router";
import { RouterTestingModule } from "@angular/router/testing";
import { appRoutes } from "src/app/app.module";
import { Movie } from "../../movie.model";
import { MoviesDetailComponent } from "../../movies-detail/movies-detail.component";
import { MoviesService } from "../../movies.service";
import { MoviesItemComponent } from "./movies-item.component";

describe('MovieItemComponent',()=>{
    const movieServiceSpy = jasmine.createSpyObj('MoviesService', ['onFetchTopRatedMovies']);
    
    beforeEach(async () => {
        TestBed.configureTestingModule({
          imports:[
            HttpClientTestingModule,
            RouterTestingModule.withRoutes(appRoutes),
            MatIconModule,
            MatSnackBarModule,BrowserAnimationsModule],
          declarations: [ MoviesItemComponent,MoviesDetailComponent ],
          providers:[
              {provide:MoviesService, useValue:movieServiceSpy}
                ]
        })
        .compileComponents();
      });
      //   test the creation of the component successfully
    it('initializtion of component successfully',fakeAsync(()=>{
      let fixture = TestBed.createComponent(MoviesItemComponent);
      let component = fixture.componentInstance;
      
      expect(component).toBeTruthy();
      flush();
  }));
  // check on navigation
  it('check navigation',fakeAsync(()=>{
    let fixture = TestBed.createComponent(MoviesItemComponent);
    let component = fixture.componentInstance;
    let response:Movie=new Movie(278,false,[{id:278,name:"drama"}],"","fight club","","",
    new Date(),1,1.2,90,"https://ichef.bbci.co.uk/news/976/cpsprodpb/A7E9/production/_118158924_gettyimages-507245091.jpg",[]);
    component.movie = response;
    const button:HTMLButtonElement = fixture.debugElement.nativeElement.querySelector("#detailsBtn");
    // button.click();
    let router:Router=TestBed.get(Router);
    let route:ActivatedRoute=TestBed.get(ActivatedRoute);
    // router.navigateByUrl('/movies');
    const navigateSpy = spyOn(router, 'navigate');
    component.onViewDetails(response.id);

    
    expect(navigateSpy).toHaveBeenCalled();
    let argument = navigateSpy.calls.mostRecent().args; 
    expect(argument[0]).toEqual([response.id]);
    flush();
  }));
});