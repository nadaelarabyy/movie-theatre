import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {MatCardModule} from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';


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

const appRoutes:Routes=[
  {path:'',component:LoginCardComponent,pathMatch:'full'},
  {path:'movies',component:MoviesCatalogueComponent},
  {path:'movies/:id',component:MoviesDetailComponent}
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
    MatCardModule,
    MatIconModule,
    RouterModule.forRoot(appRoutes)

  ],
  providers: [UserService,MoviesService],
  bootstrap: [AppComponent]
})
export class AppModule { }
