(function() {
    'use strict';

    angular
        .module('opalitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('h-currency', {
            parent: 'entity',
            url: '/h-currency',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'opalitApp.hCurrency.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/h-currency/h-currencies.html',
                    controller: 'HCurrencyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('hCurrency');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('h-currency-detail', {
            parent: 'h-currency',
            url: '/h-currency/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'opalitApp.hCurrency.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/h-currency/h-currency-detail.html',
                    controller: 'HCurrencyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('hCurrency');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'HCurrency', function($stateParams, HCurrency) {
                    return HCurrency.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'h-currency',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('h-currency-detail.edit', {
            parent: 'h-currency-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/h-currency/h-currency-dialog.html',
                    controller: 'HCurrencyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HCurrency', function(HCurrency) {
                            return HCurrency.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('h-currency.new', {
            parent: 'h-currency',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/h-currency/h-currency-dialog.html',
                    controller: 'HCurrencyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                op: null,
                                hi: null,
                                lo: null,
                                la: null,
                                day: null,
                                vol: null,
                                pc: null,
                                time: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('h-currency', null, { reload: 'h-currency' });
                }, function() {
                    $state.go('h-currency');
                });
            }]
        })
        .state('h-currency.edit', {
            parent: 'h-currency',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/h-currency/h-currency-dialog.html',
                    controller: 'HCurrencyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HCurrency', function(HCurrency) {
                            return HCurrency.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('h-currency', null, { reload: 'h-currency' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('h-currency.delete', {
            parent: 'h-currency',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/h-currency/h-currency-delete-dialog.html',
                    controller: 'HCurrencyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['HCurrency', function(HCurrency) {
                            return HCurrency.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('h-currency', null, { reload: 'h-currency' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
