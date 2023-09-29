

describe('Register Success Component', () => {
  beforeEach(() => {
    cy.visit('http://localhost:4200/register-success'); // Navigate to the registration success page
  });

  it('should display a success message', () => {
    // Assert that the success message is visible
    cy.get('[data-test=success-message]').should('be.visible');
  });

  it('should provide a link to the login page', () => {
    // Assert that a link to the login page is visible
    cy.get('[data-test=login-link]').should('be.visible');
  });

  it('should navigate to the login page when the link is clicked', () => {
    // Click the login link
    cy.get('[data-test=login-link]').click();

    // Assert that the URL is redirected to the login page
    cy.url().should('include', '/login');
  });

  // Add more test cases as needed
});