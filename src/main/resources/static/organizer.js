
const API = "/api";

let events = [];
let registrations = [];


async function request(url, options = {}) {
    const response = await fetch(API + url, {
        headers: { "Content-Type": "application/json" },
        ...options
    });

    const text = await response.text();

    if (!response.ok) {
        throw new Error(text || response.status);
    }

    if (!text) return null;

    try {
        return JSON.parse(text);
    } catch {
        return text;
    }
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

function formatDate(date) {
    return new Date(date).toLocaleString("en-IN", {
        dateStyle: "medium",
        timeStyle: "short"
    });
}

function showMessage(message, error = false) {
    const box = document.getElementById("event-message");
    box.textContent = message;
    box.style.color = error ? "#F87171" : "#86EFAC";
}

// LOAD EVENTS AND REGISTRATIONS

async function loadData() {
    try {
        const [eventData, registrationData] = await Promise.all([
            request("/events"),
            request("/registrations")
        ]);

        events = Array.isArray(eventData)
            ? eventData
            : eventData.events || eventData.data || [];

        registrations = Array.isArray(registrationData)
            ? registrationData
            : registrationData.registrations || registrationData.data || [];

        displayEvents();
        updateDropdown();
        updateDashboard();

    } catch (error) {
        console.error(error);
        showMessage("Could not load dashboard data.", true);
    }
}

// DASHBOARD STATISTICS

function updateDashboard() {
    document.getElementById("total-events").textContent = events.length;

    document.getElementById("total-registrations").textContent =
        registrations.length;

    const capacity = events.reduce(
        (sum, event) => sum + Number(event.capacity || 0), 0
    );

    const active = registrations.filter(registration =>
        String(registration.status).toUpperCase() !== "CANCELLED"
    ).length;

    document.getElementById("available-seats").textContent =
        Math.max(0, capacity - active);
}

// DISPLAY EVENTS

function displayEvents() {
    const container = document.getElementById("manage-events-container");

    if (!events.length) {
        container.innerHTML = "<p>No events available.</p>";
        return;
    }

    container.innerHTML = events.map(event => `
        <div class="event-card">
            <h3>${escapeHTML(event.event_name || event.title)}</h3>
            <p>Event ID: ${escapeHTML(event.event_id)}</p>
            <p>${escapeHTML(event.category)}</p>
            <p>${escapeHTML(formatDate(event.event_date))}</p>
            <p>${escapeHTML(event.venue)}</p>

            <button onclick="editEvent(${Number(event.event_id)})">
                Edit
            </button>

            <button onclick="deleteEvent(${Number(event.event_id)})">
                Delete
            </button>
        </div>
    `).join("");
}

// ADD OR UPDATE EVENT

async function saveEvent(e) {
    e.preventDefault();

    const editId = document.getElementById("edit-event-id").value;

    const data = {
        event_id: Number(document.getElementById("event-id").value),
        club_id: Number(document.getElementById("club-id").value),
        event_name: document.getElementById("event-name").value.trim(),
        title: document.getElementById("event-title").value.trim(),
        description_of_event:
            document.getElementById("event-description").value.trim(),
        category: document.getElementById("event-category").value,
        event_date: document.getElementById("event-date").value,
        venue: document.getElementById("event-venue").value.trim(),
        capacity: Number(document.getElementById("event-capacity").value),
        status_of_event: document.getElementById("event-status").value
    };

    try {
        await request(editId ? `/events/${editId}` : "/events", {
            method: editId ? "PUT" : "POST",
            body: JSON.stringify(data)
        });

        showMessage(editId ? "Event updated!" : "Event created!");
        resetForm();
        await loadData();

    } catch (error) {
        console.error(error);
        showMessage("Could not save event.", true);
    }
}

// EDIT EVENT

async function editEvent(id) {
    try {
        const event = await request(`/events/${id}`);

        document.getElementById("edit-event-id").value = event.event_id;
        document.getElementById("event-id").value = event.event_id;
        document.getElementById("club-id").value = event.club_id;
        document.getElementById("event-name").value = event.event_name || "";
        document.getElementById("event-title").value = event.title || "";
        document.getElementById("event-description").value =
            event.description_of_event || "";
        document.getElementById("event-category").value = event.category;
        document.getElementById("event-date").value =
            event.event_date ? event.event_date.substring(0, 16) : "";
        document.getElementById("event-venue").value = event.venue;
        document.getElementById("event-capacity").value = event.capacity;
        document.getElementById("event-status").value = event.status_of_event;

        document.getElementById("event-form-heading").textContent = "Edit Event";
        document.getElementById("save-event-btn").textContent = "Update Event";

        document.getElementById("manage").scrollIntoView({
            behavior: "smooth"
        });

    } catch (error) {
        showMessage("Could not load event.", true);
    }
}

// RESET FORM

function resetForm() {
    document.getElementById("event-form").reset();
    document.getElementById("edit-event-id").value = "";

    document.getElementById("event-form-heading").textContent =
        "Create an Event";

    document.getElementById("save-event-btn").textContent =
        "Create Event";
}

// DELETE EVENT

async function deleteEvent(id) {
    if (!confirm("Are you sure you want to delete this event?")) return;

    try {
        await request(`/events/${id}`, { method: "DELETE" });

        showMessage("Event deleted.");
        await loadData();

    } catch (error) {
        console.error(error);
        showMessage("Could not delete event. Check its registrations.", true);
    }
}

// PARTICIPANT DROPDOWN

function updateDropdown() {
    const dropdown = document.getElementById("participant-event");

    dropdown.innerHTML =
        '<option value="">Choose an event</option>' +
        events.map(event => `
            <option value="${Number(event.event_id)}">
                ${escapeHTML(event.event_name || event.title)}
            </option>
        `).join("");
}

// LOAD PARTICIPANTS

async function loadParticipants() {
    const id = document.getElementById("participant-event").value;
    const container = document.getElementById("participants-container");

    if (!id) {
        container.textContent = "Please select an event.";
        return;
    }

    try {
        const data = await request(`/registrations/event/${id}`);

        const participants = Array.isArray(data)
            ? data
            : data.registrations || data.participants || data.data || [];

        if (!participants.length) {
            container.innerHTML = "<p>No participants registered.</p>";
            return;
        }

        container.innerHTML = `
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    ${participants.map(person => `
                        <tr>
                            <td>${escapeHTML(person.student_name)}</td>
                            <td>${escapeHTML(person.student_email)}</td>
                            <td>${escapeHTML(person.status)}</td>
                            <td>
                                <button onclick="cancelRegistration(${Number(person.registration_id)})">
                                    Cancel
                                </button>
                            </td>
                        </tr>
                    `).join("")}
                </tbody>
            </table>
        `;

    } catch (error) {
        console.error(error);
        container.textContent = "Could not load participants.";
    }
}

// CANCEL REGISTRATION

async function cancelRegistration(id) {
    if (!confirm("Cancel this registration?")) return;

    try {
        await request(`/registrations/cancel/${id}`, {
            method: "PUT"
        });

        showMessage("Registration cancelled.");
        await loadData();
        await loadParticipants();

    } catch (error) {
        console.error(error);
        showMessage("Could not cancel registration.", true);
    }
}

// INITIALIZE ORGANIZER PAGE

document.addEventListener("DOMContentLoaded", () => {
    loadData();

    document.getElementById("event-form")
        .addEventListener("submit", saveEvent);

    document.getElementById("reset-event-form")
        .addEventListener("click", resetForm);

    document.getElementById("load-participants")
        .addEventListener("click", loadParticipants);
});
