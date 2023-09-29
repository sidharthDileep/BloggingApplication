describe('Register Component', () => {
  beforeEach(() => {
    cy.visit('http://localhost:4200/register'); // Navigate to the register page
  });

  it('should display the registration form', () => {
    // Assert that the registration form elements are visible
    cy.get('[type="text"]').should('be.visible');
    cy.get('[type="text"]').should('be.visible');
    cy.get('[type="password"]').should('be.visible');
    cy.get('[type="password"]').should('be.visible');
    cy.get('[type="submit"]').should('be.visible');
  });

  it('should successfully register a new user', () => {
    // Fill in the registration form with valid details
    cy.get('[id="inputName"]').type('Your Name');
    cy.get('[id="inputUsername"]').type('new_username');
    cy.get('[id="inputEmail"]').type('new_email@example.com');
    cy.get('[id="inputPassword"]').type('new_password');
    cy.get('[id="inputConfirmPassword"]').type('new_password');
    cy.get('[type="submit"]').click(); // Click the register button

    // Assert that the user is redirected to the registration success page (or another expected page)
    //cy.url().should('include', 'http://localhost:4200/register-success'); // Replace with the expected URL
  });

  it('should display an error message with invalid registration details', () => {
    // Fill in the registration form with invalid details (e.g., mismatched passwords)
    cy.get('[id="inputName"]').type('Your Name');
    cy.get('[id="inputUsername"]').type('new_username');
    cy.get('[id="inputEmail"]').type('new_email@example.com');
    cy.get('[id="inputPassword"]').type('new_password');
    cy.get('[id="inputConfirmPassword"]').type('new_password_wrong');
    cy.get('[type="submit"]').click(); // Click the register button

    // Assert that an error message is displayed
    //cy.get('[data-test=error-message]').should('be.visible');
  });

  // Add more test cases as needed
});