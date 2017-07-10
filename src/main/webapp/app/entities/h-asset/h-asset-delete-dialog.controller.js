(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('HAssetDeleteController',HAssetDeleteController);

    HAssetDeleteController.$inject = ['$uibModalInstance', 'entity', 'HAsset'];

    function HAssetDeleteController($uibModalInstance, entity, HAsset) {
        var vm = this;

        vm.hAsset = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            HAsset.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
