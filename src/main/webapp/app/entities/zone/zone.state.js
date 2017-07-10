(function() {
    'use strict';

    angular
        .module('opalitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('zone', {
            parent: 'entity',
            url: '/zone',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'opalitApp.zone.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/zone/zones.html',
                    controller: 'ZoneController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('zone');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('zone-detail', {
            parent: 'zone',
            url: '/zone/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'opalitApp.zone.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/zone/zone-detail.html',
                    controller: 'ZoneDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('zone');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Zone', function($stateParams, Zone) {
                    return Zone.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'zone',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('zone-detail.edit', {
            parent: 'zone-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zone/zone-dialog.html',
                    controller: 'ZoneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Zone', function(Zone) {
                            return Zone.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('zone.new', {
            parent: 'zone',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zone/zone-dialog.html',
                    controller: 'ZoneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('zone', null, { reload: 'zone' });
                }, function() {
                    $state.go('zone');
                });
            }]
        })
        .state('zone.edit', {
            parent: 'zone',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zone/zone-dialog.html',
                    controller: 'ZoneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Zone', function(Zone) {
                            return Zone.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('zone', null, { reload: 'zone' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('zone.delete', {
            parent: 'zone',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zone/zone-delete-dialog.html',
                    controller: 'ZoneDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Zone', function(Zone) {
                            return Zone.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('zone', null, { reload: 'zone' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
