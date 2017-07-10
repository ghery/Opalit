(function() {
    'use strict';
    angular
        .module('opalitApp')
        .factory('Market', Market);

    Market.$inject = ['$resource'];

    function Market ($resource) {
        var resourceUrl =  'api/markets/:id';

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
