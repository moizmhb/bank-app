import { Component, ViewChild } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { snackBarSettings } from 'src/app/constants';
import { TransactionService } from 'src/app/services/transaction.service';
import { UserService } from 'src/app/services/user.service';
import { AllTransactionsComponent } from './all-transactions/all-transactions.component';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from './dialog/dialog.component';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.scss']
})
export class TransactionComponent {

  @ViewChild(AllTransactionsComponent) allTransactionsComp!: AllTransactionsComponent;
  amount: number | undefined;
  constructor(
    private transactionService: TransactionService,
    private userService: UserService,
    private snackBar: MatSnackBar,
    public dialog: MatDialog
  ) { }

  depsOrWith(type: string) {
    if (this.amount && this.amount > 0) {
      if (type == 'Deposit' && this.amount) {
        this.transactionService.deposit(this.amount).subscribe((data: any) => {
          if (data) {
            this.snackBar.open('Deposited successfully', 'okay', { ...<any>snackBarSettings, panelClass: 'success' });
            this.allTransactionsComp.callApi({ pageNumber: 0, pageSize: this.allTransactionsComp.pageSize });
            this.amount = undefined;
          } else {
            this.allTransactionsComp.callApi({ pageNumber: 0, pageSize: this.allTransactionsComp.pageSize });
            this.snackBar.open('Something went wrong', 'okay', { ...<any>snackBarSettings, panelClass: 'error' });
          }
        }, (error) => {
          if (error.status == '200') {
            this.snackBar.open('Deposited successfully', 'okay', { ...<any>snackBarSettings, panelClass: 'success' });
          } else if (error.status == '404') {
            this.snackBar.open('Invalid amount', 'okay', { ...<any>snackBarSettings, panelClass: 'error' });
          } else if (error.error?.message) {
            this.snackBar.open(error.error?.message, 'okay', { ...<any>snackBarSettings, panelClass: 'error' });
          } else {
            this.snackBar.open('Something went wrong', 'okay', { ...<any>snackBarSettings, panelClass: 'error' });
          }
          this.allTransactionsComp.callApi({ pageNumber: 0, pageSize: this.allTransactionsComp.pageSize });

        })
      } else if (type == "Withdraw" && this.amount) {
        this.transactionService.withdraw(this.amount).subscribe({
          next: (data: any) => {
            if (data) {
              this.snackBar.open('Withdrawn successfully', 'okay', { ...<any>snackBarSettings, panelClass: 'success' });
              this.allTransactionsComp.callApi({ pageNumber: 0, pageSize: this.allTransactionsComp.pageSize });
              this.amount = undefined;
            } else {
              this.allTransactionsComp.callApi({ pageNumber: 0, pageSize: this.allTransactionsComp.pageSize });
              this.snackBar.open('Insufficient balance', 'okay', { ...<any>snackBarSettings, panelClass: 'error' });
            }
          },
          error: (error: any) => {
            this.allTransactionsComp.callApi({ pageNumber: 0, pageSize: this.allTransactionsComp.pageSize });
            if (error.status == '400') {
              this.snackBar.open('Insufficient balance', 'okay', { ...<any>snackBarSettings, panelClass: 'error' });
            } else if (error.status == '200') {
              this.snackBar.open('Withdrawn successfully', 'okay', { ...<any>snackBarSettings, panelClass: 'success' });
            } else if (error.status == '404') {
              this.snackBar.open('Invalid amount', 'okay', { ...<any>snackBarSettings, panelClass: 'error' });
            }
            else {
              this.snackBar.open('Something went wrong', 'okay', { ...<any>snackBarSettings, panelClass: 'error' });
            }
          }
        })
      }
    }
  }

  openDialog(type: string) {
    let dialogRef = this.dialog.open(DialogComponent, {
      width: '330px',
      // height: '150px',
      data: { transactionType: type, accountNumber: this.userService.userValue?.accountNumber }
    });
    dialogRef.afterClosed().subscribe(data => {
      if (data) {
        console.log("data", data);
        this.amount = data.amount;
        this.depsOrWith(data.transaction_type);
      }
    })
  }
}
