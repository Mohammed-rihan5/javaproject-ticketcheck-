/**
 * 
 */
// Tabs
const tabs = document.querySelectorAll(".tab");
const signinForm = document.getElementById("signinForm");
const signupForm = document.getElementById("signupForm");

// Switch using tab buttons
tabs.forEach(tab => {
  tab.addEventListener("click", () => {
    tabs.forEach(t => t.classList.remove("active"));
    tab.classList.add("active");

    if (tab.dataset.tab === "signin") {
      signinForm.classList.remove("hidden");
      signupForm.classList.add("hidden");
    } else {
      signinForm.classList.add("hidden");
      signupForm.classList.remove("hidden");
    }
  });
});

// Switch using links
document.querySelectorAll("[data-switch]").forEach(link => {
  link.addEventListener("click", (e) => {
    e.preventDefault();

    const target = link.dataset.switch;

    if (target === "signup") {
      signinForm.classList.add("hidden");
      signupForm.classList.remove("hidden");
    } else {
      signinForm.classList.remove("hidden");
      signupForm.classList.add("hidden");
    }
  });
});