import { Pipe, PipeTransform } from '@angular/core';

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
