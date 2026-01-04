document.addEventListener('DOMContentLoaded', function() {
    const passwordInput = document.getElementById('password');
    const confirmInput = document.getElementById('confirmPassword');
    const registerButton = document.querySelector('button[type="submit"]');

    const reqLength = document.getElementById('req-length');
    const reqSpecial = document.getElementById('req-special');
    const reqMatch = document.getElementById('req-match');

    function updateStatus(element, isValid) {
        const icon = element.querySelector('span');
        if (isValid) {
            element.classList.remove('text-gray-500', 'text-rose-500');
            element.classList.add('text-teal-500', 'font-medium');
            icon.textContent = '✓';
        } else {
            element.classList.remove('text-teal-500', 'font-medium', 'text-rose-500');
            element.classList.add('text-gray-500');
            icon.textContent = '○';
        }
    }

    function validatePassword() {
        const password = passwordInput.value;
        const confirm = confirmInput.value;

        // Reset styles
        passwordInput.classList.remove('border-teal-500', 'border-rose-500');
        confirmInput.classList.remove('border-teal-500', 'border-rose-500');

        let isValid = true;

        // Length Check
        const hasLength = password.length > 8;
        updateStatus(reqLength, hasLength);

        // Special Char Check
        const hasSpecial = /[^a-zA-Z0-9]/.test(password);
        updateStatus(reqSpecial, hasSpecial);

        // Overall Password Input Logic
        if (hasLength && hasSpecial) {
            passwordInput.classList.add('border-teal-500');
        } else {
            if (password.length > 0) {
                // If user is typing and fails basic rules, we could show rose, 
                // but checking individual rules is more precise. 
                // Let's keep input border gray until valid or explicitly invalid if we wanted strictness.
                // For now, let's highlight error if length > 0 but rules failing? 
                // Actually simplicity: Teal if good. Rose if bad AND length > 0?
                passwordInput.classList.add('border-rose-500');
            }
            isValid = false;
        }

        // Match Check
        const match = password === confirm && password !== '';
        updateStatus(reqMatch, match);
        
        if (match) {
            confirmInput.classList.add('border-teal-500');
        } else {
            if (confirm.length > 0) {
                 confirmInput.classList.add('border-rose-500');
            }
            isValid = false;
        }
    }

    if (passwordInput && confirmInput) {
        passwordInput.addEventListener('input', validatePassword);
        confirmInput.addEventListener('input', validatePassword);
    }
});
