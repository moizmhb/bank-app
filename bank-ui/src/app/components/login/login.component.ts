import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { UserService } from 'src/app/services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { snackBarSettings } from 'src/app/constants';
// import { AlertService } from 'src/app/services/alert.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  form!: FormGroup;
  loading = false;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private snackBar: MatSnackBar
    // private alertService: AlertService
  ) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  // convenience getter for easy access to form fields
  get f() { return this.form.controls; }

  onSubmit() {
    console.log("onSubmit")
    this.submitted = true;

    // reset alerts on submit
    // this.alertService.clear();

    // stop here if form is invalid
    if (this.form.invalid) {
      return;
    }

    this.loading = true;
    this.userService.login(this.f['username'].value, this.f['password'].value)
      .pipe(first())
      .subscribe({
        next: (data) => {
          if (data.accessToken) { // login success
            // get return url from query parameters or default to home page
            this.snackBar.open('Logged in successfully', 'okay', { ...<any>snackBarSettings, panelClass: 'success' });
            const returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
            this.router.navigateByUrl(returnUrl);
          } else {
            this.loading = false;
            this.snackBar.open('Invalid Creds', 'okay', { ...<any>snackBarSettings, panelClass: 'error' });
            // this.alertService.error("Invalid Creds");
          }
        },
        error: error => {
          // this.alertService.error("Invalid Creds");
          // console.log("error: ", error)
          if (error.status == "401") {
            this.snackBar.open('Invalid username or password', 'okay', { ...<any>snackBarSettings, panelClass: 'mat-warn' });
          } else {
            this.snackBar.open('Something went wrong', 'okay', { ...<any>snackBarSettings, panelClass: 'error' });
          }
          this.loading = false;
        }
      });
  }
}