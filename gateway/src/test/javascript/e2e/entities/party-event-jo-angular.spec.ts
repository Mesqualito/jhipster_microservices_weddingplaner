import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('PartyEvent e2e test', () => {

    let navBarPage: NavBarPage;
    let partyEventDialogPage: PartyEventDialogPage;
    let partyEventComponentsPage: PartyEventComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load PartyEvents', () => {
        navBarPage.goToEntity('party-event-jo-angular');
        partyEventComponentsPage = new PartyEventComponentsPage();
        expect(partyEventComponentsPage.getTitle())
            .toMatch(/weddingplanerApp.partyEvent.home.title/);

    });

    it('should load create PartyEvent dialog', () => {
        partyEventComponentsPage.clickOnCreateButton();
        partyEventDialogPage = new PartyEventDialogPage();
        expect(partyEventDialogPage.getModalTitle())
            .toMatch(/weddingplanerApp.partyEvent.home.createOrEditLabel/);
        partyEventDialogPage.close();
    });

    it('should create and save PartyEvents', () => {
        partyEventComponentsPage.clickOnCreateButton();
        partyEventDialogPage.setEventNameInput('eventName');
        expect(partyEventDialogPage.getEventNameInput()).toMatch('eventName');
        partyEventDialogPage.setEventShortDescriptionInput('eventShortDescription');
        expect(partyEventDialogPage.getEventShortDescriptionInput()).toMatch('eventShortDescription');
        partyEventDialogPage.setEventLongDescriptionInput('eventLongDescription');
        expect(partyEventDialogPage.getEventLongDescriptionInput()).toMatch('eventLongDescription');
        partyEventDialogPage.languageSelectLastOption();
        partyEventDialogPage.setEventInitTimeInput(12310020012301);
        expect(partyEventDialogPage.getEventInitTimeInput()).toMatch('2001-12-31T02:30');
        partyEventDialogPage.setEventStartTimeInput('2000-12-31');
        expect(partyEventDialogPage.getEventStartTimeInput()).toMatch('2000-12-31');
        partyEventDialogPage.setEventEndTimeInput('2000-12-31');
        expect(partyEventDialogPage.getEventEndTimeInput()).toMatch('2000-12-31');
        partyEventDialogPage.setEventMaxPersonInput('5');
        expect(partyEventDialogPage.getEventMaxPersonInput()).toMatch('5');
        partyEventDialogPage.setWeightInput('5');
        expect(partyEventDialogPage.getWeightInput()).toMatch('5');
        partyEventDialogPage.ownerSelectLastOption();
        partyEventDialogPage.save();
        expect(partyEventDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PartyEventComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-party-event-jo-angular div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PartyEventDialogPage {
    modalTitle = element(by.css('h4#myPartyEventLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    eventNameInput = element(by.css('input#field_eventName'));
    eventShortDescriptionInput = element(by.css('input#field_eventShortDescription'));
    eventLongDescriptionInput = element(by.css('textarea#field_eventLongDescription'));
    languageSelect = element(by.css('select#field_language'));
    eventInitTimeInput = element(by.css('input#field_eventInitTime'));
    eventStartTimeInput = element(by.css('input#field_eventStartTime'));
    eventEndTimeInput = element(by.css('input#field_eventEndTime'));
    eventMaxPersonInput = element(by.css('input#field_eventMaxPerson'));
    weightInput = element(by.css('input#field_weight'));
    ownerSelect = element(by.css('select#field_owner'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setEventNameInput = function(eventName) {
        this.eventNameInput.sendKeys(eventName);
    };

    getEventNameInput = function() {
        return this.eventNameInput.getAttribute('value');
    };

    setEventShortDescriptionInput = function(eventShortDescription) {
        this.eventShortDescriptionInput.sendKeys(eventShortDescription);
    };

    getEventShortDescriptionInput = function() {
        return this.eventShortDescriptionInput.getAttribute('value');
    };

    setEventLongDescriptionInput = function(eventLongDescription) {
        this.eventLongDescriptionInput.sendKeys(eventLongDescription);
    };

    getEventLongDescriptionInput = function() {
        return this.eventLongDescriptionInput.getAttribute('value');
    };

    setLanguageSelect = function(language) {
        this.languageSelect.sendKeys(language);
    };

    getLanguageSelect = function() {
        return this.languageSelect.element(by.css('option:checked')).getText();
    };

    languageSelectLastOption = function() {
        this.languageSelect.all(by.tagName('option')).last().click();
    };
    setEventInitTimeInput = function(eventInitTime) {
        this.eventInitTimeInput.sendKeys(eventInitTime);
    };

    getEventInitTimeInput = function() {
        return this.eventInitTimeInput.getAttribute('value');
    };

    setEventStartTimeInput = function(eventStartTime) {
        this.eventStartTimeInput.sendKeys(eventStartTime);
    };

    getEventStartTimeInput = function() {
        return this.eventStartTimeInput.getAttribute('value');
    };

    setEventEndTimeInput = function(eventEndTime) {
        this.eventEndTimeInput.sendKeys(eventEndTime);
    };

    getEventEndTimeInput = function() {
        return this.eventEndTimeInput.getAttribute('value');
    };

    setEventMaxPersonInput = function(eventMaxPerson) {
        this.eventMaxPersonInput.sendKeys(eventMaxPerson);
    };

    getEventMaxPersonInput = function() {
        return this.eventMaxPersonInput.getAttribute('value');
    };

    setWeightInput = function(weight) {
        this.weightInput.sendKeys(weight);
    };

    getWeightInput = function() {
        return this.weightInput.getAttribute('value');
    };

    ownerSelectLastOption = function() {
        this.ownerSelect.all(by.tagName('option')).last().click();
    };

    ownerSelectOption = function(option) {
        this.ownerSelect.sendKeys(option);
    };

    getOwnerSelect = function() {
        return this.ownerSelect;
    };

    getOwnerSelectedOption = function() {
        return this.ownerSelect.element(by.css('option:checked')).getText();
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
