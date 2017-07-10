(function() {
    'use strict';

    angular
        .module('opalitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('calcul', {
            parent: 'entity',
            url: '/calcul',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'opalitApp.calcul.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/calcul/calculs.html',
                    controller: 'CalculController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('calcul');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('calcul-detail', {
            parent: 'calcul',
            url: '/calcul/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'opalitApp.calcul.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/calcul/calcul-detail.html',
                    controller: 'CalculDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('calcul');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Calcul', function($stateParams, Calcul) {
                    return Calcul.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'calcul',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('calcul-detail.edit', {
            parent: 'calcul-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calcul/calcul-dialog.html',
                    controller: 'CalculDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Calcul', function(Calcul) {
                            return Calcul.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('calcul.new', {
            parent: 'calcul',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calcul/calcul-dialog.html',
                    controller: 'CalculDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                marketvalue: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('calcul', null, { reload: 'calcul' });
                }, function() {
                    $state.go('calcul');
                });
            }]
        })
        .state('calcul.edit', {
            parent: 'calcul',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calcul/calcul-dialog.html',
                    controller: 'CalculDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Calcul', function(Calcul) {
                            return Calcul.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('calcul', null, { reload: 'calcul' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('calcul.delete', {
            parent: 'calcul',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calcul/calcul-delete-dialog.html',
                    controller: 'CalculDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Calcul', function(Calcul) {
                            return Calcul.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('calcul', null, { reload: 'calcul' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
