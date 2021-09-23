import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { MoviesCatalogueComponent } from './movies-catalogue.component';



describe('MovieCatalogueComponent', () => {
  beforeEach(async () => {
    TestBed.configureTestingModule({
      imports:[RouterTestingModule],
      declarations: [
          MoviesCatalogueComponent
      ],
      providers: []

    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(MoviesCatalogueComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

});

