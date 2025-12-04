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

function logout() {
	document.cookie = "jwt=; Path=/;";
	window.location.href = "/login.html"
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
                const li = document.createElement("li");
                li.innerHTML = `
                    ID: ${t.taskId} — User: ${t.userId}<br>
                    Assigned: ${t.assigned} | Completed: ${t.completed}<br>
                    Assignment: ${t.assignment}<br>
                    <button onclick="deleteTask(${t.taskId})">❌</button>
                    <button onclick="updateTask(${t.taskId})">✏️</button>
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
        method: "POST",
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

    fetch(`/tasks/${taskId}`, {
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