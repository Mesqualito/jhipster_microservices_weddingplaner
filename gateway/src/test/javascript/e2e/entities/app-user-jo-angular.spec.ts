import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('AppUser e2e test', () => {

    let navBarPage: NavBarPage;
    let appUserDialogPage: AppUserDialogPage;
    let appUserComponentsPage: AppUserComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load AppUsers', () => {
        navBarPage.goToEntity('app-user-jo-angular');
        appUserComponentsPage = new AppUserComponentsPage();
        expect(appUserComponentsPage.getTitle())
            .toMatch(/weddingplanerApp.appUser.home.title/);

    });

    it('should load create AppUser dialog', () => {
        appUserComponentsPage.clickOnCreateButton();
        appUserDialogPage = new AppUserDialogPage();
        expect(appUserDialogPage.getModalTitle())
            .toMatch(/weddingplanerApp.appUser.home.createOrEditLabel/);
        appUserDialogPage.close();
    });

   /* it('should create and save AppUsers', () => {
        appUserComponentsPage.clickOnCreateButton();
        appUserDialogPage.setAppUserAccountInput('appUserAccount');
        expect(appUserDialogPage.getAppUserAccountInput()).toMatch('appUserAccount');
        appUserDialogPage.setAppUserPasswordInput('appUserPassword');
        expect(appUserDialogPage.getAppUserPasswordInput()).toMatch('appUserPassword');
        appUserDialogPage.languageSelectLastOption();
        appUserDialogPage.save();
        expect(appUserDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class AppUserComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-app-user-jo-angular div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AppUserDialogPage {
    modalTitle = element(by.css('h4#myAppUserLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    appUserAccountInput = element(by.css('input#field_appUserAccount'));
    appUserPasswordInput = element(by.css('input#field_appUserPassword'));
    languageSelect = element(by.css('select#field_language'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setAppUserAccountInput = function(appUserAccount) {
        this.appUserAccountInput.sendKeys(appUserAccount);
    };

    getAppUserAccountInput = function() {
        return this.appUserAccountInput.getAttribute('value');
    };

    setAppUserPasswordInput = function(appUserPassword) {
        this.appUserPasswordInput.sendKeys(appUserPassword);
    };

    getAppUserPasswordInput = function() {
        return this.appUserPasswordInput.getAttribute('value');
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
