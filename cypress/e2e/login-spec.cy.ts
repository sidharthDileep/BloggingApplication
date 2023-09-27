

describe('Login Component', () => {
  beforeEach(() => {
    cy.visit('/login'); // Navigate to the login page
  });

  it('should display the login form', () => {
    // Assert that the login form elements are visible
    cy.get('[data-test=username]').should('be.visible');
    cy.get('[data-test=password]').should('be.visible');
    cy.get('[data-test=login-button]').should('be.visible');
  });

  it('should successfully log in with valid credentials', () => {
    // Fill in the login form with valid credentials
    cy.get('[data-test=username]').type('your_username');
    cy.get('[data-test=password]').type('your_password');
    cy.get('[data-test=login-button]').click(); // Click the login button

    // Assert that the user is redirected to the home page (or another expected page)
    cy.url().should('include', '/home'); // Replace with the expected URL
  });

  it('should display an error message with invalid credentials', () => {
    // Fill in the login form with invalid credentials
    cy.get('[data-test=username]').type('invalid_username');
    cy.get('[data-test=password]').type('invalid_password');
    cy.get('[data-test=login-button]').click(); // Click the login button

    // Assert that an error message is displayed
    cy.get('[data-test=error-message]').should('be.visible');
  });

  // Add more test cases as needed
});