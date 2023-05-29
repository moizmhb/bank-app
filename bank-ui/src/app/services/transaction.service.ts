import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserService } from './user.service';
import { URLs } from '../constants';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  constructor(private http: HttpClient, private userService: UserService) { }

  getAllTransactions({ pageNumber, pageSize }: { pageNumber: number, pageSize: number }) {
    console.log("pageNumber", pageNumber, pageSize)
    return this.http.get(URLs.getAllTransactions + this.userService.userValue?.accountNumber + '&pageNumber='+ pageNumber +'&pageSize=' + pageSize);
  }

  getAllTransactionsCount() {
    return this.http.get(URLs.getAllTransactionsCount);
  }

  deposit(amount: number) {
    return this.http.post(URLs.deposit, { amount: amount, accountNumber: this.userService.userValue?.accountNumber})
  }

  withdraw(amount: number) {
    return this.http.post(URLs.withdraw, { amount: amount, accountNumber: this.userService.userValue?.accountNumber})

  }
}
