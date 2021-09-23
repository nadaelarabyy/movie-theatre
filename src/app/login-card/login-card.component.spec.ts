import {  fakeAsync, TestBed, tick } from "@angular/core/testing";
import {  FormGroup, FormsModule } from "@angular/forms";
import { MatSnackBar, MatSnackBarModule } from "@angular/material/snack-bar";
import { By } from "@angular/platform-browser";
import {  Router } from "@angular/router";
import { User } from "../user.model";
import { UserService } from "../users.service";
import {LoginCardComponent} from './login-card.component';

describe('LoginCardComponent',()=>{
    const snackbar = {
        close: jasmine.createSpy('close')
      };
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    const userServiceSpy = jasmine.createSpyObj('UserService', ['login','getRandomUser']);
    const userspy = jasmine.objectContaining(['email','password']);
    
    
    beforeEach(async () => {
        TestBed.configureTestingModule({
          imports:[MatSnackBarModule,FormsModule],
          declarations: [ LoginCardComponent ],
          providers:[
              {provide: MatSnackBar,useValue: snackbar},
              {provide: Router, useValue:routerSpy},
              {provide:UserService, useValue:userServiceSpy},
            ]
        })
        .compileComponents();
      });
    //   ensure that the component is successfully created
        it('should create the app', fakeAsync(()=>
        {
            const fixture = TestBed.createComponent(LoginCardComponent);
            const loginComponent = fixture.componentInstance;
            fixture.detectChanges();
            expect(loginComponent).toBeTruthy();
        }
        )
        
        );
    
    // ensure the form initial state is correct
    it('component initial state', async() => {
        const fixture = TestBed.createComponent(LoginCardComponent);
        const component = fixture.componentInstance;
        fixture.detectChanges();
        expect(component.loginForm.touched).toBeFalsy();
        const userData:FormGroup = fixture.debugElement.nativeElement.querySelector("#user-data");
        expect(userData.valid).toBeFalsy();
      });

    // test validation of email and password, it should return true on correct values entered
    it('Validation of correct email and password entry', fakeAsync(() => {
        const fixture = TestBed.createComponent(LoginCardComponent);
        const component = fixture.componentInstance;
        component.ngOnInit();
        fixture.detectChanges();
        tick();
        component.loginForm.form.setValue(
            {
                userData:{email:'test@test.com',password:'test@123'}
            }
            );
        fixture.detectChanges();
        tick();
        expect(component.loginForm.form.valid).toBeTruthy();
        
      }));
    // test validation of email and password, it should return true on correct values entered
    it('validation message on email not entered', fakeAsync(() => {
        const fixture = TestBed.createComponent(LoginCardComponent);
        const component = fixture.componentInstance;
        component.ngOnInit();
        fixture.detectChanges();
        tick();
        component.loginForm.form.setValue(
            {
                userData:{email:'',password:'test@123'}
            }
            );
        component.loginForm.form.markAllAsTouched();
        fixture.detectChanges();
        tick();
        const span:HTMLElement  = fixture.debugElement.nativeElement.querySelector('#email-help');
        expect(span.innerText).toEqual('Please enter a valid email!');
      }));
// check the validation of password
      it('validation message on password not entered', fakeAsync(() => {
    const fixture = TestBed.createComponent(LoginCardComponent);
    const component = fixture.componentInstance;
    component.ngOnInit();
    fixture.detectChanges();
    tick();
    component.loginForm.form.setValue(
        {
            userData:{email:'test@test.com',password:''}
        }
        );
    component.loginForm.form.markAllAsTouched();
    fixture.detectChanges();
    tick();
    const span:HTMLElement  = fixture.debugElement.nativeElement.querySelector('#password-help');
    expect(span.innerText).toEqual('Please enter a password!');
  }));


});

