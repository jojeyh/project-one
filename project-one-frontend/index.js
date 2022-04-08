var CryptoJS = require('crypto-js');

let loginBtn = document.querySelector('#login-btn');

loginBtn.addEventListener('click', async () => {
	let usernameInput = document.querySelector('#username');
	let passwordInput = document.querySelector('#password');

	let hashedPassword = CryptoJS.SHA256(passwordInput.value).toString(CryptoJS.enc.Base64);

	const URL = 'http://localhost:8080/login'

	const jsonString = JSON.stringify({
		"username": usernameInput.value,
		"password": hashedPassword
	});

	let res = await fetch(URL, {
		method: 'POST',
		body: jsonString,
	});
	
	let token = res.headers.get('Token');
	localStorage.setItem('jwt', token);

	if (res.status == 200) {
		let user = await res.json();

		localStorage.setItem('user_id', user.id);

		if (user.userRole === 'finmanager') {
			window.location = 'finmanager.html';
		} else if (user.userRole === 'employee') {
			window.location = 'employee.html'	
		}
	} else {
		let errorMsg = await res.text();
		console.log(errorMsg);

		let errorElement = document.querySelector('#error-msg');
		errorElement.innerText = errorMsg;
		errorElement.style.color = 'red';
	}
}
)
