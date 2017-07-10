(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('MarketDeleteController',MarketDeleteController);

    MarketDeleteController.$inject = ['$uibModalInstance', 'entity', 'Market'];

    function MarketDeleteController($uibModalInstance, entity, Market) {
        var vm = this;

        vm.market = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Market.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
