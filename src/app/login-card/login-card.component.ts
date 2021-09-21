import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from '../user.model';
import { UserService } from '../users.service';

@Component({
  selector: 'app-login-card',
  templateUrl: './login-card.component.html',
  styleUrls: ['./login-card.component.css']
})
export class LoginCardComponent implements OnInit{
  @ViewChild('f',{static:true}) loginForm!:NgForm;
  loggedIn: boolean=false;
  subscription:Subscription;
  constructor(private router:Router, private route:ActivatedRoute,
    private userService:UserService) { 
    
  }
  ngOnInit(){
    

  }
  onSubmit(){
    const user = new User(this.loginForm.form.value.userData.email,this.loginForm.form.value.userData.password);
    if(this.userService.login(user))
    {
      console.log("Exists!!!");

      this.router.navigate(['movies'],{relativeTo:this.route});
    }
    else{
      console.log('it does not exist!!!');
      this.router.navigate(['error'],{relativeTo:this.route});
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
