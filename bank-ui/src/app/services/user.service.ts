import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, filter, first, map } from 'rxjs';
import { User } from '../models/user';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { URLs } from '../constants';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userSubject: BehaviorSubject<User | null>;
  public user: Observable<User | null>;
  currentUser: any;

  constructor(private router: Router, private http: HttpClient) {
    this.userSubject = new BehaviorSubject(
      JSON.parse(localStorage.getItem('user')!)
    );
    this.user = this.userSubject.asObservable();
  }

  public get userValue() {
    return this.userSubject.value;
  }
  
  login(username: string, password: string) {
    return this.http
      .post<User>(URLs.login, {
        username,
        password,
      })
      .pipe(
        map((user) => {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('user', JSON.stringify(user));
          this.userSubject.next(user);
          return user;
        })
      );
  }

  logout() {
    // remove user from local storage and set current user to null
    localStorage.removeItem('user');
    this.userSubject.next(null);
    this.router.navigate(['/login']);
  }

  register(user: User) {
    // this.createAccount();
    return this.http.post(URLs.register, {...user, role: []})
  }

  getUserInfo() {
    return this.http.get(URLs.getUserInfo + this.userValue?.customerNumber).pipe(map((data: any) => {
      this.userSubject.next({ ...this.userValue, firstName: data.firstName, lastName: data.lastName, email: data.email });
      return data;
    }));
  }

  // createAccount() {
  //   this.getAllCustomerDetails().subscribe((data: any) => {
  //     if (data) {
  //       this.currentUser = data.filter((ele: any) => ele.email == JSON.parse(localStorage['user'])['email'])[0];

        // let addAccountPayload = {
        //   "accountBalance": "10000.00",
        //   "accountNumber": "5010",
        //   "accountStatus": "Active",
        //   "accountType": "current",
        //   "createDateTime": new Date(),
        //   // "customer": {
        //   //   "value": "<Circular reference to #/components/schemas/Customer detected>"
        //   // },
        //   "id": "1234",
        //   "updateDateTime": new Date()
        // }
  //       this.http.post(URLs.addAccount + this.currentUser['customerNumber'], addAccountPayload).subscribe(data => {
  //         if (data) {
  //           console.log("Account created successfully");
  //         }
  //       });
  //     }
  //   })
  // }

  // getAllCustomerDetails() {
  //   return this.http.get(URLs.getAllCustDetails).pipe(first());
  // }
}
