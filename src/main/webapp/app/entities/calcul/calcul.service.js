(function() {
    'use strict';
    angular
        .module('opalitApp')
        .factory('Calcul', Calcul);

    Calcul.$inject = ['$resource'];

    function Calcul ($resource) {
        var resourceUrl =  'api/calculs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
