$(() => {
    $.getJSON('/user', userAvt => {
        buildTable(userAvt)
    })
    const buildTable = (user) => {
        const bodyTable = document.querySelector(`tbody`)
        bodyTable.innerHTML += `<tr id="tr${user.id}">
                                                <td>${user.firstName}</td>
                                                <td>${user.lastName}</td>
                                                <td>${user.age}</td>
                                                <td>${user.email}</td>
                                                <td>${user.username}</td>
                                                <td>${user.roles.map(role => role.name.replace("ROLE_", "")).join(" , ")}</td>
                                            </tr>`
    }
});