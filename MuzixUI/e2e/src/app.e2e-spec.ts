import { AppPage } from './app.po';
import { protractor } from 'protractor/built/ptor';
import { browser, by, element } from 'protractor';


describe('Muzix App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('should display title', () => {
    page.navigateTo();
    expect(browser.getTitle()).toEqual('MuzixApp');
  });

  it('should redirect to /login route on opening the application', () => {
    expect(browser.getCurrentUrl()).toContain('/login');
  });

  it('should be redirected to /register route', () => {
    browser.element(by.css('.register-user')).click()
    expect(browser.getCurrentUrl()).toContain('/register');
  });

   it('should be able to register user', () => {
     browser.element(by.id('firstName')).sendKeys('Testfirst');
     browser.element(by.id('lastName')).sendKeys('Testlast');
     browser.element(by.id('userId')).sendKeys('testuser');
     browser.element(by.id('password')).sendKeys('Password');
     browser.element(by.css('.register-user')).click()
     expect(browser.getCurrentUrl()).toContain('/login');
   })

  it('should be able to login user and navigate to top tracks', () => {
    browser.element(by.id('userId')).sendKeys('testuser');
    browser.element(by.id('password')).sendKeys('Password');
    browser.element(by.css('.login-user')).click()
    expect(browser.getCurrentUrl()).toContain('/tracks/top');
  });

  it('should be able to navigate to top tracks', () => {
    browser.element(by.id('top-button')).click()
    expect(browser.getCurrentUrl()).toContain('/tracks/top');
  });

  it('should be able to navigate to featured playlists', () => {
    browser.element(by.id('featured-button')).click()
    expect(browser.getCurrentUrl()).toContain('/playlists/featured');
  });

  it('should be able to navigate to bookmarks page', () => {
    browser.element(by.id('bookmark-button')).click()
    expect(browser.getCurrentUrl()).toContain('/tracks/bookmarks');
  });

  it('should be able to navigate to my playlist', () => {
    browser.element(by.id('playlist-button')).click()
    expect(browser.getCurrentUrl()).toContain('/tracks/playlist');
  });

  it('should be able to search for tracks', () => {
    browser.element(by.id('search-button')).click()
    expect(browser.getCurrentUrl()).toContain('/tracks/search');
    browser.element(by.id('search-button-input')).sendKeys('honey');
    browser.element(by.id('search-button-input')).sendKeys(protractor.Key.ENTER);
    browser.actions().sendKeys(protractor.Key.ENTER).perform();
    const searchItems =  element.all(by.css('.mat-card-title'));
    expect(searchItems.count()).toBe(20);
    for(let i = 0; i < 1; i +=1){
      expect(searchItems.get(i).getText()).toContain('Honey');
    }
  });

  it('should be able to add tracks to playlist', async () => {
    browser.driver.manage().window().maximize();
    browser.driver.sleep(1000);
    const searchItems = element.all(by.css('.track-thumbnail'));
    expect(searchItems.count()).toBe(20);
    searchItems.get(0).click();
    browser.element(by.id('add-button')).click()   
  });

});
