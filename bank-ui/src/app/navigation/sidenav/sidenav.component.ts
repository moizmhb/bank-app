import { Component, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent {
  @Output() closeSideNav = new EventEmitter();

  constructor(public router: Router) { }

  onToggleClose(value: string) {
    console.log("route", this.router)
    if(value == 'home') {
      this.router.navigate(['/home']);
    } else if(value == 'transaction') {
      this.router.navigate(['/transaction']);
    }
    this.closeSideNav.emit();
  }

  ngOnInit() {
  }

}
