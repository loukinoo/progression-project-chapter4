document.getElementById("load-users").addEventListener("click", loadUsers);
document.getElementById("add-user").addEventListener("click", createUser);
document.getElementById("logout").addEventListener("click", logout);
const token = document.cookie
    .split("; ")
    .find(row => row.startsWith("jwt="))
    ?.split("=")[1];

if (!token) {
	alert("Permission denied: you have to login first");
	window.location.href = "/login.html"
}

function logout() {
	document.cookie = "jwt=; Path=/;";
	window.location.href = "/login.html"
}

function loadUsers() {
    fetch("/users", {
		headers: { "Authorization": "Bearer " + token }
	})
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
        headers: { "Content-Type": "application/json", "Authorization": "Bearer " + token },
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
        headers: { "Content-Type": "application/json", "Authorization": "Bearer " + token },
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
        method: "DELETE",
		headers: { "Authorization": "Bearer " + token }
    })
    .then(res => {
        if (!res.ok) throw new Error("Errore DELETE");
    })
    .then(() => loadUsers());
}