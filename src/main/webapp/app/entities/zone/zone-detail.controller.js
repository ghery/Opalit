(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('ZoneDetailController', ZoneDetailController);

    ZoneDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Zone'];

    function ZoneDetailController($scope, $rootScope, $stateParams, previousState, entity, Zone) {
        var vm = this;

        vm.zone = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opalitApp:zoneUpdate', function(event, result) {
            vm.zone = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
