(function() {
    'use strict';
    angular
        .module('opalitApp')
        .factory('Asset', Asset);

    Asset.$inject = ['$resource', 'DateUtils'];

    function Asset ($resource, DateUtils) {
        var resourceUrl =  'api/assets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.gdate = DateUtils.convertLocalDateFromServer(data.gdate);
                        data.gdateb = DateUtils.convertLocalDateFromServer(data.gdateb);
                        data.gdatec = DateUtils.convertLocalDateFromServer(data.gdatec);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.gdate = DateUtils.convertLocalDateToServer(copy.gdate);
                    copy.gdateb = DateUtils.convertLocalDateToServer(copy.gdateb);
                    copy.gdatec = DateUtils.convertLocalDateToServer(copy.gdatec);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.gdate = DateUtils.convertLocalDateToServer(copy.gdate);
                    copy.gdateb = DateUtils.convertLocalDateToServer(copy.gdateb);
                    copy.gdatec = DateUtils.convertLocalDateToServer(copy.gdatec);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
