import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit{

  userData$: Observable<any>;
  constructor(private userService: UserService) { 
    this.userData$ = this.userService.getUserInfo();
  }

  ngOnInit() {
    // this.userData$.subscribe(data => {
    //   if(data) {
    //     console.log("user data: ", data);
    //   }
    // })
  }
}
