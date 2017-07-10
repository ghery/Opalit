(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('CurrencyDetailController', CurrencyDetailController);

    CurrencyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Currency'];

    function CurrencyDetailController($scope, $rootScope, $stateParams, previousState, entity, Currency) {
        var vm = this;

        vm.currency = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opalitApp:currencyUpdate', function(event, result) {
            vm.currency = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
