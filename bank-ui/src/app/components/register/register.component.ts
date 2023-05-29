import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { UserService } from 'src/app/services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { snackBarSettings } from 'src/app/constants';
import { emailValidator } from './email.validator.directive';
// import { AlertService } from 'src/app/services/alert.service';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

    form!: FormGroup;
    loading = false;
    submitted = false;
    showPassword: boolean = false;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private userService: UserService,
        private snackBar: MatSnackBar
        //   private alertService: AlertService
    ) { }

    ngOnInit() {
        this.form = this.formBuilder.group({
            firstName: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
            lastName: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
            email: ['', [Validators.required, Validators.pattern(/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/)]],
            username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(10)]],
            password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(16), Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$/)]]
        });
    }

    // convenience getter for easy access to form fields
    get f() { return this.form.controls; }

    onSubmit() {
        this.submitted = true;

        // reset alerts on submit
        // this.alertService.clear();

        // stop here if form is invalid
        if (this.form.invalid) {
            return;
        }

        this.loading = true;
        this.userService.register(this.form.value)
            .pipe(first())
            .subscribe({
                next: () => {
                    // this.alertService.success('Registration successful', { keepAfterRouteChange: true });
                    this.snackBar.open('Registration successful', 'okay', { ...<any>snackBarSettings, panelClass: 'success' });
                    this.router.navigate(['../login'], { relativeTo: this.route });
                },
                error: error => {
                    // this.alertService.error(error);
                    // console.log("error", error)
                    if (error.error.message)
                        this.snackBar.open(error.error.message, 'okay', { ...<any>snackBarSettings, panelClass: 'error' });
                    else
                        this.snackBar.open("Something went wrong", 'okay', { ...<any>snackBarSettings, panelClass: 'error' });
                    this.loading = false;
                }
            });
    }
}
