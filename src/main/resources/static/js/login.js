document.getElementById("signin").addEventListener("click", signin);
document.getElementById("signup").addEventListener("click", signup);

const docToken = document.cookie
    .split("; ")
    .find(row => row.startsWith("jwt="))
    ?.split("=")[1];

if (docToken) {
	alert("You have already logged in: redirecting to /tasks.html");
	window.location.href = "/tasks.html"
}

function signin() {
	const username = document.getElementById("username").value;
	const password = document.getElementById("password").value;
	const message = document.getElementById("message-in");

	try {
		fetch("/api/auth/signin", {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify({ username, password })
		})
			.then(res => {
				result = res.json();
				console.log(result);
				return result;
			})
			.then(res => {
				if (res.error) {
					message.innerHTML = res.error;
					return;
				}
				token = res.token;
				console.log(token);
				document.cookie = "jwt="+token+"; path=/";
				localStorage.setItem("isAdmin", res.isAdmin);
				localStorage.setItem("username", res.username);
				console.log(document.cookie);
				console.log(localStorage);
				message.innerHTML = res.message;
				window.location.href = "/tasks.html";
				return token;
			});
	} catch (err) {
        message.innerHTML = "Error: cannot connect to server";
		console.error(err);
	}
}

function signup() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
	const message = document.getElementById("message-up");

	try {
	    fetch("/api/auth/signup", {
	        method: "POST",
	        headers: { "Content-Type": "application/json" },
	        body: JSON.stringify({ username, password })
	    })
			.then(res => {
				result = res.json();
				console.log(result); 
				return result;
			})
			.then(res => {
				if (res.error) {
					message.innerHTML = res.error;
					return;
				}
				message.innerHTML = res.message;
				return;
			})
			.then(() => signin());
	} catch (err) {
		message.innerHTML = "Error: cannot connect to server";
		console.error(err);
	}
}
