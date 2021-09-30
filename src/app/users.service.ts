import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, Subject } from "rxjs";
import { User } from "./user.model";
@Injectable({
    providedIn: 'root'
  })
export class UserService{
    loggedInChanged :BehaviorSubject<boolean>=new BehaviorSubject(null);
    private users:User[]=[
        new User('nadaelaraby@yahoo.com','nadodi@123'),
        new User('salmaNabil@gmail.com','salomii@123'),
        new User('nadamagdy@yahoo.com','nodz@123'),
        new User('sonduselshemy@yahoo.com','sondus@123'),
        new User('yomnaabdouelfotouh@yahoo.com','yomna@123'),
        new User('radwataha@yahoo.com','radwa@123')
    ];
    login(user:User){
        for(let user1 of this.users){
            if(user.email === user1.email && user.password === user1.password){
                
                 this.loggedInChanged.next(true);
                 return true;
            }
        }
        this.loggedInChanged.next(null);
        return false;
    }
    getRandomUser():User{
        const rand = Math.floor(Math.random() * this.users.length);
        const user = this.users[rand];
        return new User(user.email,user.password);
    }
    getVal(){
        return this.loggedInChanged.next(true);
        }
    
    

}