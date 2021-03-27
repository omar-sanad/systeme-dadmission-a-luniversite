const api = 'http://localhost:8080';
let lastDiplome = '';
let principalPdf = '';
let waitingPdf = '';

function login() {
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;
    if (email.toString().trim() === '' || password.toString().trim() === '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Les champs ne doivent pas être vides !',
            showConfirmButton: false,
            timer: 2500
        });
    } else {
        if (email.toString().endsWith(".ac.ma")) {
            axios.post(api + '/api/administrators/login', {
                email: email,
                password: password,
            }).then(function (response2) {
                if (response2.data.code === '1') {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Logged in successfully !',
                        showConfirmButton: false,
                        timer: 2500
                    }).then(() => {
                        cookie.set('token', response2.data.token);
                        cookie.set('id', response2.data.id);
                        cookie.set('type', 'admin');
                        window.location.href = "admin-dashboard/index.html";
                    });
                } else if (response2.data.code === '3') {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'No admin with that email address !',
                        showConfirmButton: false,
                        timer: 2500
                    });
                } else if (response2.data.code === '2') {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Wrong credentials !',
                        showConfirmButton: false,
                        timer: 2500
                    });
                }
            }).catch(function (error) {
                console.log(error);
            });
        } else {
            axios.post(api + '/api/students/login', {
                email: email,
                password: password,
            }).then(function (response) {
                console.log(response.data);
                if (response.data.code === '1') {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Logged in successfully !',
                        showConfirmButton: false,
                        timer: 2500
                    });
                    cookie.set('token', response.data.token);
                    cookie.set('type', 'student');
                    cookie.set('id', response.data.id);
                    window.location.href = "student-dashboard/index.html";
                } else if (response.data.code === '2') {
                    Swal.fire({
                        position: 'center',
                        icon: 'warning',
                        title: 'Please verify your email address !',
                        showConfirmButton: false,
                        timer: 2500
                    });
                } else if (response.data.code === '4') {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'No student with that email address !',
                        showConfirmButton: false,
                        timer: 2500
                    });
                } else if (response.data.code === '3') {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Wrong credentials !',
                        showConfirmButton: false,
                        timer: 2500
                    });
                }
            }).catch(function (error) {
                console.log(error);
            });
        }

    }
}


function register() {
    let firstName = document.getElementById('firstName').value;
    let lastName = document.getElementById('lastName').value;
    let email = document.getElementById('email').value;
    let phoneNumber = document.getElementById('phoneNumber').value;
    let password = document.getElementById('password').value;
    let repeatedPassword = document.getElementById('repeatedPassword').value;

    if (firstName.toString().trim() === '' || lastName.toString().trim() === '' || email.toString().trim() === '' || phoneNumber.toString().trim() === '' || password.toString().trim() === '' || repeatedPassword.toString().trim() === '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Les champs ne doivent pas être vides !',
            showConfirmButton: false,
            timer: 2500
        });
    } else {
        if (password === repeatedPassword) {
            axios.post(api + '/api/students/signup', {
                firstName: firstName,
                lastName: lastName,
                email: email,
                phoneNumber: phoneNumber,
                password: password,
            }).then(function (response) {
                if (response.data.code === '1') {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Votre compte a été créé avec succès, veuillez activer votre email !',
                        showConfirmButton: false,
                        timer: 2500
                    }).then(() => {
                        window.location.href = "index.html";
                    });
                } else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: response.data.message,
                        showConfirmButton: false,
                        timer: 2500
                    });
                }
            }).catch(function (error) {
                console.log(error);
            });
        } else {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'password did not match !',
                showConfirmButton: false,
                timer: 2500
            });
        }

    }
}


function getBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });
}

function addEstablishment() {
    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;
    let type = document.getElementById('type').value;
    var logo = document.querySelector('#establishmentLogo > input[type="file"]').files[0];

    if (name.toString().trim() === '' || type.toString().trim() === '' || description.toString().trim() === '' || logo.toString().trim() === '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Les champs ne doivent pas être vides !',
            showConfirmButton: false,
            timer: 2500
        });
    } else {
        getBase64(logo).then(
            function (data) {
                axios.post(api + '/api/establishments', {
                        name: name,
                        description: description,
                        type: type,
                        logo: data,
                    }, {headers: {"Authorization": cookie.get('token')}}
                ).then(function (response) {
                    console.log(response.data);
                    if (response.data.code === '1') {
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: 'L\'opération s\'est terminée avec succès !',
                            showConfirmButton: false,
                            timer: 2500
                        }).then(() => {
                            window.location.href = "establishments.html";
                        })
                    } else {
                        Swal.fire({
                            position: 'center',
                            icon: 'error',
                            title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                            showConfirmButton: false,
                            timer: 2500
                        });
                    }
                }).catch(function (error) {
                    console.log(error);
                });
            }
        );
    }
}

