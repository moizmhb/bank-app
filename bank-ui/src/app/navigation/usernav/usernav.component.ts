import { Component, EventEmitter, Output } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-usernav',
  templateUrl: './usernav.component.html',
  styleUrls: ['./usernav.component.scss']
})
export class UsernavComponent {
  @Output() closeUserNav = new EventEmitter();

  constructor(public userService: UserService) {

  }

  logout() {
    this.closeUserNav.emit();
    this.userService.logout();
  }
}
