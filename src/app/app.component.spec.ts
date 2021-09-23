import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { RouterTestingModule } from '@angular/router/testing';



describe('AppComponent', () => {
  beforeEach(async () => {
    TestBed.configureTestingModule({
      imports:[RouterTestingModule],
      declarations: [
        AppComponent,
        HeaderComponent,
        FooterComponent
      ],
      providers: []

    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'movie-theatre'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('movie-theatre');
  });

});

