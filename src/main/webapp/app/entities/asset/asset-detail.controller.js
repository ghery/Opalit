(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('AssetDetailController', AssetDetailController);

    AssetDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Asset', 'Market', 'Currency', 'Country', 'City', 'Sector'];

    function AssetDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Asset, Market, Currency, Country, City, Sector) {
        var vm = this;

        vm.asset = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('opalitApp:assetUpdate', function(event, result) {
            vm.asset = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
