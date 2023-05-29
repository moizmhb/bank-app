export const hostname = "http://localhost:82/"
export const URLs = {
    login: hostname + 'api/auth/signin#',
    register: hostname + 'api/auth/signup#',
    getUserInfo: hostname + 'api/customers/',
    getAllTransactions: 'http://localhost:82/api/transaction/allTransactions?accountNumber=',//'api/getAllTransactions',
    getAllTransactionsCount: hostname + 'api/transaction/count',
    deposit: 'http://localhost:82/api/transaction/deposit',//'api/deposit',
    withdraw: 'http://localhost:82/api/transaction/withdraw'
};

export const snackBarSettings = {
    duration: 3000,
    horizontalPosition: 'end',
    verticalPosition: 'top',
};

export const paginatorSettings = {
    pageSize: 5,
    showFirstLastButtons: true,
    showPageSizeOptions: [5,10,15],
    hidePageSize: false
}