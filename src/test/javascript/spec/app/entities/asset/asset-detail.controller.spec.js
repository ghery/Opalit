'use strict';

describe('Controller Tests', function() {

    describe('Asset Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAsset, MockMarket, MockCurrency, MockCountry, MockCity, MockSector;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAsset = jasmine.createSpy('MockAsset');
            MockMarket = jasmine.createSpy('MockMarket');
            MockCurrency = jasmine.createSpy('MockCurrency');
            MockCountry = jasmine.createSpy('MockCountry');
            MockCity = jasmine.createSpy('MockCity');
            MockSector = jasmine.createSpy('MockSector');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Asset': MockAsset,
                'Market': MockMarket,
                'Currency': MockCurrency,
                'Country': MockCountry,
                'City': MockCity,
                'Sector': MockSector
            };
            createController = function() {
                $injector.get('$controller')("AssetDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'opalitApp:assetUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
