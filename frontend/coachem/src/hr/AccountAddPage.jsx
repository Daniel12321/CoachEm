import { useState } from 'react';

export default function AccountAddPage() {
    const [password, setPassword] = useState(generatePassword());

    function addAccount(e) {
        e.preventDefault();
        const data = {
            name: e.target[0].value,
            email: e.target[1].value,
            password: e.target[2].value,
            city: e.target[3].value,
            address: e.target[4].value,
            zipcode: e.target[5].value,
            phonenumber: e.target[6].value,
            role: e.target[7].value,
        };
        let dataJSON = JSON.stringify(data);
        fetch(`http://localhost:8080/api/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
            body: dataJSON,
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to add account');
                }
            })
            .catch((error) => console.log(error));
    }

    function refreshPassword() {
        setPassword(generatePassword());
    }

    function generatePassword() {
        const randomPassword = Math.random().toString(36).slice(2);
        return randomPassword;
    }

    return (
        <div className="addAccountsPage">
            <h1>Account Add Page!</h1>
            <div>
                <form id="my-form" onSubmit={(e) => addAccount(e)}>
                    <label htmlFor="name">Name: </label>
                    <input
                        id="name"
                        type="text"
                        placeholder="name"
                        maxLength={30}
                        required
                    />{' '}
                    <br />
                    <label htmlFor="email">Email: </label>
                    <input
                        id="email"
                        type="text"
                        placeholder="email"
                        maxLength={50}
                        required
                    />{' '}
                    <br />
                    <label htmlFor="password">Password: </label>
                    <input
                        id="password"
                        type="password"
                        value={password}
                        maxLength={20}
                        minLength={8}
                        readOnly
                        required
                    />{' '}
                    <img
                        src=""
                        alt="refresh"
                        onClick={() => refreshPassword()}
                    ></img>
                    <br />
                    <label htmlFor="city">City: </label>
                    <input
                        id="city"
                        type="text"
                        placeholder="city"
                        maxLength={30}
                        required
                    />{' '}
                    <br />
                    <label htmlFor="address">Address: </label>
                    <input
                        id="address"
                        type="text"
                        placeholder="address"
                        maxLength={30}
                        required
                    />{' '}
                    <br />
                    <label htmlFor="zipcode">Zipcode: </label>
                    <input
                        id="zipcode"
                        type="text"
                        placeholder="zipcode"
                        maxLength={30}
                        required
                    />{' '}
                    <br />
                    <label htmlFor="phonenumber">Phonenumber: </label>
                    <input
                        id="phonenumber"
                        type="text"
                        placeholder="phonenumber (optional)"
                        maxLength={15}
                    />{' '}
                    <br />
                    <label htmlFor="role">Role: </label>
                    <select name="role" id="role">
                        <option value="TRAINEE">TRAINEE</option>
                        <option value="MANAGER">MANAGER</option>
                        <option value="COACH">COACH</option>
                        <option value="HR">HR</option>
                    </select>{' '}
                    <br />
                    <button type="submit">add account</button>
                </form>
            </div>
        </div>
    );
}
