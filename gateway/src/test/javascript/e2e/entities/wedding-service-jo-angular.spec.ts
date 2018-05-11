import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('WeddingService e2e test', () => {

    let navBarPage: NavBarPage;
    let weddingServiceDialogPage: WeddingServiceDialogPage;
    let weddingServiceComponentsPage: WeddingServiceComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load WeddingServices', () => {
        navBarPage.goToEntity('wedding-service-jo-angular');
        weddingServiceComponentsPage = new WeddingServiceComponentsPage();
        expect(weddingServiceComponentsPage.getTitle())
            .toMatch(/weddingplanerApp.weddingService.home.title/);

    });

    it('should load create WeddingService dialog', () => {
        weddingServiceComponentsPage.clickOnCreateButton();
        weddingServiceDialogPage = new WeddingServiceDialogPage();
        expect(weddingServiceDialogPage.getModalTitle())
            .toMatch(/weddingplanerApp.weddingService.home.createOrEditLabel/);
        weddingServiceDialogPage.close();
    });

    it('should create and save WeddingServices', () => {
        weddingServiceComponentsPage.clickOnCreateButton();
        weddingServiceDialogPage.setFirstNameInput('firstName');
        expect(weddingServiceDialogPage.getFirstNameInput()).toMatch('firstName');
        weddingServiceDialogPage.setLastNameInput('lastName');
        expect(weddingServiceDialogPage.getLastNameInput()).toMatch('lastName');
        weddingServiceDialogPage.setEmailInput('email');
        expect(weddingServiceDialogPage.getEmailInput()).toMatch('email');
        weddingServiceDialogPage.setServiceCommittedDateInput(12310020012301);
        expect(weddingServiceDialogPage.getServiceCommittedDateInput()).toMatch('2001-12-31T02:30');
        weddingServiceDialogPage.businessAddressSelectLastOption();
        weddingServiceDialogPage.privateAddressSelectLastOption();
        weddingServiceDialogPage.appUserSelectLastOption();
        weddingServiceDialogPage.save();
        expect(weddingServiceDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class WeddingServiceComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-wedding-service-jo-angular div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class WeddingServiceDialogPage {
    modalTitle = element(by.css('h4#myWeddingServiceLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    firstNameInput = element(by.css('input#field_firstName'));
    lastNameInput = element(by.css('input#field_lastName'));
    emailInput = element(by.css('input#field_email'));
    serviceCommittedDateInput = element(by.css('input#field_serviceCommittedDate'));
    businessAddressSelect = element(by.css('select#field_businessAddress'));
    privateAddressSelect = element(by.css('select#field_privateAddress'));
    appUserSelect = element(by.css('select#field_appUser'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setFirstNameInput = function(firstName) {
        this.firstNameInput.sendKeys(firstName);
    };

    getFirstNameInput = function() {
        return this.firstNameInput.getAttribute('value');
    };

    setLastNameInput = function(lastName) {
        this.lastNameInput.sendKeys(lastName);
    };

    getLastNameInput = function() {
        return this.lastNameInput.getAttribute('value');
    };

    setEmailInput = function(email) {
        this.emailInput.sendKeys(email);
    };

    getEmailInput = function() {
        return this.emailInput.getAttribute('value');
    };

    setServiceCommittedDateInput = function(serviceCommittedDate) {
        this.serviceCommittedDateInput.sendKeys(serviceCommittedDate);
    };

    getServiceCommittedDateInput = function() {
        return this.serviceCommittedDateInput.getAttribute('value');
    };

    businessAddressSelectLastOption = function() {
        this.businessAddressSelect.all(by.tagName('option')).last().click();
    };

    businessAddressSelectOption = function(option) {
        this.businessAddressSelect.sendKeys(option);
    };

    getBusinessAddressSelect = function() {
        return this.businessAddressSelect;
    };

    getBusinessAddressSelectedOption = function() {
        return this.businessAddressSelect.element(by.css('option:checked')).getText();
    };

    privateAddressSelectLastOption = function() {
        this.privateAddressSelect.all(by.tagName('option')).last().click();
    };

    privateAddressSelectOption = function(option) {
        this.privateAddressSelect.sendKeys(option);
    };

    getPrivateAddressSelect = function() {
        return this.privateAddressSelect;
    };

    getPrivateAddressSelectedOption = function() {
        return this.privateAddressSelect.element(by.css('option:checked')).getText();
    };

    appUserSelectLastOption = function() {
        this.appUserSelect.all(by.tagName('option')).last().click();
    };

    appUserSelectOption = function(option) {
        this.appUserSelect.sendKeys(option);
    };

    getAppUserSelect = function() {
        return this.appUserSelect;
    };

    getAppUserSelectedOption = function() {
        return this.appUserSelect.element(by.css('option:checked')).getText();
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
