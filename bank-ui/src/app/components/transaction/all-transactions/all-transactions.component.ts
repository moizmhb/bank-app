import { Component } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Observable } from 'rxjs';
import { paginatorSettings } from 'src/app/constants';
import { TransactionService } from 'src/app/services/transaction.service';

@Component({
  selector: 'app-all-transactions',
  templateUrl: './all-transactions.component.html',
  styleUrls: ['./all-transactions.component.scss']
})
export class AllTransactionsComponent {
  paginatorSettings = paginatorSettings;
  displayedColumns: string[] = ['time', 'type', 'amount'];
  allTransactions$!: Observable<any>;
  getAllTransactionsCount$: Observable<any>;
  asdf: any;

  // length!: number;
  pageSize: number = paginatorSettings.pageSize;
  pageIndex: number = 0;
  // pageSizeOptions!: any[];

  constructor(private transactionService: TransactionService) {
    // this.allTransactions$ = this.transactionService.getAllTransactions();
    this.getAllTransactionsCount$ = this.transactionService.getAllTransactionsCount();
    // this.pageSizeOptions = 
  }

  ngOnInit() {
    this.callApi({ pageNumber: 0, pageSize: this.pageSize });
  }

  callApi({ pageNumber, pageSize }: { pageNumber: number, pageSize: number }) {
    console.log("pageNumber in callApi", pageNumber, pageSize)
    this.getAllTransactionsCount$ = this.transactionService.getAllTransactionsCount();
    this.allTransactions$ = this.transactionService.getAllTransactions({ pageNumber: pageNumber, pageSize: pageSize });
    // this.getAllTransactionsCount$.subscribe();
    // this.allTransactions$.subscribe();
  }

  handlePageEvent(e: PageEvent) {
    // this.pageEvent = e;
    // this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    console.log("pageNumber: ", e.pageIndex, "pageSize: ", this.pageSize)
    this.callApi({ pageNumber: e.pageIndex, pageSize: this.pageSize })
  }
}
