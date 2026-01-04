# Password Security Enhancement Walkthrough

Let's implement password security features: verification, matching, and encoding.

## Changes to be Implemented

### 1. Frontend: `register.html`
- **Confirm Password Field**: Let's add a new input field for password confirmation.
- **Real-time Validation**: Let's add JavaScript to provide immediate feedback to the user:
  - **Match Check**: Checks if "Password" and "Confirm Password" match.
  - **Complexity Check**: valid if length > 8 and contains at least one special character.
  - **Visual Feedback**: Turns text/borders teal (valid) or rose (invalid).

### 2. Backend DTO: `RegistrationForm.java`
- Let's add `confirmPassword` field to capture the second input.

### 3. Backend Controller: `RegistrationController.java`
- Let's implement **Validation Logic**: 
  - Validates that `password` equals `confirmPassword`.
  - Validates password length (> 8).
  - Validates special character presence.
  - Adds errors to `BindingResult` if validation fails, reloading the form with error messages.
- Let's implement **Password Encoding**:
  - Implemented `encodePassword` method using **SHA-512** hashing followed by **Base64** encoding.
  - The raw password is strictly processed and encoded *before* being set on the `UsuarioJpa` entity.