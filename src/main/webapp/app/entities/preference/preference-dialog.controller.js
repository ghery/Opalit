(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('PreferenceDialogController', PreferenceDialogController);

    PreferenceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Preference', 'People'];

    function PreferenceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Preference, People) {
        var vm = this;

        vm.preference = entity;
        vm.clear = clear;
        vm.save = save;
        vm.people = People.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.preference.id !== null) {
                Preference.update(vm.preference, onSaveSuccess, onSaveError);
            } else {
                Preference.save(vm.preference, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('opalitApp:preferenceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
