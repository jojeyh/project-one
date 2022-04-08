window.addEventListener('load', async (event) => {
	await populateReimbursementsTable('pending');

	let decisionSubmits = document.getElementsByClassName('decision-btn');
	for (let i=0; i < decisionSubmits.length; i++) {
		let decisionSubmit = decisionSubmits[i];
		decisionSubmit.addEventListener('click', async () => {
			let decisionSelect = decisionSubmit.parentNode.parentNode.childNodes[8].firstChild; // select tag
			
			const URL = `http://localhost:8080/user/${localStorage.getItem('user_id')}/reimbursements/${decisionSelect.parentNode.parentNode.firstChild.innerText}?status=${decisionSelect.value}`

			let res = await fetch(URL, {
				method: 'PATCH',
				headers: {
					'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
				}
			});

			if (res.status = 200) {
				populateReimbursementsTable('pending');
			}
		});
	}
/*
	let modalImg = document.getElementById('img-preview');
	let modalDiv = document.getElementById('modal-div');

	let images = document.getElementsByClassName('receipt-img');
	for (let image of images) {
		image.onclick = function() {
			modalImg.setAttribute('src', image.src);
			modalDiv.style.display = 'block';
		}
	}

	var span = document.getElementById("close");
	span.onclick = function() {
		modal.style.display = "none";
	}
	*/
});

let logoutBtn = document.querySelector('#logout-btn');
logoutBtn.addEventListener('click', () => {
	localStorage.removeItem('jwt');
	window.location = 'index.html';
});

function centsToDollars(cents) {
        dollars = cents.toString();
        l = dollars.length;
        return "$" + dollars.slice(0, l-2) + "." + dollars.slice(l-2, l);
}

 		function openModal(img) {
			let modalImg = document.getElementById('modal-img');
			modalImg.setAttribute('src', img.src);

            document.getElementById("modal-div")
                .classList.add("is-active");
        }
 
        // Add event listeners to close the modal
        // whenever user click outside modal
        document.querySelectorAll(
			'.modal-background, .modal-close,.modal-card-head .delete, .modal-card-foot .button'
                  ).forEach(($el) => {
            const $modal = $el.closest('.modal');
 
            $el.addEventListener('click', () => {
 
                // Remove the is-active class from the modal
                $modal.classList.remove("is-active");
            });
        });

function setFilter(filter) {
	populateReimbursementsTable(filter.value); 
}

async function populateReimbursementsTable(filter) {
	const URL = `http://localhost:8080/reimbursements`;

	let res = await fetch(URL, {
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
		}
	});

	if (res.status = 200) {

		let reimbursements = await res.json();
		let tblBody = document.querySelector('#reimb-tbl > tbody');
		let tblHead = document.querySelector('#reimb-tbl > thead');
		tblBody.innerHTML = '';
		tblHead.innerHTML = '';

		let tr = document.createElement('tr');
		let th1 = document.createElement('th');
		th1.innerText = 'ID';
		let th2 = document.createElement('th');
		th2.innerText = 'Name';
		let th3 = document.createElement('th');
		th3.innerText = 'Description';
		let th4 = document.createElement('th');
		th4.innerText = 'Receipt';
		let th5 = document.createElement('th');
		th5.innerText = 'Type';
		let th6 = document.createElement('th');
		th6.innerText = 'Submitted';
		let th7 = document.createElement('th');
		th7.innerText = 'Status';
		let th8 = document.createElement('th');
		th8.innerText = 'Amount';

		tr.appendChild(th1);
		tr.appendChild(th2);
		tr.appendChild(th3);
		tr.appendChild(th4);
		tr.appendChild(th5);
		tr.appendChild(th6);
		tr.appendChild(th7);
		tr.appendChild(th8);

		tblHead.appendChild(tr);
		
		for (let reimbursement of reimbursements) {
			if (reimbursement.status != filter) {
				continue;
			}
			
			let tr = document.createElement('tr');

			let td1 = document.createElement('td');
			td1.innerText = reimbursement.id;

			let td2 = document.createElement('td');
			td2.innerText = reimbursement.employee_name;

			let td3 = document.createElement('td');
			td3.innerText = reimbursement.description;

			let td4 = document.createElement('td');
			let btn = document.createElement('button');
			btn.setAttribute('type', 'button');
			td4.appendChild(btn);
			let imgElement = document.createElement('img');
			imgElement.setAttribute('src', `http://localhost:8080/user/${reimbursement.employee_id}/reimbursements/${reimbursement.id}/receipt`);
			imgElement.style.height = '50px';
			imgElement.style.width = '50px';
			btn.appendChild(imgElement);
            btn.setAttribute('onclick', 'openModal(this.firstChild)');

			let td5 = document.createElement('td');
			td5.innerText = reimbursement.type;

			let td6 = document.createElement('td');
			td6.innerText = reimbursement.submitted;

			let td7 = document.createElement('td');
			td7.innerText = reimbursement.status;

			let td8 = document.createElement('td');
			td8.innerText = centsToDollars(reimbursement.amount)

			let td9 = document.createElement('td');
			td9.innerHTML = `<select class="select">
				<option value="rejected">Reject</option>
				<option value="approved">Approve</option>
				</select>`;

			let td10 = document.createElement('td');
			td10.innerHTML = `<button class="button decision-btn">Submit</button>`

			tr.appendChild(td1);
			tr.appendChild(td2);
			tr.appendChild(td3);
			tr.appendChild(td4);
			tr.appendChild(td5);
			tr.appendChild(td6);
			tr.appendChild(td7);
			tr.appendChild(td8);
			tr.appendChild(td9);
			tr.appendChild(td10);
		

			tblBody.appendChild(tr);
		}
	}

	 let decisionSubmits = document.getElementsByClassName('decision-btn');
        for (let i=0; i < decisionSubmits.length; i++) {
                let decisionSubmit = decisionSubmits[i];
                decisionSubmit.addEventListener('click', async () => {
                        let decisionSelect = decisionSubmit.parentNode.parentNode.childNodes[8].firstChild; // select tag

                        const URL = `http://localhost:8080/user/${localStorage.getItem('user_id')}/reimbursements/${decisionSelect.parentNode.parentNode.firstChild.innerText}?status=${decisionSelect.value}`

                        let res = await fetch(URL, {
                                method: 'PATCH',
                                headers: {
                                        'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
                                }
                        });

                        if (res.status = 200) {
                                populateReimbursementsTable('pending');
                        }
                });
	}

}


