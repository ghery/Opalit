(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('HAssetDetailController', HAssetDetailController);

    HAssetDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'HAsset', 'Asset'];

    function HAssetDetailController($scope, $rootScope, $stateParams, previousState, entity, HAsset, Asset) {
        var vm = this;

        vm.hAsset = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opalitApp:hAssetUpdate', function(event, result) {
            vm.hAsset = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
