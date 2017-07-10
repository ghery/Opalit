(function() {
    'use strict';

    angular
        .module('opalitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('people', {
            parent: 'entity',
            url: '/people',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'opalitApp.people.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/people/people.html',
                    controller: 'PeopleController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('people');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('people-detail', {
            parent: 'people',
            url: '/people/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'opalitApp.people.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/people/people-detail.html',
                    controller: 'PeopleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('people');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'People', function($stateParams, People) {
                    return People.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'people',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('people-detail.edit', {
            parent: 'people-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/people/people-dialog.html',
                    controller: 'PeopleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['People', function(People) {
                            return People.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('people.new', {
            parent: 'people',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/people/people-dialog.html',
                    controller: 'PeopleDialogController',
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
                    $state.go('people', null, { reload: 'people' });
                }, function() {
                    $state.go('people');
                });
            }]
        })
        .state('people.edit', {
            parent: 'people',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/people/people-dialog.html',
                    controller: 'PeopleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['People', function(People) {
                            return People.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('people', null, { reload: 'people' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('people.delete', {
            parent: 'people',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/people/people-delete-dialog.html',
                    controller: 'PeopleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['People', function(People) {
                            return People.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('people', null, { reload: 'people' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