function getAllEstablishments() {
    axios.get(api + '/api/establishments', {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        for (var i = 0; i < response.data.length; i++) {
            addSingleEstablishment((i + 1), response.data[i]);
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function addAllTheTrainingsToSelectOptions() {
    let allEstablishments = document.getElementById('establishments');
    let allTraining = document.getElementById('speciality');
    axios.get(api + '/api/specialties/establishment/' + allEstablishments.value, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        allTraining.innerHTML = '';
        for (var i = 0; i < response.data.length; i++) {
            let item = '<option value="' + response.data[i].id + '">' + response.data[i].name + '</option>';
            allTraining.innerHTML += item;
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function addAllTheTrainingsToTheSelectOptions() {
    let allEstablishments = document.getElementById('establishments');
    let allTraining = document.getElementById('training');
    axios.get(api + '/api/specialties/trainings/establishment/' + allEstablishments.value, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        allTraining.innerHTML = '';
        for (var i = 0; i < response.data.length; i++) {
            let item = '<option value="' + response.data[i].id + '">' + response.data[i].name + '</option>';
            allTraining.innerHTML += item;
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function addAllTheSpecialtiesToTheSelectOptions() {
    let allTraining = document.getElementById('training');
    let allSpecialties = document.getElementById('speciality');
    axios.get(api + '/api/specialties//specialties/trainings/establishment/' + allTraining.value, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        allSpecialties.innerHTML = '';
        for (var i = 0; i < response.data.length; i++) {
            let item = '<option value="' + response.data[i].id + '">' + response.data[i].name + '</option>';
            allSpecialties.innerHTML += item;
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function addSingleEstablishment(i, element) {
    let allEstablishments = document.getElementById('all-establishments');
    var item = '<tr>' +
        '<td style="text-align: center">' + i + '</td>' +
        '<td style="text-align: center">' + element.name + '</td>' +
        '<td style="text-align: center">' + element.description + '</td>' +
        '<td style="text-align: center">' + element.type + '</td>' +
        '<td style="text-align: center"><button onclick="viewEstablishmentLogo(' + element.id + ')" style="margin-right: 8px;" class="btn btn-waning btn-round">View</button></td>' +
        '<td style="text-align: center">' +
        '<button onclick="deleteEstablishment(' + element.id + ')" style="margin-right: 8px;" class="btn btn-danger btn-round">Remove</button>' +
        '<button onclick="updateEstablishment(' + element.id + ')" style="margin-right: 8px;" class="btn btn-success btn-round">Update</button>' +
        '</td>' +
        '</tr>';

    allEstablishments.innerHTML += item;
}


function getAllStudents() {
    axios.get(api + '/api/students', {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        for (var i = 0; i < response.data.length; i++) {
            addSingleStudent((i + 1), response.data[i]);
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function addSingleStudent(i, element) {
    let all = document.getElementById('all-students');
    var item = '<tr>' +
        '<td style="text-align: center">' + i + '</td>' +
        '<td style="text-align: center">' + element.firstName + '</td>' +
        '<td style="text-align: center">' + element.lastName + '</td>' +
        '<td style="text-align: center">' + element.nationalCode + '</td>' +
        '<td style="text-align: center"><button onclick="downloadDocuments(' + element.id + ')" style="margin-right: 8px;" class="btn btn-waning btn-round">Document</button></td>' +
        '<td style="text-align: center">' +
        '<button onclick="deleteStudent(' + element.id + ')" style="margin-right: 8px;" class="btn btn-danger btn-round">Remove</button>'
    '</td>' +
    '</tr>';

    all.innerHTML += item;
}

function downloadDocuments(id) {
    axios.get(api + '/api/students/' + id, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        let documents = response.data.documents;
        let downloadLink = 'http://localhost:8080/files/uploads/' + documents;
        window.open(downloadLink);
    }).catch(function (error) {
        console.log(error);
    });
}

function deleteStudent(id) {
    Swal.fire({
        title: 'Do you want really to delete this student ?',
        showDenyButton: true,
        showCancelButton: false,
        confirmButtonText: `Delete`,
        denyButtonText: `Close`,
    }).then((result) => {
        if (result.isConfirmed) {
            axios.delete(api + '/api/students/' + id, {
                headers: {"Authorization": cookie.get('token')}
            }).then(function (response) {
                if (response.data.code === '1') {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'L\'opération s\'est terminée avec succès !',
                        showConfirmButton: false,
                        timer: 2500
                    }).then(() => {
                        window.location.reload();
                    });
                }
            }).catch(function (error) {
                console.log(error);
            });
        }
    })
}

function addAllEstablishmentsToSelectOptions() {
    axios.get(api + '/api/establishments', {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        let establishments = document.getElementById('establishments');
        for (var i = 0; i < response.data.length; i++) {
            let option = '<option value="' + response.data[i].id + '">' + response.data[i].name + '</option>';
            establishments.innerHTML += option;
        }
    }).catch(function (error) {
        console.log(error);
    });
}


function addAllTrainingsToSelectOptions() {
    axios.get(api + '/api/trainings', {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        let trainings = document.getElementById('training');
        for (var i = 0; i < response.data.length; i++) {
            let option = '<option value="' + response.data[i].id + '">' + response.data[i].name + '</option>';
            trainings.innerHTML += option;
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function updateEstablishment(id) {
    axios.get(api + '/api/establishments/' + id, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        var name = response.data.name;
        var description = response.data.description;

        let nameInput = document.getElementById('name');
        let descriptionInput = document.getElementById('description');
        let addButton = document.getElementById('addButton');

        nameInput.value = name;
        descriptionInput.value = description;
        addButton.innerText = "Update";
        addButton.setAttribute("onClick", "updateSingleEstablishment(" + id + ");");
    }).catch(function (error) {
        console.log(error);
    });
}

function updateSingleEstablishment(id) {
    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;
    let type = document.getElementById('type').value;
    var logo = document.querySelector('#establishmentLogo > input[type="file"]').files[0];
    getBase64(logo).then(
        function (data) {
            axios.put(api + '/api/establishments', {
                    id: parseInt(id),
                    name: name,
                    description: description,
                    type: type,
                    logo: data,
                }, {headers: {"Authorization": cookie.get('token')}}
            ).then(function (response) {
                console.log(response.data);
                if (response.data.code === '1') {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'L\'opération s\'est terminée avec succès !',
                        showConfirmButton: false,
                        timer: 2500
                    }).then(() => {
                        window.location.href = "establishments.html";
                    })
                } else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                        showConfirmButton: false,
                        timer: 2500
                    });
                }
            }).catch(function (error) {
                console.log(error);
            });
        }
    );
}

function viewEstablishmentLogo(id) {
    axios.get(api + '/api/establishments/' + id, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        let logo = api + "/files/logos/" + response.data.logo;
        Swal.fire({
            imageUrl: logo,
            imageHeight: 200
        });
    }).catch(function (error) {
        console.log(error);
    });
}

function deleteEstablishment(id) {

    Swal.fire({
        title: 'Do you want really to delete this establishment ?',
        showDenyButton: true,
        showCancelButton: false,
        confirmButtonText: `Delete`,
        denyButtonText: `Close`,
    }).then((result) => {
        if (result.isConfirmed) {

            axios.delete(api + '/api/establishments/' + id, {
                headers: {"Authorization": cookie.get('token')}
            }).then(function (response) {
                if (response.data.code === '1') {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'L\'opération s\'est terminée avec succès !',
                        showConfirmButton: false,
                        timer: 2500
                    }).then(() => {
                        window.location.href = "establishments.html";
                    });
                }
            }).catch(function (error) {
                console.log(error);
            });
        }
    })
}


function saveUniversityInformation() {
    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;
    var logo = document.querySelector('#universityLogo > input[type="file"]').files[0];

    if (name.toString().trim() === '' || description.toString().trim() === '' || logo.toString().trim() === '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Les champs ne doivent pas être vides !',
            showConfirmButton: false,
            timer: 2500
        });
    } else {
        getBase64(logo).then(
            function (data) {
                axios.post(api + '/api/establishments/university', {
                        name: name,
                        description: description,
                        logo: data,
                    }, {headers: {"Authorization": cookie.get('token')}}
                ).then(function (response) {
                    console.log(response.data);
                    if (response.data.code === '1') {
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: 'L\'opération s\'est terminée avec succès !',
                            showConfirmButton: false,
                            timer: 2500
                        }).then(() => {
                            window.location.reload();
                        })
                    } else {
                        Swal.fire({
                            position: 'center',
                            icon: 'error',
                            title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                            showConfirmButton: false,
                            timer: 2500
                        });
                    }
                }).catch(function (error) {
                    console.log(error);
                });
            }
        );
    }
}

function getUniversityInformation() {
    axios.get(api + '/api/establishments/university', {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        if (response.data.code === '1') {
            let name = document.getElementById('name');
            let description = document.getElementById('description');
            let universityImg = document.getElementById('universityLogoImg');
            if (response.data.name !== 'undefined' && response.data.description !== 'undefined' && response.data.logo !== 'undefined') {
                name.value = response.data.name;
                description.value = response.data.description;
                universityImg.src = api + '/files/logos/' + response.data.logo;
            }
        }
    }).catch(function (error) {
        console.log(error);
    });
}


function getAdminInformation() {
    let id = cookie.get('id');
    axios.get(api + '/api/administrators/' + id, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        if (response.data.toString() !== '') {
            let firstName = document.getElementById('firstName');
            let lastName = document.getElementById('lastName');
            let identityCode = document.getElementById('identityCode');
            let email = document.getElementById('email');
            firstName.value = response.data.firstName;
            lastName.value = response.data.lastName;
            identityCode.value = response.data.identityCode;
            email.value = response.data.email;
        }
    }).catch(function (error) {
        console.log(error);
    });
}


function updateAdminProfile() {
    let id = cookie.get('id');
    let firstName = document.getElementById('firstName').value;
    let lastName = document.getElementById('lastName').value;
    let identityCode = document.getElementById('identityCode').value;
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;

    if (firstName.toString().trim() === '' || lastName.toString().trim() === '' || identityCode.toString().trim() === '' || email.toString().trim() === '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Les champs ne doivent pas être vides !',
            showConfirmButton: false,
            timer: 2500
        });
    } else {
        axios.put(api + '/api/administrators', {
                id: parseInt(id),
                firstName: firstName,
                lastName: lastName,
                identityCode: identityCode,
                email: email,
                password: password
            }, {headers: {"Authorization": cookie.get('token')}}
        ).then(function (response) {
            console.log(response.data);
            if (response.data.code === '1') {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'L\'opération s\'est terminée avec succès !',
                    showConfirmButton: false,
                    timer: 2500
                }).then(() => {
                    window.location.reload();
                })
            } else {
                Swal.fire({
                    position: 'center',
                    icon: 'error',
                    title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                    showConfirmButton: false,
                    timer: 2500
                });
            }
        }).catch(function (error) {
            console.log(error);
        });
    }
}

