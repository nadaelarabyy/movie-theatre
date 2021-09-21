import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {MatIconModule} from '@angular/material/icon';
import {MatChipsModule} from '@angular/material/chips';

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

const appRoutes:Routes=[
  {path:'',component:LoginCardComponent,pathMatch:'full'},
  {path:'movies',component:MoviesCatalogueComponent,children:[
    {path:'',component:MoviesListComponent},
    {path:':id',component:MoviesDetailComponent},
  ]},
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
    ErrorPageComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    BrowserAnimationsModule,
    MatChipsModule,
    MatIconModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule

  ],
  providers: [UserService,MoviesService],
  bootstrap: [AppComponent]
})
export class AppModule { }
