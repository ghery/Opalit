(function() {
    'use strict';

    angular
        .module('opalitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('h-book', {
            parent: 'entity',
            url: '/h-book',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'opalitApp.hBook.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/h-book/h-books.html',
                    controller: 'HBookController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('hBook');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('h-book-detail', {
            parent: 'h-book',
            url: '/h-book/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'opalitApp.hBook.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/h-book/h-book-detail.html',
                    controller: 'HBookDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('hBook');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'HBook', function($stateParams, HBook) {
                    return HBook.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'h-book',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('h-book-detail.edit', {
            parent: 'h-book-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/h-book/h-book-dialog.html',
                    controller: 'HBookDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HBook', function(HBook) {
                            return HBook.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('h-book.new', {
            parent: 'h-book',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/h-book/h-book-dialog.html',
                    controller: 'HBookDialogController',
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
                    $state.go('h-book', null, { reload: 'h-book' });
                }, function() {
                    $state.go('h-book');
                });
            }]
        })
        .state('h-book.edit', {
            parent: 'h-book',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/h-book/h-book-dialog.html',
                    controller: 'HBookDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HBook', function(HBook) {
                            return HBook.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('h-book', null, { reload: 'h-book' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('h-book.delete', {
            parent: 'h-book',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/h-book/h-book-delete-dialog.html',
                    controller: 'HBookDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['HBook', function(HBook) {
                            return HBook.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('h-book', null, { reload: 'h-book' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
