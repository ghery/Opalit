(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('PeopleDialogController', PeopleDialogController);

    PeopleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'People'];

    function PeopleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, People) {
        var vm = this;

        vm.people = entity;
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
            if (vm.people.id !== null) {
                People.update(vm.people, onSaveSuccess, onSaveError);
            } else {
                People.save(vm.people, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('opalitApp:peopleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
