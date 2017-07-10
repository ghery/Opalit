'use strict';

describe('Controller Tests', function() {

    describe('Transaction Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTransaction, MockBook, MockAsset, MockCurrency, MockPeople;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTransaction = jasmine.createSpy('MockTransaction');
            MockBook = jasmine.createSpy('MockBook');
            MockAsset = jasmine.createSpy('MockAsset');
            MockCurrency = jasmine.createSpy('MockCurrency');
            MockPeople = jasmine.createSpy('MockPeople');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Transaction': MockTransaction,
                'Book': MockBook,
                'Asset': MockAsset,
                'Currency': MockCurrency,
                'People': MockPeople
            };
            createController = function() {
                $injector.get('$controller')("TransactionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'opalitApp:transactionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
