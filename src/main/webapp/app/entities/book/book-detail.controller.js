(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('BookDetailController', BookDetailController);

    BookDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Book', 'Currency', 'Calcul', 'Asset', 'ParseLinks', 'AlertService', 'paginationConstants', 'Transaction'];

    function BookDetailController($scope, $rootScope, $stateParams, previousState, entity, Book, Currency, Calcul, Asset, ParseLinks, AlertService, paginationConstants, Transaction) {
        var vm = this;

        vm.book = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opalitApp:bookUpdate', function(event, result) {
            vm.book = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.books = [];
        vm.transactions = [];
                vm.loadPage = loadPage;
                vm.itemsPerPage = paginationConstants.itemsPerPage;
                vm.page = 0;
                vm.links = {
                    last: 0
                };
                vm.predicate = 'id';
                vm.reset = reset;
                vm.reverse = true;

                loadAll();

                function loadAll () {
                    Book.query({
                        page: vm.page,
                        size: vm.itemsPerPage,
                        sort: sort()
                    }, onSuccess, onError);

                    Transaction.query({
                        page: vm.page,
                        size: vm.itemsPerPage,
                        sort: sort()
                    }, onSuccess, onError);

                    function sort() {
                        var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                        if (vm.predicate !== 'id') {
                            result.push('id');
                        }
                        return result;
                    }

                    function onSuccess(data, headers) {
                        vm.links = ParseLinks.parse(headers('link'));
                        vm.totalItems = headers('X-Total-Count');
                        for (var i = 0; i < data.length; i++) {
                            vm.books.push(data[i]);
                            vm.transactions.push(data[i]);
                        }
                    }

                    function onError(error) {
                        AlertService.error(error.data.message);
                    }
                }

                function reset () {
                    vm.page = 0;
                    vm.books = [];
                    vm.transactions = [];
                    loadAll();
                }

                function loadPage(page) {
                    vm.page = page;
                    loadAll();
                }
    }
})();
