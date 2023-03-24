import './InfoChangeControllPage.css';
import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import {Components3} from '../common/Components';

export default function AccountUpdatePage({ logout }) {
    const [account, setAccounts] = useState([]);
    const { id } = useParams();

    useEffect(() => {
        async function getAccount() {
            const res = await fetch(`http://localhost:8080/api/person/${id}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${localStorage.getItem(
                        'access_token'
                    )}`,
                },
            });
            if (res.status === 401) {
                logout();
            }
            const data = await res.json();
            setAccounts(data);
        }
        getAccount();
    }, [id, logout]);

    const updateInfo = async (e) => {
        e.preventDefault();
        const body = {
            name: e.target[0].value,
            address: e.target[1].value,
            city: e.target[2].value,
            zipcode: e.target[3].value,
            phonenumber: e.target[4].value,
        };

        fetch(`http://127.0.0.1:8080/api/person/update/${id}`, {
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
            <Components3 account={account} updateInfo={updateInfo} />
             {/* <div className="personal-info">
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
            </div>   */}
        </div>
    );
}