function addTraining() {
    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;
    let establishment = document.getElementById('establishments').value;
    let principal = document.getElementById('principal').value;

    if (name.toString().trim() === '' || description.toString().trim() === '' || principal.toString().trim() === '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Les champs ne doivent pas être vides !',
            showConfirmButton: false,
            timer: 2500
        });
    } else {
        axios.post(api + '/api/trainings', {
                name: name,
                description: description,
                establishment: parseInt(establishment),
                studentNumberPrincipalList: parseInt(principal),
            }, {headers: {"Authorization": cookie.get('token')}}
        ).then(function (response) {
            console.log(response.data);
            if (response.data.code === '1') {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'L\'opération s\'est terminée avec succès !',
                    showConfirmButton: false,
                    timer: 2500
                }).then(() => {
                    window.location.href = "trainings.html";
                })
            } else {
                Swal.fire({
                    position: 'center',
                    icon: 'error',
                    title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                    showConfirmButton: false,
                    timer: 2500
                });
            }
        }).catch(function (error) {
            console.log(error);
        });
    }
}

function addScholarship() {
    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;

    if (name.toString().trim() === '' || description.toString().trim() === '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Les champs ne doivent pas être vides !',
            showConfirmButton: false,
            timer: 2500
        });
    } else {
        axios.post(api + '/api/scholarships', {
                name: name,
                description: description
            }, {headers: {"Authorization": cookie.get('token')}}
        ).then(function (response) {
            console.log(response.data);
            if (response.data.code === '1') {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'L\'opération s\'est terminée avec succès !',
                    showConfirmButton: false,
                    timer: 2500
                }).then(() => {
                    window.location.reload();
                })
            } else {
                Swal.fire({
                    position: 'center',
                    icon: 'error',
                    title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                    showConfirmButton: false,
                    timer: 2500
                });
            }
        }).catch(function (error) {
            console.log(error);
        });
    }
}


function addSpecialty() {
    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;
    let training = document.getElementById('training').value;

    if (name.toString().trim() === '' || description.toString().trim() === '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Les champs ne doivent pas être vides !',
            showConfirmButton: false,
            timer: 2500
        });
    } else {
        axios.post(api + '/api/specialties', {
                name: name,
                description: description,
                training: parseInt(training),
            }, {headers: {"Authorization": cookie.get('token')}}
        ).then(function (response) {
            console.log(response.data);
            if (response.data.code === '1') {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'L\'opération s\'est terminée avec succès !',
                    showConfirmButton: false,
                    timer: 2500
                }).then(() => {
                    window.location.reload();
                })
            } else {
                Swal.fire({
                    position: 'center',
                    icon: 'error',
                    title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                    showConfirmButton: false,
                    timer: 2500
                });
            }
        }).catch(function (error) {
            console.log(error);
        });
    }
}


