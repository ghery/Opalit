(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('HBookDetailController', HBookDetailController);

    HBookDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'HBook', 'Book'];

    function HBookDetailController($scope, $rootScope, $stateParams, previousState, entity, HBook, Book) {
        var vm = this;

        vm.hBook = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opalitApp:hBookUpdate', function(event, result) {
            vm.hBook = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
