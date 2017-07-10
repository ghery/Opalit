(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('AssetDialogController', AssetDialogController);

    AssetDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Asset', 'Market', 'Currency', 'Country', 'City', 'Sector'];

    function AssetDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Asset, Market, Currency, Country, City, Sector) {
        var vm = this;

        vm.asset = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.markets = Market.query();
        vm.currencies = Currency.query();
        vm.countries = Country.query();
        vm.cities = City.query();
        vm.sectors = Sector.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.asset.id !== null) {
                Asset.update(vm.asset, onSaveSuccess, onSaveError);
            } else {
                Asset.save(vm.asset, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('opalitApp:assetUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setComments = function ($file, asset) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        asset.comments = base64Data;
                        asset.commentsContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.gdate = false;
        vm.datePickerOpenStatus.gdateb = false;
        vm.datePickerOpenStatus.gdatec = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
