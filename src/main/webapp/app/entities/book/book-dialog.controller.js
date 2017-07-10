(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('BookDialogController', BookDialogController);

    BookDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Book', 'Currency', 'Calcul', 'Asset'];

    function BookDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Book, Currency, Calcul, Asset) {
        var vm = this;

        vm.book = entity;
        vm.clear = clear;
        vm.save = save;
        vm.books = Book.query();
        vm.currencies = Currency.query();
        vm.calculs = Calcul.query();
        vm.assets = Asset.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.book.id !== null) {
                Book.update(vm.book, onSaveSuccess, onSaveError);
            } else {
                Book.save(vm.book, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('opalitApp:bookUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
