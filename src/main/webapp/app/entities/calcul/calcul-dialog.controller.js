(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('CalculDialogController', CalculDialogController);

    CalculDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Calcul'];

    function CalculDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Calcul) {
        var vm = this;

        vm.calcul = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.calcul.id !== null) {
                Calcul.update(vm.calcul, onSaveSuccess, onSaveError);
            } else {
                Calcul.save(vm.calcul, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('opalitApp:calculUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
