import { useLocalStorage } from '../common/LocalStorage';
import './AccountAddPage.css';

export default function AccountAddPage({ home, logout }) {
    const [api] = useLocalStorage('api');

    function addAccount(e) {
        e.preventDefault();

        const data = {
            name: e.target[0].value,
            email: e.target[1].value,
            city: e.target[2].value,
            address: e.target[3].value,
            zipcode: e.target[4].value,
            phonenumber: e.target[5].value,
            role: e.target[6].value,
            password: generatePassword(),
        };
        let dataJSON = JSON.stringify(data);
        fetch(`${api}/api/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
            body: dataJSON,
        })
            .then((response) => {
                if (response.status === 401) {
                    logout();
                } else if (response.status === 403 || response.ok) {
                    home();
                }
            })
            .catch((error) => console.log(error));
    }

    function generatePassword() {
        const randomPassword = Math.random().toString(36).slice(2);
        return randomPassword;
    }

    return (
        <div className="addAccountsPage">
            <h1>Account Add Page!</h1>
            <div>
                <form
                    id="my-form"
                    className="new-account-form"
                    onSubmit={(e) => addAccount(e)}
                >
                    <label htmlFor="name">Name: </label>
                    <input
                        id="name"
                        type="text"
                        placeholder="name"
                        maxLength={30}
                        required
                    />
                    <label htmlFor="email">Email: </label>
                    <input
                        id="email"
                        type="text"
                        placeholder="email"
                        maxLength={50}
                        required
                    />
                    <div className="account-add-form__flex"></div>
                    <label htmlFor="city">City: </label>
                    <input
                        id="city"
                        type="text"
                        placeholder="city"
                        maxLength={30}
                        required
                    />
                    <label htmlFor="address">Address: </label>
                    <input
                        id="address"
                        type="text"
                        placeholder="address"
                        maxLength={30}
                        required
                    />
                    <label htmlFor="zipcode">Zipcode: </label>
                    <input
                        id="zipcode"
                        type="text"
                        placeholder="zipcode"
                        maxLength={30}
                        required
                    />
                    <label htmlFor="phonenumber">Phonenumber: </label>
                    <input
                        id="phonenumber"
                        type="text"
                        placeholder="phonenumber (optional)"
                        maxLength={15}
                    />
                    <label htmlFor="role">Role: </label>
                    <select name="role" id="role">
                        <option value="TRAINEE">TRAINEE</option>
                        <option value="MANAGER">MANAGER</option>
                        <option value="COACH">COACH</option>
                        <option value="HR">HR</option>
                    </select>
                    <input value="add account" type="submit"></input>
                </form>
            </div>
        </div>
    );
}
