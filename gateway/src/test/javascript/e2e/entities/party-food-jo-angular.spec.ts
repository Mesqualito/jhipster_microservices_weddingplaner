import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('PartyFood e2e test', () => {

    let navBarPage: NavBarPage;
    let partyFoodDialogPage: PartyFoodDialogPage;
    let partyFoodComponentsPage: PartyFoodComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load PartyFoods', () => {
        navBarPage.goToEntity('party-food-jo-angular');
        partyFoodComponentsPage = new PartyFoodComponentsPage();
        expect(partyFoodComponentsPage.getTitle())
            .toMatch(/weddingplanerApp.partyFood.home.title/);

    });

    it('should load create PartyFood dialog', () => {
        partyFoodComponentsPage.clickOnCreateButton();
        partyFoodDialogPage = new PartyFoodDialogPage();
        expect(partyFoodDialogPage.getModalTitle())
            .toMatch(/weddingplanerApp.partyFood.home.createOrEditLabel/);
        partyFoodDialogPage.close();
    });

    it('should create and save PartyFoods', () => {
        partyFoodComponentsPage.clickOnCreateButton();
        partyFoodDialogPage.setFoodNameInput('foodName');
        expect(partyFoodDialogPage.getFoodNameInput()).toMatch('foodName');
        partyFoodDialogPage.setFoodShortDescriptionInput('foodShortDescription');
        expect(partyFoodDialogPage.getFoodShortDescriptionInput()).toMatch('foodShortDescription');
        partyFoodDialogPage.setFoodLongDescriptionInput('foodLongDescription');
        expect(partyFoodDialogPage.getFoodLongDescriptionInput()).toMatch('foodLongDescription');
        partyFoodDialogPage.languageSelectLastOption();
        partyFoodDialogPage.setFoodQuantityPersonsInput('5');
        expect(partyFoodDialogPage.getFoodQuantityPersonsInput()).toMatch('5');
        partyFoodDialogPage.setFoodBestServeTimeInput('2000-12-31');
        expect(partyFoodDialogPage.getFoodBestServeTimeInput()).toMatch('2000-12-31');
        partyFoodDialogPage.getFoodProposalAcceptedInput().isSelected().then((selected) => {
            if (selected) {
                partyFoodDialogPage.getFoodProposalAcceptedInput().click();
                expect(partyFoodDialogPage.getFoodProposalAcceptedInput().isSelected()).toBeFalsy();
            } else {
                partyFoodDialogPage.getFoodProposalAcceptedInput().click();
                expect(partyFoodDialogPage.getFoodProposalAcceptedInput().isSelected()).toBeTruthy();
            }
        });
        partyFoodDialogPage.setFoodProposalAcceptTimeInput(12310020012301);
        expect(partyFoodDialogPage.getFoodProposalAcceptTimeInput()).toMatch('2001-12-31T02:30');
        partyFoodDialogPage.setWeightInput('5');
        expect(partyFoodDialogPage.getWeightInput()).toMatch('5');
        partyFoodDialogPage.ownerSelectLastOption();
        // partyFoodDialogPage.memberUserSelectLastOption();
        partyFoodDialogPage.save();
        expect(partyFoodDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PartyFoodComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-party-food-jo-angular div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PartyFoodDialogPage {
    modalTitle = element(by.css('h4#myPartyFoodLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    foodNameInput = element(by.css('input#field_foodName'));
    foodShortDescriptionInput = element(by.css('input#field_foodShortDescription'));
    foodLongDescriptionInput = element(by.css('textarea#field_foodLongDescription'));
    languageSelect = element(by.css('select#field_language'));
    foodQuantityPersonsInput = element(by.css('input#field_foodQuantityPersons'));
    foodBestServeTimeInput = element(by.css('input#field_foodBestServeTime'));
    foodProposalAcceptedInput = element(by.css('input#field_foodProposalAccepted'));
    foodProposalAcceptTimeInput = element(by.css('input#field_foodProposalAcceptTime'));
    weightInput = element(by.css('input#field_weight'));
    ownerSelect = element(by.css('select#field_owner'));
    memberUserSelect = element(by.css('select#field_memberUser'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setFoodNameInput = function(foodName) {
        this.foodNameInput.sendKeys(foodName);
    };

    getFoodNameInput = function() {
        return this.foodNameInput.getAttribute('value');
    };

    setFoodShortDescriptionInput = function(foodShortDescription) {
        this.foodShortDescriptionInput.sendKeys(foodShortDescription);
    };

    getFoodShortDescriptionInput = function() {
        return this.foodShortDescriptionInput.getAttribute('value');
    };

    setFoodLongDescriptionInput = function(foodLongDescription) {
        this.foodLongDescriptionInput.sendKeys(foodLongDescription);
    };

    getFoodLongDescriptionInput = function() {
        return this.foodLongDescriptionInput.getAttribute('value');
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
    setFoodQuantityPersonsInput = function(foodQuantityPersons) {
        this.foodQuantityPersonsInput.sendKeys(foodQuantityPersons);
    };

    getFoodQuantityPersonsInput = function() {
        return this.foodQuantityPersonsInput.getAttribute('value');
    };

    setFoodBestServeTimeInput = function(foodBestServeTime) {
        this.foodBestServeTimeInput.sendKeys(foodBestServeTime);
    };

    getFoodBestServeTimeInput = function() {
        return this.foodBestServeTimeInput.getAttribute('value');
    };

    getFoodProposalAcceptedInput = function() {
        return this.foodProposalAcceptedInput;
    };
    setFoodProposalAcceptTimeInput = function(foodProposalAcceptTime) {
        this.foodProposalAcceptTimeInput.sendKeys(foodProposalAcceptTime);
    };

    getFoodProposalAcceptTimeInput = function() {
        return this.foodProposalAcceptTimeInput.getAttribute('value');
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

    memberUserSelectLastOption = function() {
        this.memberUserSelect.all(by.tagName('option')).last().click();
    };

    memberUserSelectOption = function(option) {
        this.memberUserSelect.sendKeys(option);
    };

    getMemberUserSelect = function() {
        return this.memberUserSelect;
    };

    getMemberUserSelectedOption = function() {
        return this.memberUserSelect.element(by.css('option:checked')).getText();
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
