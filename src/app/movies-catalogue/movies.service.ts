import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CastMember, Movie, Review } from "./movie.model";
import { map } from 'rxjs/operators';

@Injectable()
export class MoviesService{
    protected API_KEY="8c6f102e3e869d116008630637634ce3";
    topRatedMovies:Movie[]=[];
    constructor(private http:HttpClient){}
    onFetchTopRatedMovies(){
        return this.http.get(`https://api.themoviedb.org/3/movie/top_rated?api_key=${this.API_KEY}`)
        .pipe(map(responseData=>{
            const moviesArray=[];
            if(responseData.hasOwnProperty('results')){
                for(let movie of responseData['results']){
                    const movieModel = new Movie(movie['id'],movie['adult'],[],'',
                    movie['original_language'],movie['title'],movie['overview'],
                    new Date(movie['release_date']),movie['vote_count'],movie['vote_average'],0,
                    `http://image.tmdb.org/t/p/w500${movie['poster_path']}`,[]);
                    moviesArray.push(movieModel);
                }

            }
            return moviesArray;
        }
        ));
    }

    onFetchMovieById(movie_id:number){
        return this.http.get(`https://api.themoviedb.org/3/movie/${movie_id}?api_key=${this.API_KEY}&language=en-US`)
        .pipe(map((responseData=>{
            const movieDetails = new Movie(responseData['id'],responseData['adult'],responseData['genres'],
            responseData['homepage'],responseData['original_language'],responseData['title'],responseData['overview'],
            new Date(responseData['release_date']),responseData['vote_count'],responseData['vote_average'],
            responseData['runtime'],`http://image.tmdb.org/t/p/w342${responseData['poster_path']}`,
            responseData['production_companies']);
            return movieDetails;
        })

        ))
    }
    onFetchCastAndDirector(movie_id:number){
        return this.http.get(`https://api.themoviedb.org/3/movie/${movie_id}/credits?api_key=${this.API_KEY}&language=en-US`)
        .pipe(map((responseData)=>{
            // top 10 cast members
            let castMembers:any[]=[]
            if(responseData['cast'].length>10){
                for(let i=0;i<10;i++){
                    const name =responseData['cast'][i]['name'];
                    const character =responseData['cast'][i]['character'];
                    const img ='http://image.tmdb.org/t/p/w92'+responseData['cast'][i]['profile_path'];
                    castMembers.push(new CastMember(name,character,img));
                }
            }
            else{
                for(let i=0;i<responseData['cast'].length;i++){
                    const name =responseData['cast'][i]['name'];
                    const character =responseData['cast'][i]['character'];
                    const img ='http://image.tmdb.org/t/p/w92'+responseData['cast'][i]['profile_path'];
                    castMembers.push(new CastMember(name,character,img));
                }
                // castMembers = responseData['cast'];
            }
            // director
            let director:string = '';
            for(let crew_member of responseData['crew']){
                if(crew_member['job']==='Director'){
                    director = crew_member['name'];
                    break;
                }
            }
            castMembers.push(director);
            return castMembers;
        }));
    }
    onFetchMovieReviews(movie_id:number){
        return this.http.get(`https://api.themoviedb.org/3/movie/${movie_id}/reviews?api_key=${this.API_KEY}&language=en-US&page=1`)
        .pipe(map((responseData)=>{
            const reviews:Review[]=[];
            if(responseData['results'].length>5){
                for(let i=0;i<5;i++){
                    const review = responseData['results'][i]; 
                    const review_model = new Review(review['content'],review['author'],new Date(review['created_at']));
                    reviews.push(review_model);

                }
            }
            else{
                for(let review of responseData['results']){
                    const review_model = new Review(review['content'],review['author'],new Date(review['created_at']));
                    reviews.push(review_model);
                }
            }
            return reviews;
            

        }));
    }
    onFetchRecommendations(movie_id:number){
        return this.http.get(`https://api.themoviedb.org/3/movie/${movie_id}/recommendations?api_key=${this.API_KEY}&language=en-US&page=1`)
        .pipe(map(responseData=>{
            const recommendations=[];
            if(responseData['results'].length>5){

                for(let i=0; i<5;i++){
                    const recommend = responseData["results"][i];
                    const movieModel = new Movie(recommend['id'],recommend['adult'],[],'',
                    recommend['original_language'],recommend['title'],recommend['overview'],
                    new Date(recommend['release_date']),recommend['vote_count'],recommend['vote_average'],0,
                    `http://image.tmdb.org/t/p/w500${recommend['poster_path']}`,[]);
                    recommendations.push(movieModel);
                }
            }
            else{
                for(let recommend of responseData["results"]){
                    const movieModel = new Movie(recommend['id'],recommend['adult'],[],'',
                    recommend['original_language'],recommend['title'],recommend['overview'],
                    new Date(recommend['release_date']),recommend['vote_count'],recommend['vote_average'],0,
                    `http://image.tmdb.org/t/p/w500${recommend['poster_path']}`,[]);
                    recommendations.push(movieModel);

                }

            }
            return recommendations;
        }))
    }

    

}