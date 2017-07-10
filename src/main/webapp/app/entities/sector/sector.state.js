(function() {
    'use strict';

    angular
        .module('opalitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sector', {
            parent: 'entity',
            url: '/sector',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'opalitApp.sector.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sector/sectors.html',
                    controller: 'SectorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sector');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sector-detail', {
            parent: 'sector',
            url: '/sector/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'opalitApp.sector.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sector/sector-detail.html',
                    controller: 'SectorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sector');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Sector', function($stateParams, Sector) {
                    return Sector.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sector',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sector-detail.edit', {
            parent: 'sector-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sector/sector-dialog.html',
                    controller: 'SectorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sector', function(Sector) {
                            return Sector.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sector.new', {
            parent: 'sector',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sector/sector-dialog.html',
                    controller: 'SectorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                sector: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sector', null, { reload: 'sector' });
                }, function() {
                    $state.go('sector');
                });
            }]
        })
        .state('sector.edit', {
            parent: 'sector',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sector/sector-dialog.html',
                    controller: 'SectorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sector', function(Sector) {
                            return Sector.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sector', null, { reload: 'sector' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sector.delete', {
            parent: 'sector',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sector/sector-delete-dialog.html',
                    controller: 'SectorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Sector', function(Sector) {
                            return Sector.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sector', null, { reload: 'sector' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
