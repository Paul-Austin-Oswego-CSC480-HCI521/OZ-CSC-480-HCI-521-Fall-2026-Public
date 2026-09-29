import "./styles/login.css";

// The form emits credentials; the page owns the login integration.
// TODO (#49): Replace this temporary event inspection with the real login API call.
document.querySelector("#login-form-container").addEventListener("login-submit", (event) => {
  console.log(event.detail);
});

