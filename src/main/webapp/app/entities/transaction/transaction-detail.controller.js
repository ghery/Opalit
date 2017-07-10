(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('TransactionDetailController', TransactionDetailController);

    TransactionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Transaction', 'Book', 'Asset', 'Currency', 'People'];

    function TransactionDetailController($scope, $rootScope, $stateParams, previousState, entity, Transaction, Book, Asset, Currency, People) {
        var vm = this;

        vm.transaction = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opalitApp:transactionUpdate', function(event, result) {
            vm.transaction = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
