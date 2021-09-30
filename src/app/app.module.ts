import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {MatIconModule} from '@angular/material/icon';
import {MatChipsModule} from '@angular/material/chips';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatCardModule} from '@angular/material/card';
// ----------------------------------------------
import { CarouselModule, MDBBootstrapModule, WavesModule } from 'angular-bootstrap-md';
import {MatPaginatorModule} from '@angular/material/paginator';

// ------------------------------------------------

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { LoginCardComponent } from './login-card/login-card.component';
import { MoviesCatalogueComponent } from './movies-catalogue/movies-catalogue.component';
import { MoviesListComponent } from './movies-catalogue/movies-list/movies-list.component';
import { MoviesItemComponent } from './movies-catalogue/movies-list/movies-item/movies-item.component';
import { MoviesDetailComponent } from './movies-catalogue/movies-detail/movies-detail.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FooterComponent } from './footer/footer.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { FormsModule } from '@angular/forms';
import { UserService } from './users.service';
import { MoviesService } from './movies-catalogue/movies.service';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { AuthGuard } from './auth-gurad.service';
import { FilterPipe, ShortenPipe, TransformPipe } from './transform-pipe.pipe';
import { MoviesTopListComponent } from './movies-catalogue/movies-top-list/movies-top-list.component';

export const appRoutes:Routes=[
  {path:'',redirectTo:'/movies',pathMatch:'full'},
  {path:'movies',component:MoviesCatalogueComponent
  ,canActivate:[AuthGuard]
  ,children:[
    {path:'',component:MoviesListComponent},
    {path:'topRated',component:MoviesTopListComponent},
    {path:':id',component:MoviesDetailComponent},
  ]},
  {path:'login',component:LoginCardComponent},
  {path:'error',component:ErrorPageComponent}
];
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginCardComponent,
    MoviesCatalogueComponent,
    MoviesListComponent,
    MoviesItemComponent,
    MoviesDetailComponent,
    FooterComponent,
    ErrorPageComponent,
    TransformPipe,
    ShortenPipe,
    FilterPipe,
    MoviesTopListComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    BrowserAnimationsModule,
    MatChipsModule,
    MatIconModule,
    MatSnackBarModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MDBBootstrapModule.forRoot(),
    CarouselModule, WavesModule,
    MatPaginatorModule
  ],
  providers: [UserService,MoviesService],
  bootstrap: [AppComponent]
})
export class AppModule { }
