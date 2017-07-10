(function() {
    'use strict';

    angular
        .module('opalitApp')
        .controller('HBookDeleteController',HBookDeleteController);

    HBookDeleteController.$inject = ['$uibModalInstance', 'entity', 'HBook'];

    function HBookDeleteController($uibModalInstance, entity, HBook) {
        var vm = this;

        vm.hBook = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            HBook.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
