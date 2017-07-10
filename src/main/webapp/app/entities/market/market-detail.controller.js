(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('MarketDetailController', MarketDetailController);

    MarketDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Market', 'Currency', 'City'];

    function MarketDetailController($scope, $rootScope, $stateParams, previousState, entity, Market, Currency, City) {
        var vm = this;

        vm.market = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opalitApp:marketUpdate', function(event, result) {
            vm.market = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
