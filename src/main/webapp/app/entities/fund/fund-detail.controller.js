(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('FundDetailController', FundDetailController);

    FundDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Fund', 'Asset', 'Book'];

    function FundDetailController($scope, $rootScope, $stateParams, previousState, entity, Fund, Asset, Book) {
        var vm = this;

        vm.fund = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opalitApp:fundUpdate', function(event, result) {
            vm.fund = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
