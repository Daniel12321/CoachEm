import './AccountUpdatePage.css';
import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { useLocalStorage } from '../common/LocalStorage';

export default function AccountUpdatePage({ logout }) {
    const [api] = useLocalStorage('api');
    const [account, setAccounts] = useState([]);
    const { id } = useParams();

    useEffect(() => {
           fetch(`${api}/api/person/${id}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${localStorage.getItem(
                        'access_token'
                    )}`,
                },
            }).then((response) => {
                if (response.status === 401) {
                    logout();
                }
                return response.json();
            })
            .then((data) => setAccounts(data));
    }, [id, logout, api]);

    const updateInfo = async (e) => {
        e.preventDefault();
        const body = {
            name: e.target[0].value,
            address: e.target[1].value,
            city: e.target[2].value,
            zipcode: e.target[3].value,
            phonenumber: e.target[4].value,
        };

        fetch(`${api}/api/person/update/${id}`, {
            method: 'PUT',
            body: JSON.stringify(body),
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        }).then((response) => {
            if (response.status === 401) {
                logout();
            }
            return response.json();
        });
        e.target[5].value = 'saved';
    };

    return (
        <div className="accountpage">
            <h1>update Page for account {id}</h1>
            <div className="personal-info">
                <h2>Personal Details</h2>
                <form onSubmit={updateInfo}>
                    <label htmlFor="name">Name</label>
                    <input
                        type="text"
                        name="name"
                        id="name"
                        defaultValue={account.name}
                    />
                    <label htmlFor="address">Address</label>
                    <input
                        type="text"
                        name="address"
                        id="address"
                        defaultValue={account.address}
                    />
                    <label htmlFor="city">City</label>
                    <input
                        type="text"
                        name="city"
                        id="city"
                        defaultValue={account.city}
                    />
                    <label htmlFor="zipcode">Zipcode</label>
                    <input
                        type="text"
                        name="zipcode"
                        id="zipcode"
                        defaultValue={account.zipcode}
                    />
                    <label htmlFor="phonenumber">Phonenumber</label>
                    <input
                        type="text"
                        name="phonenumber"
                        id="phonenumber"
                        defaultValue={account.phonenumber}
                    />
                    <input
                        className="dark-on-hover"
                        type="submit"
                        value="Save Changes"
                    />
                </form>
            </div>
        </div>
    );
}
