import { useLocalStorage } from '../common/LocalStorage';
import './AccountPage.css';

export default function AccountPage(props) {
    const [api] = useLocalStorage('api');
    const person = JSON.parse(localStorage.getItem('person'));
    const updateInfo = (e) => {
        e.preventDefault();

        const body = {
            name: e.target[0].value,
            address: e.target[1].value,
            city: e.target[2].value,
            zipcode: e.target[3].value,
            phonenumber: e.target[4].value,
        };

        fetch(`${api}/api/infochange/new`, {
            method: 'POST',
            body: JSON.stringify(body),
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        }).then((response) => {
            if (response.status === 401) {
                props.logout();
            }
            return response.json();
        });
    };

    const updatePassword = (e) => {
        const body = {
            oldPassword: e.target[0].value,
            newPassword1: e.target[1].value,
            newPassword2: e.target[2].value,
        };

        fetch(`${api}/api/auth/change_password`, {
            method: 'POST',
            body: JSON.stringify(body),
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        }).then((response) => {
            if (response.ok) {
                alert('password changed');
            }
            if (response.status === 401) {
                props.logout();
            }
            if (!response.ok) {
                alert("wrong password or new passwords don't match");
            }
            return response.json();
        });
    };

    return (
        <div className="accountpage">
            <h1>Account Information</h1>
            <div className="personal-info">
                <h2>Personal Details</h2>
                <form onSubmit={updateInfo}>
                    <label htmlFor="name">Name</label>
                    <input
                        type="text"
                        name="name"
                        id="name"
                        defaultValue={person.name}
                    />
                    <label htmlFor="address">Address</label>
                    <input
                        type="text"
                        name="address"
                        id="address"
                        defaultValue={person.address}
                    />
                    <label htmlFor="city">City</label>
                    <input
                        type="text"
                        name="city"
                        id="city"
                        defaultValue={person.city}
                    />
                    <label htmlFor="zipcode">Zipcode</label>
                    <input
                        type="text"
                        name="zipcode"
                        id="zipcode"
                        defaultValue={person.zipcode}
                    />
                    <label htmlFor="phonenumber">Phonenumber</label>
                    <input
                        type="text"
                        name="phonenumber"
                        id="phonenumber"
                        defaultValue={person.phonenumber}
                    />
                    <input type="submit" value="request information change" />
                </form>
            </div>
            <div className="change-password">
                <h2>Change Password</h2>
                <form onSubmit={updatePassword}>
                    <label htmlFor="old-assword">Old Password</label>
                    <input
                        type="password"
                        name="old-password"
                        id="old-password"
                    />
                    <label htmlFor="new-password1">New Password</label>
                    <input
                        type="password"
                        name="new-password1"
                        id="new-password1"
                    />
                    <label htmlFor="new-password2">New Password</label>
                    <input
                        type="password"
                        name="new-password2"
                        id="new-password2"
                    />
                    <input type="submit" value="Change Password" />
                </form>
            </div>
        </div>
    );
}
