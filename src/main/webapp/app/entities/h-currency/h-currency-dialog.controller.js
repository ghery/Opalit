(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('HCurrencyDialogController', HCurrencyDialogController);

    HCurrencyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'HCurrency', 'Currency'];

    function HCurrencyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, HCurrency, Currency) {
        var vm = this;

        vm.hCurrency = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.currencies = Currency.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.hCurrency.id !== null) {
                HCurrency.update(vm.hCurrency, onSaveSuccess, onSaveError);
            } else {
                HCurrency.save(vm.hCurrency, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('opalitApp:hCurrencyUpdate', result);
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
