import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Address e2e test', () => {

    let navBarPage: NavBarPage;
    let addressDialogPage: AddressDialogPage;
    let addressComponentsPage: AddressComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Addresses', () => {
        navBarPage.goToEntity('address-jo-angular');
        addressComponentsPage = new AddressComponentsPage();
        expect(addressComponentsPage.getTitle())
            .toMatch(/weddingplanerApp.address.home.title/);

    });

    it('should load create Address dialog', () => {
        addressComponentsPage.clickOnCreateButton();
        addressDialogPage = new AddressDialogPage();
        expect(addressDialogPage.getModalTitle())
            .toMatch(/weddingplanerApp.address.home.createOrEditLabel/);
        addressDialogPage.close();
    });

   /* it('should create and save Addresses', () => {
        addressComponentsPage.clickOnCreateButton();
        addressDialogPage.addressTypeSelectLastOption();
        addressDialogPage.setAddressLine1Input('addressLine1');
        expect(addressDialogPage.getAddressLine1Input()).toMatch('addressLine1');
        addressDialogPage.setAddressLine2Input('addressLine2');
        expect(addressDialogPage.getAddressLine2Input()).toMatch('addressLine2');
        addressDialogPage.setCityInput('city');
        expect(addressDialogPage.getCityInput()).toMatch('city');
        addressDialogPage.setZipCodeInput('zipCode');
        expect(addressDialogPage.getZipCodeInput()).toMatch('zipCode');
        addressDialogPage.setStateInput('state');
        expect(addressDialogPage.getStateInput()).toMatch('state');
        addressDialogPage.setCountryInput('country');
        expect(addressDialogPage.getCountryInput()).toMatch('country');
        addressDialogPage.setBusinessPhoneNrInput('businessPhoneNr');
        expect(addressDialogPage.getBusinessPhoneNrInput()).toMatch('businessPhoneNr');
        addressDialogPage.setPrivatePhoneNrInput('privatePhoneNr');
        expect(addressDialogPage.getPrivatePhoneNrInput()).toMatch('privatePhoneNr');
        addressDialogPage.setMobilePhoneNrInput('mobilePhoneNr');
        expect(addressDialogPage.getMobilePhoneNrInput()).toMatch('mobilePhoneNr');
        addressDialogPage.save();
        expect(addressDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class AddressComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-address-jo-angular div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AddressDialogPage {
    modalTitle = element(by.css('h4#myAddressLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    addressTypeSelect = element(by.css('select#field_addressType'));
    addressLine1Input = element(by.css('input#field_addressLine1'));
    addressLine2Input = element(by.css('input#field_addressLine2'));
    cityInput = element(by.css('input#field_city'));
    zipCodeInput = element(by.css('input#field_zipCode'));
    stateInput = element(by.css('input#field_state'));
    countryInput = element(by.css('input#field_country'));
    businessPhoneNrInput = element(by.css('input#field_businessPhoneNr'));
    privatePhoneNrInput = element(by.css('input#field_privatePhoneNr'));
    mobilePhoneNrInput = element(by.css('input#field_mobilePhoneNr'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setAddressTypeSelect = function(addressType) {
        this.addressTypeSelect.sendKeys(addressType);
    };

    getAddressTypeSelect = function() {
        return this.addressTypeSelect.element(by.css('option:checked')).getText();
    };

    addressTypeSelectLastOption = function() {
        this.addressTypeSelect.all(by.tagName('option')).last().click();
    };
    setAddressLine1Input = function(addressLine1) {
        this.addressLine1Input.sendKeys(addressLine1);
    };

    getAddressLine1Input = function() {
        return this.addressLine1Input.getAttribute('value');
    };

    setAddressLine2Input = function(addressLine2) {
        this.addressLine2Input.sendKeys(addressLine2);
    };

    getAddressLine2Input = function() {
        return this.addressLine2Input.getAttribute('value');
    };

    setCityInput = function(city) {
        this.cityInput.sendKeys(city);
    };

    getCityInput = function() {
        return this.cityInput.getAttribute('value');
    };

    setZipCodeInput = function(zipCode) {
        this.zipCodeInput.sendKeys(zipCode);
    };

    getZipCodeInput = function() {
        return this.zipCodeInput.getAttribute('value');
    };

    setStateInput = function(state) {
        this.stateInput.sendKeys(state);
    };

    getStateInput = function() {
        return this.stateInput.getAttribute('value');
    };

    setCountryInput = function(country) {
        this.countryInput.sendKeys(country);
    };

    getCountryInput = function() {
        return this.countryInput.getAttribute('value');
    };

    setBusinessPhoneNrInput = function(businessPhoneNr) {
        this.businessPhoneNrInput.sendKeys(businessPhoneNr);
    };

    getBusinessPhoneNrInput = function() {
        return this.businessPhoneNrInput.getAttribute('value');
    };

    setPrivatePhoneNrInput = function(privatePhoneNr) {
        this.privatePhoneNrInput.sendKeys(privatePhoneNr);
    };

    getPrivatePhoneNrInput = function() {
        return this.privatePhoneNrInput.getAttribute('value');
    };

    setMobilePhoneNrInput = function(mobilePhoneNr) {
        this.mobilePhoneNrInput.sendKeys(mobilePhoneNr);
    };

    getMobilePhoneNrInput = function() {
        return this.mobilePhoneNrInput.getAttribute('value');
    };

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
