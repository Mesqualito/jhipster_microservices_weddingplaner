import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('WeddingHost e2e test', () => {

    let navBarPage: NavBarPage;
    let weddingHostDialogPage: WeddingHostDialogPage;
    let weddingHostComponentsPage: WeddingHostComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load WeddingHosts', () => {
        navBarPage.goToEntity('wedding-host-jo-angular');
        weddingHostComponentsPage = new WeddingHostComponentsPage();
        expect(weddingHostComponentsPage.getTitle())
            .toMatch(/weddingplanerApp.weddingHost.home.title/);

    });

    it('should load create WeddingHost dialog', () => {
        weddingHostComponentsPage.clickOnCreateButton();
        weddingHostDialogPage = new WeddingHostDialogPage();
        expect(weddingHostDialogPage.getModalTitle())
            .toMatch(/weddingplanerApp.weddingHost.home.createOrEditLabel/);
        weddingHostDialogPage.close();
    });

    it('should create and save WeddingHosts', () => {
        weddingHostComponentsPage.clickOnCreateButton();
        weddingHostDialogPage.setFirstNameInput('firstName');
        expect(weddingHostDialogPage.getFirstNameInput()).toMatch('firstName');
        weddingHostDialogPage.setLastNameInput('lastName');
        expect(weddingHostDialogPage.getLastNameInput()).toMatch('lastName');
        weddingHostDialogPage.setEmailInput('email');
        expect(weddingHostDialogPage.getEmailInput()).toMatch('email');
        weddingHostDialogPage.appUserSelectLastOption();
        weddingHostDialogPage.foodProposalAcceptHostSelectLastOption();
        weddingHostDialogPage.save();
        expect(weddingHostDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class WeddingHostComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-wedding-host-jo-angular div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class WeddingHostDialogPage {
    modalTitle = element(by.css('h4#myWeddingHostLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    firstNameInput = element(by.css('input#field_firstName'));
    lastNameInput = element(by.css('input#field_lastName'));
    emailInput = element(by.css('input#field_email'));
    appUserSelect = element(by.css('select#field_appUser'));
    foodProposalAcceptHostSelect = element(by.css('select#field_foodProposalAcceptHost'));

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

    foodProposalAcceptHostSelectLastOption = function() {
        this.foodProposalAcceptHostSelect.all(by.tagName('option')).last().click();
    };

    foodProposalAcceptHostSelectOption = function(option) {
        this.foodProposalAcceptHostSelect.sendKeys(option);
    };

    getFoodProposalAcceptHostSelect = function() {
        return this.foodProposalAcceptHostSelect;
    };

    getFoodProposalAcceptHostSelectedOption = function() {
        return this.foodProposalAcceptHostSelect.element(by.css('option:checked')).getText();
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
