(function() {
    'use strict';

    angular
        .module('opalitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('h-asset', {
            parent: 'entity',
            url: '/h-asset',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'opalitApp.hAsset.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/h-asset/h-assets.html',
                    controller: 'HAssetController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('hAsset');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('h-asset-detail', {
            parent: 'h-asset',
            url: '/h-asset/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'opalitApp.hAsset.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/h-asset/h-asset-detail.html',
                    controller: 'HAssetDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('hAsset');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'HAsset', function($stateParams, HAsset) {
                    return HAsset.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'h-asset',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('h-asset-detail.edit', {
            parent: 'h-asset-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/h-asset/h-asset-dialog.html',
                    controller: 'HAssetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HAsset', function(HAsset) {
                            return HAsset.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('h-asset.new', {
            parent: 'h-asset',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/h-asset/h-asset-dialog.html',
                    controller: 'HAssetDialogController',
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
                    $state.go('h-asset', null, { reload: 'h-asset' });
                }, function() {
                    $state.go('h-asset');
                });
            }]
        })
        .state('h-asset.edit', {
            parent: 'h-asset',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/h-asset/h-asset-dialog.html',
                    controller: 'HAssetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HAsset', function(HAsset) {
                            return HAsset.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('h-asset', null, { reload: 'h-asset' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('h-asset.delete', {
            parent: 'h-asset',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/h-asset/h-asset-delete-dialog.html',
                    controller: 'HAssetDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['HAsset', function(HAsset) {
                            return HAsset.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('h-asset', null, { reload: 'h-asset' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
