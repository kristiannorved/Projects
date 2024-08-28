/**
 * determines whether the input given to the contact form is valid or not, and
 * spawns an alert detailing whether the submission passed
 */
function submitForm() {
    event.preventDefault();

    let name = document.getElementById('name').value;
    let email = document.getElementById('email').value;
    let subject = document.getElementById('subject').value;

    let valid = true;

    var alertMessage = '';
    if (strLength(name) == 0) {
        alertMessage = alertMessage + 'Please enter your name\n';
        valid = false;
    }
    if (strLength(email) == 0) {
        alertMessage += 'Please enter your email\n';
        valid = false;
    } else if (!validateEmail(email)) {
        alertMessage += 'Please enter a valid email\n';
        valid = false;
    }
    if (strLength(subject) == 0) {
        alertMessage += 'Please enter a message';
        valid = false;
    }

    /* after figuring out if the form variables are valid, an alert is sent */
    if (valid) {
        alert('Contact form submitted! We\'ll be in touch with you shortly.');
        document.getElementById('name').value = '';
        document.getElementById('email').value = '';
        document.getElementById('subject').value = '';
    } else {
        alert(alertMessage);
    }
}

/**
 * determine whether a given email is legitimate or not
 * @email {String} an email
 * @return {Boolean} if email is valid
 */ 
function validateEmail(email) {
    var re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}
  
/**
 * find the length of a given string
 * @str {String} a string
 * @return {Number} length of string
 */
function strLength(str) {
    return str.trim().length;
}

/** 
 * toggle between showing and hiding the hamburger menu for mobile use 
 */
function hamburger() {
    event.preventDefault();
    event.stopPropagation();
    var ham = document.getElementById("ham-links");
    if (ham.style.display === "block") {
        ham.style.display = "none";
    } else {
        ham.style.display = "block";
    }
}