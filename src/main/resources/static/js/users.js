document.getElementById("load-users").addEventListener("click", loadUsers);
document.getElementById("add-user").addEventListener("click", createUser);

function loadUsers() {
    fetch("/users")
        .then(res => res.json())
        .then(users => {
            const list = document.getElementById("users-list");
            list.innerHTML = "";

            users.forEach(u => {
                const li = document.createElement("li");
				li.innerHTML = `
				        ${u.id} - ${u.name} (admin: ${u.admin})
				        <button onclick="deleteUser(${u.id})">❌</button>
				        <button onclick="updateUser(${u.id})">✏️</button>
				    `;
                list.appendChild(li);
            });
        });
}

function createUser() {
    const name = document.getElementById("new-name").value;
    const admin = document.getElementById("new-admin").checked;

    fetch("/users", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ admin, name })
    })
    .then(res => {
        if (!res.ok) throw new Error("Errore POST");
        return res.text();
    })
    .then(() => loadUsers());
}

function updateUser(id) {
	const name = document.getElementById("new-name").value;
	const admin = document.getElementById("new-admin").checked;
    
	fetch(`/users/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ admin, name })
    })
    .then(res => {
        if (!res.ok) throw new Error("Errore PUT");
        return res.text();
    })
    .then(() => loadUsers());
}

function deleteUser(id) {
    fetch(`/users/${id}`, {
        method: "DELETE"
    })
    .then(res => {
        if (!res.ok) throw new Error("Errore DELETE");
    })
    .then(() => loadUsers());
}