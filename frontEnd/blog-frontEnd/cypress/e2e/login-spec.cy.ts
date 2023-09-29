

describe('Login Component', () => {
  beforeEach(() => {
    cy.visit('http://localhost:4200/login'); // Navigate to the login page
  });

  it('should display the login form', () => {
    // Assert that the login form elements are visible
    cy.get('[id="inputUsername"]').should('be.visible');
    cy.get('[id="inputPassword" ]').should('be.visible');
    cy.get('[class="btn btn-lg btn-primary btn-block"]').should('be.visible');
  });

  it('should successfully log in with valid credentials', () => {
    // Fill in the login form with valid credentials
    cy.get('[id="inputUsername"]').type('sidhan');
    cy.get('[id="inputPassword"]').type('hello');
    cy.get('[class="btn btn-lg btn-primary btn-block"]').click(); // Click the login button

    // Assert that the user is redirected to the home page (or another expected page)
    //cy.url().should('include', '/home'); // Replace with the expected URL
  });

  // it('should display an error message with invalid credentials', () => {
  //   // Fill in the login form with invalid credentials
  //   cy.get('[data-test=username]').type('invalid_username');
  //   cy.get('[data-test=password]').type('invalid_password');
  //   cy.get('[data-test=login-button]').click(); // Click the login button

  //   // Assert that an error message is displayed
  //   cy.get('[data-test=error-message]').should('be.visible');
  // });

  // Add more test cases as needed
});