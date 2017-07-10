(function() {
    'use strict';
    angular
        .module('opalitApp')
        .factory('HBook', HBook);

    HBook.$inject = ['$resource', 'DateUtils'];

    function HBook ($resource, DateUtils) {
        var resourceUrl =  'api/h-books/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.day = DateUtils.convertLocalDateFromServer(data.day);
                        data.time = DateUtils.convertDateTimeFromServer(data.time);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.day = DateUtils.convertLocalDateToServer(copy.day);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.day = DateUtils.convertLocalDateToServer(copy.day);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
