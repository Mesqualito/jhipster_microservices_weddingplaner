import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Message e2e test', () => {

    let navBarPage: NavBarPage;
    let messageDialogPage: MessageDialogPage;
    let messageComponentsPage: MessageComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Messages', () => {
        navBarPage.goToEntity('message-jo-angular');
        messageComponentsPage = new MessageComponentsPage();
        expect(messageComponentsPage.getTitle())
            .toMatch(/weddingplanerApp.message.home.title/);

    });

    it('should load create Message dialog', () => {
        messageComponentsPage.clickOnCreateButton();
        messageDialogPage = new MessageDialogPage();
        expect(messageDialogPage.getModalTitle())
            .toMatch(/weddingplanerApp.message.home.createOrEditLabel/);
        messageDialogPage.close();
    });

   /* it('should create and save Messages', () => {
        messageComponentsPage.clickOnCreateButton();
        messageDialogPage.setMessageShortDescriptionInput('messageShortDescription');
        expect(messageDialogPage.getMessageShortDescriptionInput()).toMatch('messageShortDescription');
        messageDialogPage.setMessageInitTimeInput(12310020012301);
        expect(messageDialogPage.getMessageInitTimeInput()).toMatch('2001-12-31T02:30');
        messageDialogPage.setMessageTextInput('messageText');
        expect(messageDialogPage.getMessageTextInput()).toMatch('messageText');
        messageDialogPage.languageSelectLastOption();
        messageDialogPage.setMessageValidFromInput('2000-12-31');
        expect(messageDialogPage.getMessageValidFromInput()).toMatch('2000-12-31');
        messageDialogPage.setMessageValidUntilInput('2000-12-31');
        expect(messageDialogPage.getMessageValidUntilInput()).toMatch('2000-12-31');
        messageDialogPage.setWeightInput('5');
        expect(messageDialogPage.getWeightInput()).toMatch('5');
        messageDialogPage.ownerSelectLastOption();
        // messageDialogPage.messageRecipientSelectLastOption();
        messageDialogPage.save();
        expect(messageDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MessageComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-message-jo-angular div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MessageDialogPage {
    modalTitle = element(by.css('h4#myMessageLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    messageShortDescriptionInput = element(by.css('input#field_messageShortDescription'));
    messageInitTimeInput = element(by.css('input#field_messageInitTime'));
    messageTextInput = element(by.css('textarea#field_messageText'));
    languageSelect = element(by.css('select#field_language'));
    messageValidFromInput = element(by.css('input#field_messageValidFrom'));
    messageValidUntilInput = element(by.css('input#field_messageValidUntil'));
    weightInput = element(by.css('input#field_weight'));
    ownerSelect = element(by.css('select#field_owner'));
    messageRecipientSelect = element(by.css('select#field_messageRecipient'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setMessageShortDescriptionInput = function(messageShortDescription) {
        this.messageShortDescriptionInput.sendKeys(messageShortDescription);
    };

    getMessageShortDescriptionInput = function() {
        return this.messageShortDescriptionInput.getAttribute('value');
    };

    setMessageInitTimeInput = function(messageInitTime) {
        this.messageInitTimeInput.sendKeys(messageInitTime);
    };

    getMessageInitTimeInput = function() {
        return this.messageInitTimeInput.getAttribute('value');
    };

    setMessageTextInput = function(messageText) {
        this.messageTextInput.sendKeys(messageText);
    };

    getMessageTextInput = function() {
        return this.messageTextInput.getAttribute('value');
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
    setMessageValidFromInput = function(messageValidFrom) {
        this.messageValidFromInput.sendKeys(messageValidFrom);
    };

    getMessageValidFromInput = function() {
        return this.messageValidFromInput.getAttribute('value');
    };

    setMessageValidUntilInput = function(messageValidUntil) {
        this.messageValidUntilInput.sendKeys(messageValidUntil);
    };

    getMessageValidUntilInput = function() {
        return this.messageValidUntilInput.getAttribute('value');
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

    messageRecipientSelectLastOption = function() {
        this.messageRecipientSelect.all(by.tagName('option')).last().click();
    };

    messageRecipientSelectOption = function(option) {
        this.messageRecipientSelect.sendKeys(option);
    };

    getMessageRecipientSelect = function() {
        return this.messageRecipientSelect;
    };

    getMessageRecipientSelectedOption = function() {
        return this.messageRecipientSelect.element(by.css('option:checked')).getText();
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
