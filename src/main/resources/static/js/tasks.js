document.getElementById("load-own-tasks").addEventListener("click", loadOwnTasks);
document.getElementById("load-tasks").addEventListener("click", loadTasks);
document.getElementById("add-task").addEventListener("click", createTask);
document.getElementById("logout").addEventListener("click", logout);
const token = document.cookie
    .split("; ")
    .find(row => row.startsWith("jwt="))
    ?.split("=")[1];

if (!token) {
	alert("Permission denied: you have to login first");
	window.location.href = "/login.html"
}

const loggedAsAdmin = localStorage.getItem("isAdmin") === "true";

if (!loggedAsAdmin) {
	const adminActions = document.getElementById("adminActions");
	if (adminActions) {
		adminActions.style.display = "none";
	}
} else {
	const title = document.getElementById("title");
	title.innerHTML += " (Logged in as admin)";
}

function logout() {
	document.cookie = "jwt=; Path=/;";
	localStorage.removeItem("isAdmin");
	localStorage.removeItem("username");
	window.location.href = "/login.html"
}

function loadOwnTasks() {
	fetch("/tasks/own", {
		headers: { "Authorization": "Bearer " + token }
	})
		.then(res => res.json())
		.then(tasks => {
			const list = document.getElementById("own-tasks-list");
            list.innerHTML = "";

            tasks.forEach(t => {
                const li = document.createElement("li");
				let completion = ""
				if (t.completed) {
					completion = `
						Completed! 
						<button onClick="changeCompletionTask(${t.taskId})">Mark as not yet completed</button>
					`;
				} else {
					completion = `
						Not yet completed 
						<button onClick="changeCompletionTask(${t.taskId})">Mark as completed</button>
					`;
				}
                li.innerHTML = `
                    Assignment: ${t.assignment}<br>
                    ${completion}
                    <hr>
                `;
                list.appendChild(li);
            });
		});
}

function changeCompletionTask(taskId) {
	fetch(`/tasks/complete/${taskId}`, {
		method: "PUT",
		headers: { "Content-Type": "application/json", "Authorization": "Bearer " + token },
	})
		.then(() => loadOwnTasks());
}

function loadTasks() {
			
    fetch("/tasks", {
		headers: { "Authorization": "Bearer " + token }
	})
        .then(res => res.json())
        .then(tasks => {
            const list = document.getElementById("tasks-list");
            list.innerHTML = "";

            tasks.forEach(t => {
				let adminButtons = "";
				if (loggedAsAdmin) {
					adminButtons = `
						<button onclick="deleteTask(${t.taskId})">❌</button>
						<button onclick="updateTask(${t.taskId})">✏️</button>
					`;
				}
				
                const li = document.createElement("li");
                li.innerHTML = `
                    ID: ${t.taskId} — User: ${t.userId}<br>
                    Assigned: ${t.assigned} | Completed: ${t.completed}<br>
                    Assignment: ${t.assignment}<br>
                    ${adminButtons}
                    <hr>
                `;
                list.appendChild(li);
            });
        });
}

function createTask() {
    const userId = Number(document.getElementById("task-userId").value);
    const assignment = document.getElementById("task-assignment").value;
    const completed = document.getElementById("task-completed").checked;
    const assigned = document.getElementById("task-assigned").checked;

    fetch("/tasks", {
        method: "PUT",
        headers: { "Content-Type": "application/json", "Authorization": "Bearer " + token },
        body: JSON.stringify({ userId, assignment, completed, assigned })
    })
    .then(res => {
        if (!res.ok) throw new Error("Errore POST");
        return res.text();
    })
    .then(() => loadTasks());
}

function updateTask(taskId) {
    const userId = Number(document.getElementById("task-userId").value);
    const assignment = document.getElementById("task-assignment").value;
    const completed = document.getElementById("task-completed").checked;
    const assigned = document.getElementById("task-assigned").checked;

    fetch(`/tasks/update/${taskId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json", "Authorization": "Bearer " + token },
        body: JSON.stringify({ userId, assignment, completed, assigned })
    })
    .then(res => {
        if (!res.ok) throw new Error("Errore PUT");
        return res.text();
    })
    .then(() => {
        document.getElementById("add-task").onclick = createTask;
        loadTasks();
    });
}

function deleteTask(taskId) {
    fetch(`/tasks/${taskId}`, { 
		method: "DELETE", 
		headers: { "Authorization": "Bearer " + token }
	})
        .then(res => {
            if (!res.ok) throw new Error("Errore DELETE");
        })
        .then(() => loadTasks());
}