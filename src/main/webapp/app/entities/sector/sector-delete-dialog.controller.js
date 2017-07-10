(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('SectorDeleteController',SectorDeleteController);

    SectorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Sector'];

    function SectorDeleteController($uibModalInstance, entity, Sector) {
        var vm = this;

        vm.sector = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Sector.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
