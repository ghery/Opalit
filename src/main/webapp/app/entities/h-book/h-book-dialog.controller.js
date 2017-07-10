(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('HBookDialogController', HBookDialogController);

    HBookDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'HBook', 'Book'];

    function HBookDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, HBook, Book) {
        var vm = this;

        vm.hBook = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.books = Book.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.hBook.id !== null) {
                HBook.update(vm.hBook, onSaveSuccess, onSaveError);
            } else {
                HBook.save(vm.hBook, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('opalitApp:hBookUpdate', result);
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
