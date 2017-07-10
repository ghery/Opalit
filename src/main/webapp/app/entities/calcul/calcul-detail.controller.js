(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('CalculDetailController', CalculDetailController);

    CalculDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Calcul'];

    function CalculDetailController($scope, $rootScope, $stateParams, previousState, entity, Calcul) {
        var vm = this;

        vm.calcul = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opalitApp:calculUpdate', function(event, result) {
            vm.calcul = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
