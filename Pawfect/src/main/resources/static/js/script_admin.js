function loadSection(section) {
    if (section === 'users') {
        fetch('/api/users')
            .then(res => res.json())
            .then(users => {
                let html = `
                    <h2>User List</h2>
                    <table border="1">
                        <thead>
                            <tr>
                                <th>userId</th>
                                <th>userName</th>
                                <th>userTel</th>
                                <th>userNickname</th>
                                <th>email</th>
                                <th>admin</th>
                                <th>userStatus</th>
                            </tr>
                        </thead>
                        <tbody>`;
                
                users.forEach(user => {
                    html += `
                        <tr>
                            <td>${user.userId}</td>
                            <td>${user.userName}</td>
                            <td>${user.userTel}</td>
                            <td>${user.userNickname}</td>
                            <td>${user.email}</td>
                            <td>${user.admin ? '✔' : '❌'}</td>
                            <td>${user.userStatus}</td>
                        </tr>`;
                });

                html += `</tbody></table>`;
                document.getElementById("main-content").innerHTML = html;
            });
    }
}
