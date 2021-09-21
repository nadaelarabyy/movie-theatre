import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { User } from '../user.model';
import { UserService } from '../users.service';

@Component({
  selector: 'app-login-card',
  templateUrl: './login-card.component.html',
  styleUrls: ['./login-card.component.css']
})
export class LoginCardComponent implements OnInit{
  @ViewChild('f',{static:true}) loginForm!:NgForm;
  public loggedIn:boolean=false;

  constructor(private userService:UserService) { 
    
  }
  ngOnInit(){}
  onSubmit(){
    
    const user = new User(this.loginForm.form.value.userData.email,this.loginForm.form.value.userData.password);
    if(this.userService.login(user))
    {
      console.log("Exists!!!");
      this.loggedIn=true;
    }
    else{
      console.log('it does not exist!!!');
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

}
