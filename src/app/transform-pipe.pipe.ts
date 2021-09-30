import { Pipe, PipeTransform } from '@angular/core';
import { Movie } from './movies-catalogue/movie.model';

@Pipe({
  name: 'convertRuntime'
})
export class TransformPipe implements PipeTransform {

  transform(num:number): string {
    return Math.floor(num /60) + "h " + Number(num%60) +"min";
  }

}

@Pipe({
  name: 'shorten'
})
export class ShortenPipe implements PipeTransform {

  transform(content:string): string {
    return content.slice(0,800)+"...";
  }

}

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {

  transform(movieList:Movie[],filterCategory:string): Movie[] {
    let out:Movie[] = [];
    if(filterCategory === "None")
      return movieList;
    else{
      for(let movie of movieList){
        // if(movie["genres"].includes(parseInt(filterCategory)))
        if(movie["genres"].includes(parseInt(filterCategory))){
            out.push(movie);          
        }
      }
      
      return out;
    }
  }

}
