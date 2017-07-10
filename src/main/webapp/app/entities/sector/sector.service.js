(function() {
    'use strict';
    angular
        .module('opalitApp')
        .factory('Sector', Sector);

    Sector.$inject = ['$resource'];

    function Sector ($resource) {
        var resourceUrl =  'api/sectors/:id';

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
