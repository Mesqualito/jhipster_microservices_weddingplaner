import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('WeddingGuest e2e test', () => {

    let navBarPage: NavBarPage;
    let weddingGuestDialogPage: WeddingGuestDialogPage;
    let weddingGuestComponentsPage: WeddingGuestComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load WeddingGuests', () => {
        navBarPage.goToEntity('wedding-guest-jo-angular');
        weddingGuestComponentsPage = new WeddingGuestComponentsPage();
        expect(weddingGuestComponentsPage.getTitle())
            .toMatch(/weddingplanerApp.weddingGuest.home.title/);

    });

    it('should load create WeddingGuest dialog', () => {
        weddingGuestComponentsPage.clickOnCreateButton();
        weddingGuestDialogPage = new WeddingGuestDialogPage();
        expect(weddingGuestDialogPage.getModalTitle())
            .toMatch(/weddingplanerApp.weddingGuest.home.createOrEditLabel/);
        weddingGuestDialogPage.close();
    });

    it('should create and save WeddingGuests', () => {
        weddingGuestComponentsPage.clickOnCreateButton();
        weddingGuestDialogPage.setFirstNameInput('firstName');
        expect(weddingGuestDialogPage.getFirstNameInput()).toMatch('firstName');
        weddingGuestDialogPage.setLastNameInput('lastName');
        expect(weddingGuestDialogPage.getLastNameInput()).toMatch('lastName');
        weddingGuestDialogPage.setEmailInput('email');
        expect(weddingGuestDialogPage.getEmailInput()).toMatch('email');
        weddingGuestDialogPage.setGuestInvitationDateInput(12310020012301);
        expect(weddingGuestDialogPage.getGuestInvitationDateInput()).toMatch('2001-12-31T02:30');
        weddingGuestDialogPage.getGuestCommittedInput().isSelected().then((selected) => {
            if (selected) {
                weddingGuestDialogPage.getGuestCommittedInput().click();
                expect(weddingGuestDialogPage.getGuestCommittedInput().isSelected()).toBeFalsy();
            } else {
                weddingGuestDialogPage.getGuestCommittedInput().click();
                expect(weddingGuestDialogPage.getGuestCommittedInput().isSelected()).toBeTruthy();
            }
        });
        weddingGuestDialogPage.setGuestRsvpDateInput(12310020012301);
        expect(weddingGuestDialogPage.getGuestRsvpDateInput()).toMatch('2001-12-31T02:30');
        weddingGuestDialogPage.privateAddressSelectLastOption();
        weddingGuestDialogPage.businessAddressSelectLastOption();
        weddingGuestDialogPage.appUserSelectLastOption();
        weddingGuestDialogPage.save();
        expect(weddingGuestDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class WeddingGuestComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-wedding-guest-jo-angular div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class WeddingGuestDialogPage {
    modalTitle = element(by.css('h4#myWeddingGuestLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    firstNameInput = element(by.css('input#field_firstName'));
    lastNameInput = element(by.css('input#field_lastName'));
    emailInput = element(by.css('input#field_email'));
    guestInvitationDateInput = element(by.css('input#field_guestInvitationDate'));
    guestCommittedInput = element(by.css('input#field_guestCommitted'));
    guestRsvpDateInput = element(by.css('input#field_guestRsvpDate'));
    privateAddressSelect = element(by.css('select#field_privateAddress'));
    businessAddressSelect = element(by.css('select#field_businessAddress'));
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

    setGuestInvitationDateInput = function(guestInvitationDate) {
        this.guestInvitationDateInput.sendKeys(guestInvitationDate);
    };

    getGuestInvitationDateInput = function() {
        return this.guestInvitationDateInput.getAttribute('value');
    };

    getGuestCommittedInput = function() {
        return this.guestCommittedInput;
    };
    setGuestRsvpDateInput = function(guestRsvpDate) {
        this.guestRsvpDateInput.sendKeys(guestRsvpDate);
    };

    getGuestRsvpDateInput = function() {
        return this.guestRsvpDateInput.getAttribute('value');
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
