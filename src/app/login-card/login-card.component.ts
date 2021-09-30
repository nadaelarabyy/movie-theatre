import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import {  ActivatedRoute, Router } from '@angular/router';
import {  Subscription } from 'rxjs';
import { User } from '../user.model';
import { UserService } from '../users.service';

@Component({
  selector: 'app-login-card',
  templateUrl: './login-card.component.html',
  styleUrls: ['./login-card.component.css']
})
export class LoginCardComponent implements OnInit{
  @ViewChild('f',{static:true}) loginForm!:NgForm;
  public loggedIn: boolean;
  private subscription:Subscription;
  text:string;
  constructor(private router:Router,private route:ActivatedRoute,
    private userService:UserService,private _snackBar: MatSnackBar) { 
      if(localStorage.getItem('loggedIn')==="true"){
      
        this.router.navigate(['movies']);
      }       
  }
  ngOnInit(){ 
    
         
  }
  onSubmit(){
    if(!this.loginForm.form.valid){
      this._snackBar.open("Please enter your email or password","close",
      {
        panelClass:['warning']
      });
    }
    else{
      const user = new User(this.loginForm.form.value.userData.email,this.loginForm.form.value.userData.password);
      
      this.loggedIn = this.userService.login(user);
      if(this.loggedIn){
        this.router.navigate(['movies']);
        this._snackBar.open("LoggedIn successfully","",{
          duration:2000,
          panelClass:['success']
        })
      }
      else{
        this._snackBar.open("User doesn't exits!!!","close",{
          panelClass:['warning']
        });
      }
    }
   
    
    
    
  }
  onSuggest(){
    
    const newUser = this.userService.getRandomUser();
    this.loginForm.setValue({
      userData:{
        email:newUser.email,
        password:newUser.password
      }
    });
  }
  // ngOnDestroy(){
  //   this.subscription.unsubscribe();

  // }

}