function getAllTrainings() {
    axios.get(api + '/api/trainings', {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        for (var i = 0; i < response.data.length; i++) {
            addSingleTraining((i + 1), response.data[i]);
        }

        for (var i = 0; i < response.data.length; i++) {
            printEstablishmentName(response.data[i].establishment, 'td' + response.data[i].establishment);
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function addSingleTraining(i, element) {
    let all = document.getElementById('all-trainings');
    var item = '<tr>' +
        '<td style="text-align: center">' + i + '</td>' +
        '<td style="text-align: center">' + element.name + '</td>' +
        '<td style="text-align: center">' + element.description + '</td>' +
        '<td id="td' + element.establishment + '" style="text-align: center">' + element.establishment + '</td>' +
        '<td style="text-align: center">' + element.studentNumberPrincipalList + '</td>' +
        '<td style="text-align: center">' +
        '<button onclick="deleteTraining(' + element.id + ')" style="margin-right: 8px;" class="btn btn-danger btn-round">Remove</button>' +
        '<button onclick="updateTraining(' + element.id + ')" style="margin-right: 8px;" class="btn btn-success btn-round">Update</button>' +
        '</td>' +
        '</tr>';

    all.innerHTML += item;
}

function deleteTraining(id) {
    axios.delete(api + '/api/trainings/' + id, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        console.log(response);
        if (response.data.code === '1') {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'L\'opération s\'est terminée avec succès !',
                showConfirmButton: false,
                timer: 2500
            }).then(() => {
                window.location.reload();
            })
        } else {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                showConfirmButton: false,
                timer: 2500
            });
        }
    }).catch(function (error) {
        console.log(error);
    });
}


function updateTraining(id) {
    let addButton = document.getElementById('addButton');
    let name = document.getElementById('name');
    let description = document.getElementById('description');
    let establishment = document.getElementById('establishments');
    let principal = document.getElementById('principal');

    axios.get(api + '/api/trainings/' + id, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        name.value = response.data.name;
        description.value = response.data.description;
        establishment.value = response.data.establishment;
        principal.value = response.data.studentNumberPrincipalList;

        addButton.innerHTML = 'Mettre à jour';
        addButton.setAttribute("onClick", "updateSingleTraining(" + id + ");");

    }).catch(function (error) {
        console.log(error);
    });
}

function updateSingleTraining(id) {
    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;
    let establishment = document.getElementById('establishments').value;
    let principal = document.getElementById('principal').value;

    if (name.toString().trim() === '' || description.toString().trim() === '' || principal.toString().trim() === '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Les champs ne doivent pas être vides !',
            showConfirmButton: false,
            timer: 2500
        });
    } else {
        axios.put(api + '/api/trainings', {
                id: parseInt(id),
                name: name,
                description: description,
                establishment: parseInt(establishment),
                studentNumberPrincipalList: parseInt(principal),
            }, {headers: {"Authorization": cookie.get('token')}}
        ).then(function (response) {
            console.log(response.data);
            if (response.data.code === '1') {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'L\'opération s\'est terminée avec succès !',
                    showConfirmButton: false,
                    timer: 2500
                }).then(() => {
                    window.location.reload();
                })
            } else {
                Swal.fire({
                    position: 'center',
                    icon: 'error',
                    title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                    showConfirmButton: false,
                    timer: 2500
                });
            }
        }).catch(function (error) {
            console.log(error);
        });
    }
}


function getAllSpecialties() {
    axios.get(api + '/api/specialties', {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        for (var i = 0; i < response.data.length; i++) {
            addSingleSpecialty((i + 1), response.data[i]);
        }

        for (var i = 0; i < response.data.length; i++) {
            printSpecialtyName(response.data[i].training, (i + 1) + 'td' + response.data[i].training);
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function addSingleSpecialty(i, element) {
    let all = document.getElementById('all-specialties');
    var item = '<tr>' +
        '<td style="text-align: center">' + i + '</td>' +
        '<td style="text-align: center">' + element.name + '</td>' +
        '<td style="text-align: center">' + element.description + '</td>' +
        '<td id="' + i + 'td' + element.training + '" style="text-align: center">' + element.training + '</td>' +
        '<td style="text-align: center">' +
        '<button onclick="deleteSpecialty(' + element.id + ')" style="margin-right: 8px;" class="btn btn-danger btn-round">Remove</button>' +
        '<button onclick="updateSpecialty(' + element.id + ')" style="margin-right: 8px;" class="btn btn-success btn-round">Update</button>' +
        '</td>' +
        '</tr>';

    all.innerHTML += item;
}

function deleteSpecialty(id) {
    axios.delete(api + '/api/specialties/' + id, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        console.log(response);
        if (response.data.code === '1') {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'L\'opération s\'est terminée avec succès !',
                showConfirmButton: false,
                timer: 2500
            }).then(() => {
                window.location.reload();
            })
        } else {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                showConfirmButton: false,
                timer: 2500
            });
        }
    }).catch(function (error) {
        console.log(error);
    });
}


function updateSpecialty(id) {
    let addButton = document.getElementById('addButton');
    let name = document.getElementById('name');
    let description = document.getElementById('description');
    let training = document.getElementById('training');

    axios.get(api + '/api/specialties/' + id, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        name.value = response.data.name;
        description.value = response.data.description;
        training.value = response.data.training;

        addButton.innerHTML = 'Mettre à jour';
        addButton.setAttribute("onClick", "updateSingleSpecialty(" + id + ");");

    }).catch(function (error) {
        console.log(error);
    });
}

function updateSingleSpecialty(id) {
    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;
    let training = document.getElementById('training').value;

    if (name.toString().trim() === '' || description.toString().trim() === '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Les champs ne doivent pas être vides !',
            showConfirmButton: false,
            timer: 2500
        });
    } else {
        axios.put(api + '/api/specialties', {
                id: parseInt(id),
                name: name,
                description: description,
                training: parseInt(training),
            }, {headers: {"Authorization": cookie.get('token')}}
        ).then(function (response) {
            console.log(response.data);
            if (response.data.code === '1') {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'L\'opération s\'est terminée avec succès !',
                    showConfirmButton: false,
                    timer: 2500
                }).then(() => {
                    window.location.reload();
                })
            } else {
                Swal.fire({
                    position: 'center',
                    icon: 'error',
                    title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                    showConfirmButton: false,
                    timer: 2500
                });
            }
        }).catch(function (error) {
            console.log(error);
        });
    }
}


