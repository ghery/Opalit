(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('HCurrencyDeleteController',HCurrencyDeleteController);

    HCurrencyDeleteController.$inject = ['$uibModalInstance', 'entity', 'HCurrency'];

    function HCurrencyDeleteController($uibModalInstance, entity, HCurrency) {
        var vm = this;

        vm.hCurrency = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            HCurrency.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
