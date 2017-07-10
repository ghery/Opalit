(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('PreferenceDetailController', PreferenceDetailController);

    PreferenceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Preference', 'People'];

    function PreferenceDetailController($scope, $rootScope, $stateParams, previousState, entity, Preference, People) {
        var vm = this;

        vm.preference = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opalitApp:preferenceUpdate', function(event, result) {
            vm.preference = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