function getAllScholarships() {
    axios.get(api + '/api/scholarships', {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        for (var i = 0; i < response.data.length; i++) {
            addSingleScholarship((i + 1), response.data[i]);
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function addSingleScholarship(i, element) {
    let all = document.getElementById('all-scholarships');
    var item = '<tr>' +
        '<td style="text-align: center">' + i + '</td>' +
        '<td style="text-align: center">' + element.name + '</td>' +
        '<td style="text-align: center;width: 600px;">' + element.description + '</td>' +
        '<td style="text-align: center">' +
        '<button onclick="deleteScholarship(' + element.id + ')" style="margin-right: 8px;" class="btn btn-danger btn-round">Remove</button>' +
        '<button onclick="updateScholarship(' + element.id + ')" style="margin-right: 8px;" class="btn btn-success btn-round">Update</button>' +
        '</td>' +
        '</tr>';

    all.innerHTML += item;
}


function getAllScholarshipsForStudent() {
    axios.get(api + '/api/scholarships', {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        for (var i = 0; i < response.data.length; i++) {
            addSingleScholarshipForStudent((i + 1), response.data[i]);
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function addSingleScholarshipForStudent(i, element) {
    let all = document.getElementById('all-scholarships');
    var item = '<tr>' +
        '<td style="text-align: center">' + i + '</td>' +
        '<td style="text-align: center">' + element.name + '</td>' +
        '<td style="text-align: center;width: 600px;">' + element.description + '</td>' +
        '</tr>';

    all.innerHTML += item;
}

function deleteScholarship(id) {
    axios.delete(api + '/api/scholarships/' + id, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        console.log(response);
        if (response.data.code === '1') {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'L\'opération s\'est terminée avec succès !',
                showConfirmButton: false,
                timer: 2500
            }).then(() => {
                window.location.reload();
            })
        } else {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                showConfirmButton: false,
                timer: 2500
            });
        }
    }).catch(function (error) {
        console.log(error);
    });
}


function updateScholarship(id) {
    let addButton = document.getElementById('addButton');
    let name = document.getElementById('name');
    let description = document.getElementById('description');

    axios.get(api + '/api/scholarships/' + id, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        name.value = response.data.name;
        description.value = response.data.description;

        addButton.innerHTML = 'Mettre à jour';
        addButton.setAttribute("onClick", "updateSingleScholarship(" + id + ");");

    }).catch(function (error) {
        console.log(error);
    });
}

function updateSingleScholarship(id) {
    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;

    if (name.toString().trim() === '' || description.toString().trim() === '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Les champs ne doivent pas être vides !',
            showConfirmButton: false,
            timer: 2500
        });
    } else {
        axios.put(api + '/api/scholarships', {
                id: parseInt(id),
                name: name,
                description: description,
            }, {headers: {"Authorization": cookie.get('token')}}
        ).then(function (response) {
            console.log(response.data);
            if (response.data.code === '1') {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'L\'opération s\'est terminée avec succès !',
                    showConfirmButton: false,
                    timer: 2500
                }).then(() => {
                    window.location.reload();
                })
            } else {
                Swal.fire({
                    position: 'center',
                    icon: 'error',
                    title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                    showConfirmButton: false,
                    timer: 2500
                });
            }
        }).catch(function (error) {
            console.log(error);
        });
    }
}