/* async function populateReimbursementsTable() {
	const URL = 'http://localhost:8080/reimbursements'

	let res = await fetch(URL, {
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
		}
	});

	if (res.status = 200) {
		let reimbursements = await res.json();

		let tbody = document.querySelector('#pending-tbl > tbody');
      	let thead = document.querySelector('#pending-tbl > thead');
		tbody.innerHTML = '';
                thead.innerHTML = '';

                let tr = document.createElement('tr');
                let th1 = document.createElement('th');
                th1.innerText = 'ID';
                let th2 = document.createElement('th');
                th2.innerText = 'Name';
                let th3 = document.createElement('th');
                th3.innerText = 'Description';
                let th4 = document.createElement('th');
                th4.innerText = 'Receipt';
                let th5 = document.createElement('th');
                th5.innerText = 'Type';
                let th6 = document.createElement('th');
                th6.innerText = 'Submitted';
                let th7 = document.createElement('th');
                th7.innerText = 'Status';
                let th8 = document.createElement('th');
                th8.innerText = 'Amount';

                tr.appendChild(th1);
                tr.appendChild(th2);
                tr.appendChild(th3);
                tr.appendChild(th4);
                tr.appendChild(th5);
                tr.appendChild(th6);
                tr.appendChild(th7);
                tr.appendChild(th8);

                thead.appendChild(tr);

		for (let reimbursement of reimbursements) {
			let tr = document.createElement('tr');

			let td1 = document.createElement('td');
			td1.innerText = reimbursement.id;

			let td2 = document.createElement('td');
			td2.innerText = reimbursement.employee_id;

			let td3 = document.createElement('td');
			td3.innerText = reimbursement.description;

			let td4 = document.createElement('td');
			let link = document.createElement('a');
			link.setAttribute('href', '');
			td4.appendChild(link);
			let imgElement = document.createElement('img');
			imgElement.setAttribute('src', `http://localhost:8080/user/${reimbursement.employee_id}/reimbursements/${reimbursement.id}/receipt`);
			imgElement.setAttribute('class', 'receipt-img');
			imgElement.style.height = '50px';
			imgElement.style.width = '50px';
			link.setAttribute('type', 'button');
			link.setAttribute('class', 'btn btn-primary');
			link.setAttribute('data-bs-toggle', 'modal');
			link.setAttribute('data-bs-target', '#modal-div');
			link.setAttribute('href', '#modal-div');
			link.appendChild(imgElement);

			let td5 = document.createElement('td');
			td5.innerText = reimbursement.type;

			let td6 = document.createElement('td');
			td6.innerText = centsToDollars(reimbursement.submitted);

			let td7 = document.createElement('td');
			td7.innerText = reimbursement.status;

			let td8 = document.createElement('td');
			td8.innerText = reimbursement.amount;

			let td9 = document.createElement('td');
			td9.innerHTML = `<select class="select">
				<option value="rejected">Reject</option>
				<option value="approved">Approve</option>
				</select>`;

			let td10 = document.createElement('td');
			td10.innerHTML = `<button class="button decision-btn">Submit</button>`
					

			tr.appendChild(td1);
			tr.appendChild(td2);
			tr.appendChild(td3);
			tr.appendChild(td4);
			tr.appendChild(td5);
			tr.appendChild(td6);
			tr.appendChild(td7);
			tr.appendChild(td8);
			tr.appendChild(td9);
			tr.appendChild(td10);

			if (reimbursement.status == 'pending') {
				tbody.appendChild(tr);
			}
		}
	}
}


 */
