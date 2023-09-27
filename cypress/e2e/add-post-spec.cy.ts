describe('Add Post Component', () => {
  beforeEach(() => {
    cy.visit('/add-post'); // Navigate to the add post page
  });

  it('should display the add post form', () => {
    // Assert that the add post form elements are visible
    cy.get('[data-test=title]').should('be.visible');
    cy.get('[data-test=body]').should('be.visible');
    cy.get('[data-test=add-post-button]').should('be.visible');
  });

  it('should successfully add a new post', () => {
    // Fill in the add post form
    cy.get('[data-test=title]').type('New Post Title');
    cy.get('[data-test=body]').type('This is the content of the new post.');
    cy.get('[data-test=add-post-button]').click(); // Click the add post button

    // Assert that the success message is displayed
    cy.get('[data-test=success-message]').should('be.visible');
  });

  it('should display an error message when the form is submitted with empty fields', () => {
    // Click the add post button without filling in the form
    cy.get('[data-test=add-post-button]').click();

    // Assert that an error message is displayed
    cy.get('[data-test=error-message]').should('be.visible');
  });

  // Add more test cases as needed
});