describe('Post Component', () => {
  beforeEach(() => {
    // Visit the post page with a specific post ID (replace '123' with a valid ID)
    cy.visit('http://localhost:4200');
  });

  it('should display the post details', () => {
    // Assert that the post details are visible
    cy.get('[class="blog-title"]').should('be.visible');
    //cy.get('[data-test=post-description]').should('be.visible');
    //cy.get('[data-test=post-content]').should('be.visible');
  });

  it('should display a "Back to Home" button', () => {
    // Assert that the "Back to Home" button is visible
    //cy.get('[data-test=back-button]').should('be.visible');
  });

  it('should navigate to the home page when "Back to Home" button is clicked', () => {
    // Click the "Back to Home" button
    //cy.get('[data-test=back-button]').click();

    // Assert that the URL is redirected to the home page
    //cy.url().should('include', '/home');
  });

  it('should handle a failed API request gracefully', () => {
    // Simulate a failed API request (e.g., by modifying the URL to a non-existent post)
    cy.visit('/post/999'); // Replace '999' with a non-existent post ID

    // Assert that an error message is displayed
    //cy.get('[data-test=error-message]').should('be.visible');
  });

  // Add more test cases as needed
});