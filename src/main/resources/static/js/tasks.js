document.getElementById("load-own-tasks").addEventListener("click", showOwnTasks);
document.getElementById("load-users").addEventListener("click", loadUsers);
document.getElementById("add-task").addEventListener("click", createTask);
document.getElementById("logout").addEventListener("click", logout);
const token = document.cookie
    .split("; ")
    .find(row => row.startsWith("jwt="))
    ?.split("=")[1];

if (!token) {
	alert("Permission denied: you have to login first");
	logout();
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
	loadNotAssigned();
	loadStatistics();
}

function logout() {
	document.cookie = "jwt=; Path=/;";
	localStorage.removeItem("isAdmin");
	localStorage.removeItem("username");
	window.location.href = "/login.html"
}

function showOwnTasks() {
	const button = document.getElementById("load-own-tasks");
	const list = document.getElementById("own-tasks-list");
	if (list.innerHTML) {
		list.innerHTML="";
		button.innerHTML="Check Your Tasks";
	} else {
		loadOwnTasks();
	}
}

function loadOwnTasks() {
	fetch("/tasks/own", {
		headers: { "Authorization": "Bearer " + token }
	})
		.then(res => {
			if (res.status === 401) {
	            alert("Token expired: login again.");
	            logout(); 
	            throw new Error("Expired Token");
			}
			return res.json()
		})
		.then(async tasks => {
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
			const rate = await getOwnRate();
			const p = document.createElement("p");
			if (rate < 0) {
				p.innerHTML = `
					No task has been assigned yet!
				`
			} else {
				p.innerHTML = `
					Percentage of tasks completed: ${rate}%
				`
			}
			list.appendChild(p);
			
			const button = document.getElementById("load-own-tasks");
			button.innerHTML="Hide Tasks";
		});
}

async function getOwnRate() {
    try {
        const response = await fetch("/tasks/rate/own", {
            headers: { "Authorization": "Bearer " + token }
        });

		
		if (response.status === 401) {
            alert("Token expired: login again.");
            logout(); 
            throw new Error("Expired Token");
		}
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
		
        const res = await response.json();
        return res.rate;
        
    } catch (error) {
        console.error("Error in loading completion rate:", error);
        return -1;
    }
}

function changeCompletionTask(taskId) {
	fetch(`/tasks/complete/${taskId}`, {
		method: "PUT",
		headers: { "Content-Type": "application/json", "Authorization": "Bearer " + token },
	})
		.then(() => loadOwnTasks());
}

function loadUsers() {
	const button = document.getElementById("load-users");
	const list = document.getElementById("users-list");
	if (list.innerHTML) {
		list.innerHTML="";
		button.innerHTML="Show All Users";
		return;
	}
	
    fetch("/users", {
		headers: { "Authorization": "Bearer " + token }
	})
		.then(res => {
			if (res.status === 401) {
	            alert("Token expired: login again.");
	            logout(); 
	            throw new Error("Expired Token");
			}
			return res.json()
		})
        .then(users => {
            const list = document.getElementById("users-list");
            list.innerHTML = "";

            users.forEach(async u => {
                const li = document.createElement("li");
				let rate = await getRateOf(u.id);
				if (rate < 0) {
					rate = "No tasks assigned yet";
				} else {
					rate = rate+"%";
				}
				li.innerHTML = `
				        ${u.id} - ${u.username} (admin: ${u.admin}) - Completion rate: ${rate}
				    `;
                list.appendChild(li);
            });
			const button = document.getElementById("load-users");
			button.innerHTML = "Hide Users";
        });
}

async function getRateOf(userId) {
    try {
        const response = await fetch(`/tasks/rate/${userId}`, {
            headers: { "Authorization": "Bearer " + token }
        });

		
		if (response.status === 401) {
            alert("Token expired: login again.");
            logout(); 
            throw new Error("Expired Token");
		}
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
		
        const res = await response.json();
        return res.rate;
        
    } catch (error) {
        console.error("Error in loading completion rate:", error);
        return -1;
    }
}

function createTask() {
    const assignment = document.getElementById("new-task-assignment").value;
	
    fetch("/tasks", {
        method: "POST",
        headers: { "Content-Type": "application/json", "Authorization": "Bearer " + token },
        body: assignment
    })
    .then(res => {
		if (res.status === 401) {
            alert("Token expired: login again.");
            logout(); 
            throw new Error("Expired Token");
		}
        if (!res.ok) throw new Error("POST Error");
        return res.text();
    })
    .then(() => loadNotAssigned());
}

function loadNotAssigned() {
	fetch("/tasks/not_assigned", {
		headers: { "Authorization": "Bearer " + token }
	})
		.then(res => {
			if (res.status === 401) {
	            alert("Token expired: login again.");
	            logout(); 
	            throw new Error("Expired Token");
			}
			return res.json()
		})
		.then(async tasks => {
			const list = document.getElementById("not-yet-assigned");
            list.innerHTML = "";

            tasks.forEach(t => {
                const li = document.createElement("li");
                li.innerHTML = `
                    Assignment: ${t.assignment}<br>
					<label>Assign to (username): </label>
					<input id="task-${t.taskId}-assignee" type="text">
					<button onclick="assignTask(${t.taskId})">️Assign</button><br>
					<label>Delete</label>
					<button onclick="deleteTask(${t.taskId})">❌</button><br>
					<p id="message-task-${t.taskId}"></p>
                    <hr>
                `;
                list.appendChild(li);
            });
			
		});
}

function assignTask(taskId) {
    const username = document.getElementById(`task-${taskId}-assignee`).value;
	
    fetch(`/tasks/assign/${taskId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json", "Authorization": "Bearer " + token },
        body: username
    })
    .then(res => {
		if (res.status === 401) {
            alert("Token expired: login again.");
            logout(); 
            throw new Error("Expired Token");
		}
        return res.json();
    })
    .then(res => {
		if (res.error) {
			const message = document.getElementById(`message-task-${taskId}`);
			message.innerHTML = res.error;
		} else {
	        document.getElementById("add-task").onclick = createTask;
	        loadNotAssigned();
			loadStatistics();
		}
    });
}

function deleteTask(taskId) {
    fetch(`/tasks/${taskId}`, { 
		method: "DELETE", 
		headers: { "Authorization": "Bearer " + token }
	})
        .then(res => {
			if (res.status === 401) {
	            alert("Token expired: login again.");
	            logout(); 
	            throw new Error("Expired Token");
			}
			if (!res.ok) throw new Error("DELETE Error");
		})
        .then(() => {
			loadNotAssigned();
			loadStatistics();
		});
}

function loadStatistics() {
	fetch(`/tasks/rate/global`, {
		headers: { "Authorization": "Bearer " + token }
	})
		.then(res => {
			if (res.status === 401) {
	            alert("Token expired: login again.");
	            logout(); 
	            throw new Error("Expired Token");
			}
			return res.json();
		})
		.then(res => {
			if (res.error) {
				throw new Error(res.error);
			}
			const message = document.getElementById("global-rate");
			message.innerHTML = "Global completion rate against assigned: " + res.rate + "%";
		})
}