function printEstablishmentName(id, tdId) {
    let tdElement = document.getElementById(tdId);
    axios.get(api + '/api/establishments/' + id, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        if (response.data.toString() !== '') {
            let name = response.data.name;
            tdElement.innerHTML = name;
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function printSpecialtyName(id, tdId) {
    let tdElement = document.getElementById(tdId);
    axios.get(api + '/api/trainings/' + id, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        if (response.data.toString() !== '') {
            let name = response.data.name;
            tdElement.innerHTML = name;
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function exportStudentsListAsExcel() {
    axios.post(api + '/api/students/generateExcel', {}, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        console.log(response);
        if (response.data.code === '1') {
            window.open(api + "/files/excel/" + response.data.message);
        } else {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                showConfirmButton: false,
                timer: 2500
            });
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function exportStudentsListAsCSV() {
    axios.post(api + '/api/students/generateCsv', {}, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        console.log(response);
        if (response.data.code === '1') {
            window.open(api + "/files/csv/" + response.data.message);
        } else {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                showConfirmButton: false,
                timer: 2500
            });
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function exportStudentsListAsPDF() {
    axios.post(api + '/api/students/generatePdf', {}, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        console.log(response);
        if (response.data.code === '1') {
            window.open(api + "/files/pdf/" + response.data.message);
        } else {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                showConfirmButton: false,
                timer: 2500
            });
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function logout() {
    cookie.removeSpecific('type', {path: '/'});
    cookie.removeSpecific('id', {path: '/'});
    cookie.removeSpecific('token', {path: '/'});
    Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Déconnecté avec succès !',
        showConfirmButton: false,
        timer: 2500
    }).then(() => {
        window.location.href = "../index.html";
    })
}

function getStudentPersonalInformation() {
    let id = cookie.get('id');
    axios.get(api + '/api/students/' + id, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        if (response.data.toString() !== '') {
            let firstName = document.getElementById('firstName');
            let lastName = document.getElementById('lastName');
            let email = document.getElementById('email');
            let phoneNumber = document.getElementById('phoneNumber');
            let address = document.getElementById('address');
            let birthDate = document.getElementById('birthDate');
            let city = document.getElementById('city');
            let gender = document.getElementById('gender');
            let cne = document.getElementById('cne');
            let cin = document.getElementById('cin');
            let nationality = document.getElementById('nationality');
            let type = document.getElementById('type');
            let lastDiplome = document.getElementById('lastDiplome');
            let fixe = document.getElementById('fixe');

            firstName.value = response.data.firstName;
            lastName.value = response.data.lastName;
            email.value = response.data.email;
            phoneNumber.value = response.data.phoneNumber;
            address.value = response.data.address === "null" ? "" : response.data.address;
            birthDate.value = response.data.birthDate;
            city.value = response.data.city === "null" ? "" : response.data.address;
            gender.value = response.data.gender;
            cne.value = response.data.nationalCode === "null" ? "" : response.data.address;
            cin.value = response.data.identityCode === "null" ? "" : response.data.address;
            nationality.value = response.data.nationality === "null" ? "" : response.data.address;
            type.value = response.data.type;
            lastDiplome.value = response.data.lastDiplome;
            fixe.value = response.data.secondPhoneNumber === "null" ? "" : response.data.address;
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function updateStudentPersonalInformation() {
    let id = cookie.get('id');
    let firstName = document.getElementById('firstName').value;
    let lastName = document.getElementById('lastName').value;
    let email = document.getElementById('email').value;
    let phoneNumber = document.getElementById('phoneNumber').value;
    let address = document.getElementById('address').value;
    let birthDate = document.getElementById('birthDate').value;
    let city = document.getElementById('city').value;
    let gender = document.getElementById('gender').value;
    let cne = document.getElementById('cne').value;
    let cin = document.getElementById('cin').value;
    let nationality = document.getElementById('nationality').value;
    let type = document.getElementById('type').value;
    let lastDiplome = document.getElementById('lastDiplome').value;
    let fixe = document.getElementById('fixe').value;

    if (firstName.toString().trim() === '' || lastName.toString().trim() === '' || email.toString().trim() === '' || phoneNumber.toString().trim() === '' || address.toString().trim() === '' || birthDate.toString().trim() === '' || city.toString().trim() === '' || gender.toString().trim() === '' || cne.toString().trim() === '' || cin.toString().trim() === '' || nationality.toString().trim() === '' || type.toString().trim() === '' || lastDiplome.toString().trim() === '' || fixe.toString().trim() === '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Les champs ne doivent pas être vides !',
            showConfirmButton: false,
            timer: 2500
        });
    } else {
        axios.put(api + '/api/students', {
                id: parseInt(id),
                firstName: firstName,
                lastName: lastName,
                email: email,
                phoneNumber: phoneNumber,
                address: address,
                birthDate: birthDate,
                city: city,
                gender: gender,
                nationalCode: cne,
                identityCode: cin,
                nationality: nationality,
                type: type,
                lastDiplome: lastDiplome,
                secondPhoneNumber: fixe
            }, {headers: {"Authorization": cookie.get('token')}}
        ).then(function (response) {
            console.log(response.data);
            if (response.data.code === '1') {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'L\'opération s\'est terminée avec succès !',
                    showConfirmButton: false,
                    timer: 2500
                }).then(() => {
                    window.location.reload();
                })
            } else {
                Swal.fire({
                    position: 'center',
                    icon: 'error',
                    title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                    showConfirmButton: false,
                    timer: 2500
                });
            }
        }).catch(function (error) {
            console.log(error);
        });
    }
}


function updateStudentGrades() {

    let regional = document.getElementById('regional').value;
    let bac1Year = document.getElementById('bac1Year').value;
    let national = document.getElementById('national').value;
    let bac2Year = document.getElementById('bac2Year').value;
    let semester1 = document.getElementById('semester1').value;
    let semester1Year = document.getElementById('semester1Year').value;
    let semester2 = document.getElementById('semester2').value;
    let semester2Year = document.getElementById('semester2Year').value;
    let semester3 = document.getElementById('semester3').value;
    let semester3Year = document.getElementById('semester3Year').value;
    let semester4 = document.getElementById('semester4').value;
    let semester4Year = document.getElementById('semester4Year').value;
    let semester5 = document.getElementById('semester5').value;
    let semester5Year = document.getElementById('semester5Year').value;
    let semester6 = document.getElementById('semester6').value;
    let semester6Year = document.getElementById('semester6Year').value;
    let semester7 = document.getElementById('semester7').value;
    let semester7Year = document.getElementById('semester7Year').value;
    let semester8 = document.getElementById('semester8').value;
    let semester8Year = document.getElementById('semester8Year').value;
    let semester9 = document.getElementById('semester9').value;
    let semester9Year = document.getElementById('semester9Year').value;
    let semester10 = document.getElementById('semester10').value;
    let semester10Year = document.getElementById('semester10Year').value;

    let boolean1 = regional.toString().trim() === '' ||
        bac1Year.toString().trim() === '' ||
        national.toString().trim() === '' ||
        bac2Year.toString().trim() === '' ||
        semester1.toString().trim() === '' ||
        semester1Year.toString().trim() === '' ||
        semester2.toString().trim() === '' ||
        semester2Year.toString().trim() === '' ||
        semester3.toString().trim() === '' ||
        semester3Year.toString().trim() === '' ||
        semester4.toString().trim() === '' ||
        semester4Year.toString().trim() === '';

    let boolean2 = regional.toString().trim() === '' ||
        bac1Year.toString().trim() === '' ||
        national.toString().trim() === '' ||
        bac2Year.toString().trim() === '' ||
        semester1.toString().trim() === '' ||
        semester1Year.toString().trim() === '' ||
        semester2.toString().trim() === '' ||
        semester2Year.toString().trim() === '' ||
        semester3.toString().trim() === '' ||
        semester3Year.toString().trim() === '' ||
        semester4.toString().trim() === '' ||
        semester4Year.toString().trim() === '' ||
        semester5.toString().trim() === '' ||
        semester5Year.toString().trim() === '' ||
        semester6.toString().trim() === '' ||
        semester6Year.toString().trim() === '';


    let boolean3 = regional.toString().trim() === '' ||
        bac1Year.toString().trim() === '' ||
        national.toString().trim() === '' ||
        bac2Year.toString().trim() === '' ||
        semester1.toString().trim() === '' ||
        semester1Year.toString().trim() === '' ||
        semester2.toString().trim() === '' ||
        semester2Year.toString().trim() === '' ||
        semester3.toString().trim() === '' ||
        semester3Year.toString().trim() === '' ||
        semester4.toString().trim() === '' ||
        semester4Year.toString().trim() === '' ||
        semester5.toString().trim() === '' ||
        semester5Year.toString().trim() === '' ||
        semester6.toString().trim() === '' ||
        semester6Year.toString().trim() === '' ||
        semester7.toString().trim() === '' ||
        semester7Year.toString().trim() === '' ||
        semester8.toString().trim() === '' ||
        semester8Year.toString().trim() === '' ||
        semester9.toString().trim() === '' ||
        semester9Year.toString().trim() === '' ||
        semester10.toString().trim() === '' ||
        semester10Year.toString().trim() === '';

    let test = true;

    switch (lastDiplome) {
        case 'dut':
        case 'deust':
        case 'deug':
        case 'bts':
        case 'dts':
        case 'deup':
        case 'cpge':
            test = boolean1;
            break;
        case 'lp':
        case 'lf':
        case 'lst':
            test = boolean2;
            break;
        default:
            test = boolean3;
    }

    if (test) {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Les champs ne doivent pas être vides !',
            showConfirmButton: false,
            timer: 2500
        });
    } else {
        axios.put(api + '/api/marks', {

                regionalExamMark: parseFloat(regional),
                bac1Year: bac1Year,
                nationalExamMark: parseFloat(national),
                bac2Year: bac2Year,
                semester1ExamMark: parseFloat(semester1),
                semester1Year: semester1Year,
                semester2ExamMark: parseFloat(semester2),
                semester2Year: semester2Year,
                semester3ExamMark: parseFloat(semester3),
                semester3Year: semester3Year,
                semester4ExamMark: parseFloat(semester4),
                semester4Year: semester4Year,
                semester5ExamMark: parseFloat(semester5),
                semester5Year: semester5Year,
                semester6ExamMark: parseFloat(semester6),
                semester6Year: semester6Year,
                semester7ExamMark: parseFloat(semester7),
                semester7Year: semester7Year,
                semester8ExamMark: parseFloat(semester8),
                semester8Year: semester8Year,
                semester9ExamMark: parseFloat(semester9),
                semester9Year: semester9Year,
                semester10ExamMark: parseFloat(semester10),
                semester10Year: semester10Year
            }, {headers: {"Authorization": cookie.get('token')}}
        ).then(function (response) {
            console.log(response.data);
            if (response.data.code === '1') {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'L\'opération s\'est terminée avec succès !',
                    showConfirmButton: false,
                    timer: 2500
                }).then(() => {
                    window.location.reload();
                })
            } else {
                Swal.fire({
                    position: 'center',
                    icon: 'error',
                    title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                    showConfirmButton: false,
                    timer: 2500
                });
            }
        }).catch(function (error) {
            console.log(error);
        });
    }
}


function hideUnnecessaryFields() {

    let year3 = document.getElementById('year3');
    let year4 = document.getElementById('year4');
    let year5 = document.getElementById('year5');

    let id = cookie.get('id');
    axios.get(api + '/api/students/' + id, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        let last = response.data.lastDiplome;
        console.log(last);
        switch (last) {
            case 'dut':
            case 'deust':
            case 'deug':
            case 'bts':
            case 'dts':
            case 'deup':
            case 'cpge':
                year3.style.display = 'none';
                year4.style.display = 'none';
                year5.style.display = 'none';
                break;
            case 'lp':
            case 'lf':
            case 'lst':
                year4.style.display = 'none';
                year5.style.display = 'none';
        }
    }).catch(function (error) {
        console.log(error);
    });


}


function getLastDiplome() {
    let id = cookie.get('id');
    axios.get(api + '/api/students/' + id, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        lastDiplome = response.data.lastDiplome;
    }).catch(function (error) {
        console.log(error);
    });
}


