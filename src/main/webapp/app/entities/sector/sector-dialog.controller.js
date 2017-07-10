(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('SectorDialogController', SectorDialogController);

    SectorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Sector'];

    function SectorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Sector) {
        var vm = this;

        vm.sector = entity;
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
            if (vm.sector.id !== null) {
                Sector.update(vm.sector, onSaveSuccess, onSaveError);
            } else {
                Sector.save(vm.sector, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('opalitApp:sectorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
