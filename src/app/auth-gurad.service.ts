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
import { MatSnackBar } from '@angular/material/snack-bar';
  
  
  @Injectable({ providedIn: 'root' })
  export class AuthGuard implements CanActivate {
    constructor(private authService:UserService, private router: Router,private _matSnack:MatSnackBar){}
    canActivate():boolean|UrlTree|Promise<boolean|UrlTree>|Observable<boolean|UrlTree>{
    return this.authService.loggedInChanged.pipe(
          take(1),
          map(user => {
            const isAuth = localStorage.getItem('loggedIn');
            if (isAuth) {
              return true;
            }
            this._matSnack.open("User Not logged In or Session Expired!!","",{
              duration:2000,
              panelClass:["warning2"]
            });
            return  this.router.createUrlTree(['/login']);
            // return false;
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

  