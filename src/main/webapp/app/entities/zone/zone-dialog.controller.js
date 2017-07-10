(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('ZoneDialogController', ZoneDialogController);

    ZoneDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Zone'];

    function ZoneDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Zone) {
        var vm = this;

        vm.zone = entity;
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
            if (vm.zone.id !== null) {
                Zone.update(vm.zone, onSaveSuccess, onSaveError);
            } else {
                Zone.save(vm.zone, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('opalitApp:zoneUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
