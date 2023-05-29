import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss']
})
export class DialogComponent {
  amount!: number;
  form!: FormGroup;
  
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _formBuilder: FormBuilder,
    private matDialogRef: MatDialogRef<any>
  ) { }

  ngOnInit(): void {
    
    this.form = this._formBuilder.group({
      accountId: [this.data?.accountNumber],
      amount: [null, [Validators.required, Validators.min(1), Validators.max(1000000)]],
      transaction_type: [this.data?. transactionType]
    });
    this.form.controls['accountId'].disable();
  }

  submit(): void {
    if (this.form.valid) {
      const payload = this.form.getRawValue();
      this.matDialogRef.close(payload);
    }
  }
}
