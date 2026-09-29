import "./styles/login.css";
import "./components/gift-create-account-form/gift-create-account-form.js";

// TODO: Replace this notice with registration API integration when its contract is available.
document.querySelector("gift-create-account-form").addEventListener("create-account-submit", (event) => {
  event.currentTarget.errorMessage = "Account registration is not available yet. Please try again later.";
});
