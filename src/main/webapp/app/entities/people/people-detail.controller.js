(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('PeopleDetailController', PeopleDetailController);

    PeopleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'People'];

    function PeopleDetailController($scope, $rootScope, $stateParams, previousState, entity, People) {
        var vm = this;

        vm.people = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opalitApp:peopleUpdate', function(event, result) {
            vm.people = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
