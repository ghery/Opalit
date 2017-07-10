(function() {
    'use strict';
    angular
        .module('opalitApp')
        .factory('Preference', Preference);

    Preference.$inject = ['$resource'];

    function Preference ($resource) {
        var resourceUrl =  'api/preferences/:id';

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
