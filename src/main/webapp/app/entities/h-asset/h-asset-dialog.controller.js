(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('HAssetDialogController', HAssetDialogController);

    HAssetDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'HAsset', 'Asset'];

    function HAssetDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, HAsset, Asset) {
        var vm = this;

        vm.hAsset = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.assets = Asset.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.hAsset.id !== null) {
                HAsset.update(vm.hAsset, onSaveSuccess, onSaveError);
            } else {
                HAsset.save(vm.hAsset, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('opalitApp:hAssetUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.day = false;
        vm.datePickerOpenStatus.time = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
