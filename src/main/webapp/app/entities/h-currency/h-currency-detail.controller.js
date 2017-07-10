(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('HCurrencyDetailController', HCurrencyDetailController);

    HCurrencyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'HCurrency', 'Currency'];

    function HCurrencyDetailController($scope, $rootScope, $stateParams, previousState, entity, HCurrency, Currency) {
        var vm = this;

        vm.hCurrency = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opalitApp:hCurrencyUpdate', function(event, result) {
            vm.hCurrency = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