function getStudentGrades() {
    let regional = document.getElementById('regional');
    let bac1Year = document.getElementById('bac1Year');
    let national = document.getElementById('national');
    let bac2Year = document.getElementById('bac2Year');
    let semester1 = document.getElementById('semester1');
    let semester1Year = document.getElementById('semester1Year');
    let semester2 = document.getElementById('semester2');
    let semester2Year = document.getElementById('semester2Year');
    let semester3 = document.getElementById('semester3');
    let semester3Year = document.getElementById('semester3Year');
    let semester4 = document.getElementById('semester4');
    let semester4Year = document.getElementById('semester4Year');
    let semester5 = document.getElementById('semester5');
    let semester5Year = document.getElementById('semester5Year');
    let semester6 = document.getElementById('semester6');
    let semester6Year = document.getElementById('semester6Year');
    let semester7 = document.getElementById('semester7');
    let semester7Year = document.getElementById('semester7Year');
    let semester8 = document.getElementById('semester8');
    let semester8Year = document.getElementById('semester8Year');
    let semester9 = document.getElementById('semester9');
    let semester9Year = document.getElementById('semester9Year');
    let semester10 = document.getElementById('semester10');
    let semester10Year = document.getElementById('semester10Year');


    let id = cookie.get('id');
    axios.get(api + '/api/marks/student/' + id, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        regional.value = response.data.regionalExamMark;
        bac1Year.value = response.data.bac1Year;
        national.value = response.data.nationalExamMark;
        bac2Year.value = response.data.bac2Year;
        switch (lastDiplome) {
            case 'dut':
            case 'deust':
            case 'deug':
            case 'bts':
            case 'dts':
            case 'deup':
            case 'cpge':
                semester1.value = response.data.semester1ExamMark;
                semester1Year.value = response.data.semester1Year;
                semester2.value = response.data.semester2ExamMark;
                semester2Year.value = response.data.semester2Year;
                semester3.value = response.data.semester3ExamMark;
                semester3Year.value = response.data.semester3Year;
                semester4.value = response.data.semester4ExamMark;
                semester4Year.value = response.data.semester4Year;
                break;
            case 'lp':
            case 'lf':
            case 'lst':
                semester1.value = response.data.semester1ExamMark;
                semester1Year.value = response.data.semester1Year;
                semester2.value = response.data.semester2ExamMark;
                semester2Year.value = response.data.semester2Year;
                semester3.value = response.data.semester3ExamMark;
                semester3Year.value = response.data.semester3Year;
                semester4.value = response.data.semester4ExamMark;
                semester4Year.value = response.data.semester4Year;
                semester5.value = response.data.semester5ExamMark;
                semester5Year.value = response.data.semester5Year;
                semester6.value = response.data.semester6ExamMark;
                semester6Year.value = response.data.semester6Year;
                break;
            default:
                semester1.value = response.data.semester1ExamMark;
                semester1Year.value = response.data.semester1Year;
                semester2.value = response.data.semester2ExamMark;
                semester2Year.value = response.data.semester2Year;
                semester3.value = response.data.semester3ExamMark;
                semester3Year.value = response.data.semester3Year;
                semester4.value = response.data.semester4ExamMark;
                semester4Year.value = response.data.semester4Year;
                semester5.value = response.data.semester5ExamMark;
                semester5Year.value = response.data.semester5Year;
                semester6.value = response.data.semester6ExamMark;
                semester6Year.value = response.data.semester6Year;

                semester7.value = response.data.semester7ExamMark;
                semester7Year.value = response.data.semester7Year;
                semester8.value = response.data.semester8ExamMark;
                semester8Year.value = response.data.semester8Year;
                semester9.value = response.data.semester9ExamMark;
                semester9Year.value = response.data.semester9Year;
                semester10.value = response.data.semester10ExamMark;
                semester10Year.value = response.data.semester10Year;
        }
    }).catch(function (error) {
        console.log(error);
    });
}


