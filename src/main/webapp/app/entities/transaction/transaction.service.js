(function() {
    'use strict';
    angular
        .module('opalitApp')
        .factory('Transaction', Transaction);

    Transaction.$inject = ['$resource', 'DateUtils'];

    function Transaction ($resource, DateUtils) {
        var resourceUrl =  'api/transactions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.tradeDate = DateUtils.convertLocalDateFromServer(data.tradeDate);
                        data.settlementDate = DateUtils.convertLocalDateFromServer(data.settlementDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.tradeDate = DateUtils.convertLocalDateToServer(copy.tradeDate);
                    copy.settlementDate = DateUtils.convertLocalDateToServer(copy.settlementDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.tradeDate = DateUtils.convertLocalDateToServer(copy.tradeDate);
                    copy.settlementDate = DateUtils.convertLocalDateToServer(copy.settlementDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
