(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('CalculDeleteController',CalculDeleteController);

    CalculDeleteController.$inject = ['$uibModalInstance', 'entity', 'Calcul'];

    function CalculDeleteController($uibModalInstance, entity, Calcul) {
        var vm = this;

        vm.calcul = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Calcul.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
