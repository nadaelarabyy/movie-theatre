import {
    CanActivate,
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
    Router,
    UrlTree
  } from '@angular/router';
  import { Injectable } from '@angular/core';
  import { Observable } from 'rxjs';
  import { map, take } from 'rxjs/operators';
import { UserService } from './users.service';
  
  
  @Injectable({ providedIn: 'root' })
  export class AuthGuard implements CanActivate {
    constructor(private authService:UserService, private router: Router){}
    canActivate():boolean|UrlTree|Promise<boolean|UrlTree>|Observable<boolean|UrlTree>{
    return this.authService.loggedInChanged.pipe(
          take(1),
          map(user => {
            const isAuth = !!user;
            if (isAuth) {
              return true;
            }
            return  this.router.createUrlTree(['/error']);;
          })
        );
    }

  }
//     constructor(private authService: UserService, private router: Router) {}
  
//     canActivate(route: ActivatedRouteSnapshot,router: RouterStateSnapshot):
//       boolean
//       | UrlTree
//       | Promise<boolean | UrlTree>
//       | Observable<boolean | UrlTree> {
          
      // return this.authService.loggedInChanged.pipe(
      //   take(1),
      //   map(user => {
      //     const isAuth = !!user;
      //     if (isAuth) {
      //       return true;
      //     }
      //     return false;
      //   })
      // );
//     }
//   }

  