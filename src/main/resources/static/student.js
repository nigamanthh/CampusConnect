
const API = "/api";

let events = [];

async function request(url, options = {}) {
    const response = await fetch(API + url, {
        headers: { "Content-Type": "application/json" },
        ...options
    });

    if (!response.ok) {
        throw new Error(await response.text() || response.status);
    }

    const text = await response.text();
    return text ? JSON.parse(text) : null;
}

function formatDate(date) {
    return new Date(date).toLocaleString("en-IN", {
        dateStyle: "medium",
        timeStyle: "short"
    });
}

function showMessage(message, error = false) {
    const box = document.getElementById("message");
    box.textContent = message;
    box.style.color = error ? "#F87171" : "#86EFAC";
}

function escapeHTML(value) {
    return String(value ?? "").replace(/[&<>"']/g, char => ({
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': "&quot;",
        "'": "&#039;"
    })[char]);
}

// LOAD EVENTS

async function loadEvents() {
    try {
        const data = await request("/events");
        events = Array.isArray(data) ? data : data.events || data.data || [];

        displayEvents(events);
        updateDropdown();

    } catch (error) {
        console.error(error);
        document.getElementById("event-container").textContent =
            "Unable to load events.";
    }
}

// DISPLAY EVENT CARDS

function displayEvents(list) {
    const container = document.getElementById("event-container");

    if (!list.length) {
        container.innerHTML = "<p>No events found.</p>";
        return;
    }

    container.innerHTML = list.map(event => `
        <div class="event-card">
            <span class="event-category">
                ${escapeHTML(event.category)}
            </span>

            <h3>${escapeHTML(event.event_name || event.title)}</h3>

            <p>${escapeHTML(event.description_of_event)}</p>
            <p>${escapeHTML(formatDate(event.event_date))}</p>
            <p>Venue: ${escapeHTML(event.venue)}</p>

            <button onclick="viewEvent(${Number(event.event_id)})">
                View Details
            </button>

            <button onclick="selectEvent(${Number(event.event_id)})">
                Register
            </button>
        </div>
    `).join("");
}

// SEARCH AND FILTER

function filterEvents() {
    const search = document.getElementById("search-input")
        .value.toLowerCase();

    const category = document.getElementById("category-filter").value;

    const filtered = events.filter(event => {
        const name = (event.event_name || event.title || "").toLowerCase();
        const description = (event.description_of_event || "").toLowerCase();

        return (
            (name.includes(search) || description.includes(search)) &&
            (category === "all" || event.category === category)
        );
    });

    displayEvents(filtered);
}

// VIEW EVENT DETAILS

async function viewEvent(id) {
    try {
        const event = await request(`/events/${id}`);

        document.getElementById("event-details").innerHTML = `
            <h3>${escapeHTML(event.event_name || event.title)}</h3>
            <p>${escapeHTML(event.description_of_event)}</p>
            <p>Category: ${escapeHTML(event.category)}</p>
            <p>Date: ${escapeHTML(formatDate(event.event_date))}</p>
            <p>Venue: ${escapeHTML(event.venue)}</p>
            <p>Capacity: ${escapeHTML(event.capacity)}</p>
            <p>Status: ${escapeHTML(event.status_of_event)}</p>
        `;

        document.getElementById("details").scrollIntoView({
            behavior: "smooth"
        });

    } catch (error) {
        showMessage("Could not load event details.", true);
    }
}

// FILL REGISTRATION DROPDOWN

function updateDropdown() {
    const dropdown = document.getElementById("registration-event");

    dropdown.innerHTML =
        '<option value="">Choose an event</option>' +
        events.map(event => `
            <option value="${Number(event.event_id)}">
                ${escapeHTML(event.event_name || event.title)}
            </option>
        `).join("");
}

// SELECT EVENT FOR REGISTRATION

function selectEvent(id) {
    document.getElementById("registration-event").value = id;

    document.getElementById("register").scrollIntoView({
        behavior: "smooth"
    });
}

// REGISTER STUDENT

async function registerStudent(e) {
    e.preventDefault();

    const data = {
        registration_id: Math.floor(100000 + Math.random() * 900000),
        event_id: Number(document.getElementById("registration-event").value),
        student_name: document.getElementById("student-name").value.trim(),
        student_email: document.getElementById("student-email").value.trim(),
        status: "REGISTERED"
    };

    try {
        await request("/registrations", {
            method: "POST",
            body: JSON.stringify(data)
        });

        showMessage("Registration successful!");
        document.getElementById("registration-form").reset();

    } catch (error) {
        console.error(error);
        showMessage("Registration failed. Please try again.", true);
    }
}

// INITIALIZE PAGE

document.addEventListener("DOMContentLoaded", () => {
    loadEvents();

    document.getElementById("search-input")
        .addEventListener("input", filterEvents);

    document.getElementById("category-filter")
        .addEventListener("change", filterEvents);

    document.getElementById("registration-form")
        .addEventListener("submit", registerStudent);
});