function getIdentityCardImage() {
    let img = document.getElementById('identityCardImg');
    let id = cookie.get('id');
    axios.get(api + '/api/students/' + id, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        let identityCardImage = response.data.identityCardImage;
        if (identityCardImage !== undefined) {
            let item = '<img src = "' + api + '/files/identityCards/' + identityCardImage + '" width = "500px" />';
            img.innerHTML = item;
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function getStudentDocuments() {
    let link = document.getElementById('documentsLink');
    let id = cookie.get('id');
    axios.get(api + '/api/students/' + id, {
        headers: {"Authorization": cookie.get('token')}
    }).then(function (response) {
        let documents = response.data.documents;
        if (documents !== undefined) {
            let item = '<a href = "' + api + '/files/uploads/' + documents + '" class="btn btn-primary btn-round">Télécharger les documents actuels</a>';
            link.innerHTML = item;
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function uploadIdentityCardImage() {
    var identityCard = document.querySelector('#identityCard > input[type="file"]').files[0];
    getBase64(identityCard).then(
        function (data) {
            axios.post(api + '/api/students/uploadIdentityCard', {
                    identityCardImage: data,
                }, {headers: {"Authorization": cookie.get('token')}}
            ).then(function (response) {
                console.log(response.data);
                if (response.data.code === '1') {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'L\'opération s\'est terminée avec succès !',
                        showConfirmButton: false,
                        timer: 2500
                    }).then(() => {
                        window.location.reload();
                    })
                } else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                        showConfirmButton: false,
                        timer: 2500
                    });
                }
            }).catch(function (error) {
                console.log(error);
            });
        }
    );
}


function uploadStudentDocuments() {
    var documents = document.querySelector('#documents > input[type="file"]').files[0];
    getBase64(documents).then(
        function (data) {
            axios.post(api + '/api/students/upload', {
                    documents: data,
                }, {headers: {"Authorization": cookie.get('token')}}
            ).then(function (response) {
                console.log(response.data);
                if (response.data.code === '1') {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'L\'opération s\'est terminée avec succès !',
                        showConfirmButton: false,
                        timer: 2500
                    }).then(() => {
                        window.location.reload();
                    })
                } else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Une erreur s\'est produite, veuillez réessayer plus tard !',
                        showConfirmButton: false,
                        timer: 2500
                    });
                }
            }).catch(function (error) {
                console.log(error);
            });
        }
    );
}

function goToRegister() {
    window.location.href = "register.html";
}

function sendVerificationCode() {
    //TODO
    let email = document.getElementById('email').value;
    let phoneNumber = document.getElementById('phoneNumber').value;
    let choice = document.getElementById('phoneOrSms').value;
    let id = cookie.get('id');
    axios.post(api + '/api/students/forgetPassword', {
        email: email,
        phoneNumber: phoneNumber,
        choice: choice
    }).then(function (response) {
        if (response.data.code === '1') {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Un code de vérification vous a été envoyé !',
                showConfirmButton: false,
                timer: 2500
            });
        } else {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: response.data.message,
                showConfirmButton: false,
                timer: 2500
            });
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function resetPassword() {
    let email = document.getElementById('email').value;
    let newPassword = document.getElementById('password').value;
    let newPasswordRepeated = document.getElementById('repeatedPassword').value;
    let code = document.getElementById('code').value;
    let id = cookie.get('id');
    axios.post(api + '/api/students/resetPassword', {
        email: email,
        newPassword: newPassword,
        newPasswordRepeated: newPasswordRepeated,
        code: parseInt(code)
    }).then(function (response) {
        if (response.data.code === '1') {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'le mot de passe a été mis à jour avec succès !',
                showConfirmButton: false,
                timer: 2500
            });
        } else {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: response.data.message,
                showConfirmButton: false,
                timer: 2500
            });
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function updateStudentSpeciality() {
    let speciality = document.getElementById('speciality').value;
    let establishment = document.getElementById('establishments').value;
    let id = cookie.get('id');
    axios.put(api + '/api/students/speciality', {
        establishment: establishment,
        speciality: speciality,
        id: id
    }).then(function (response) {
        if (response.data.code === '1') {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Enregistré avec succès!',
                showConfirmButton: false,
                timer: 2500
            });
        } else {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: response.data.message,
                showConfirmButton: false,
                timer: 2500
            });
        }
    }).catch(function (error) {
        console.log(error);
    });
}


function generateAllTheLists() {
    let speciality = document.getElementById('speciality').value;
    let training = document.getElementById('training');
    let establishment = document.getElementById('establishments').value;
    let id = cookie.get('id');
    axios.post(api + '/api/establishments/principalList', {
            speciality: speciality,
            id: training.value,
            name: training.options[training.selectedIndex].text,
            establishment: establishment,
            studentNumberPrincipalList: 1,
        }, {headers: {"Authorization": cookie.get('token')}}
    ).then(function (response) {
        if (response.data.code === '1') {
            principalPdf = response.data.message;
            axios.post(api + '/api/establishments/waitingList', {
                    speciality: speciality,
                    id: training.value,
                    name: training.options[training.selectedIndex].text,
                    establishment: establishment,
                    studentNumberPrincipalList: 1,
                }, {headers: {"Authorization": cookie.get('token')}}
            ).then(function (response) {
                if (response.data.code === '1') {
                    waitingPdf = response.data.message;
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Les listes ont été générées avec succés !',
                        showConfirmButton: false,
                        timer: 2500
                    });
                } else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: response.data.message,
                        showConfirmButton: false,
                        timer: 2500
                    });
                }
            }).catch(function (error) {
                console.log(error);
            });
        } else {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: response.data.message,
                showConfirmButton: false,
                timer: 2500
            });
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function showPrincipalList() {
    window.open(api + "/lists/" + principalPdf);
}

function showWaitingList() {
    window.open(api + "/lists/" + waitingPdf);
}

function showThePrincipalList(){
    principalPdf = 'ESTSB-LP-PRINCIPAL.pdf';
    window.open(api + "/lists/" + principalPdf);
}

function showTheWaitingList(){
    waitingPdf = 'ESTSB-LP-ATTENTE.pdf';
    window.open(api + "/lists/" + waitingPdf);
}