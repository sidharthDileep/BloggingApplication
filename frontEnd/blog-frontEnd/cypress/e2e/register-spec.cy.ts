describe('Register Component', () => {
  beforeEach(() => {
    cy.visit('/register'); // Navigate to the register page
  });

  it('should display the registration form', () => {
    // Assert that the registration form elements are visible
    cy.get('[data-test=name]').should('be.visible');
    cy.get('[data-test=username]').should('be.visible');
    cy.get('[data-test=email]').should('be.visible');
    cy.get('[data-test=password]').should('be.visible');
    cy.get('[data-test=confirm-password]').should('be.visible');
    cy.get('[data-test=register-button]').should('be.visible');
  });

  it('should successfully register a new user', () => {
    // Fill in the registration form with valid details
    cy.get('[data-test=name]').type('Your Name');
    cy.get('[data-test=username]').type('new_username');
    cy.get('[data-test=email]').type('new_email@example.com');
    cy.get('[data-test=password]').type('new_password');
    cy.get('[data-test=confirm-password]').type('new_password');
    cy.get('[data-test=register-button]').click(); // Click the register button

    // Assert that the user is redirected to the registration success page (or another expected page)
    cy.url().should('include', '/register-success'); // Replace with the expected URL
  });

  it('should display an error message with invalid registration details', () => {
    // Fill in the registration form with invalid details (e.g., mismatched passwords)
    cy.get('[data-test=name]').type('Your Name');
    cy.get('[data-test=username]').type('new_username');
    cy.get('[data-test=email]').type('new_email@example.com');
    cy.get('[data-test=password]').type('password1');
    cy.get('[data-test=confirm-password]').type('password2');
    cy.get('[data-test=register-button]').click(); // Click the register button

    // Assert that an error message is displayed
    cy.get('[data-test=error-message]').should('be.visible');
  });

  // Add more test cases as needed
});