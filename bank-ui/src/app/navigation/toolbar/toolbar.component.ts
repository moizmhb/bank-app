import { Component, EventEmitter, Output } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent {
  @Output() SideNavToggle = new EventEmitter();  
  @Output() UserNavToggle = new EventEmitter();  

  constructor(public userService: UserService) { }

  openSidenav() {
   this.SideNavToggle.emit();
  }

  openUsernav() {
    this.UserNavToggle.emit();
   }
}
