import './AccountUpdatePage.css';
import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function AccountUpdatePage(props) {
    const [account, setAccounts] = useState([]);
    const navigate = useNavigate();
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
                props.logout();
            }
            const data = await res.json();
            setAccounts(data);
        }
        getAccount();
    }, [id]);

    const updateInfo = async (e) => {
        console.log('update');
        e.preventDefault();
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
                        readOnly
                    />
                    <label htmlFor="address" readOnly>
                        Address
                    </label>
                    <input
                        type="text"
                        name="address"
                        id="address"
                        defaultValue={account.address}
                        readOnly
                    />
                    <label htmlFor="city" readOnly>
                        City
                    </label>
                    <input
                        type="text"
                        name="city"
                        id="city"
                        defaultValue={account.city}
                        readOnly
                    />
                    <label htmlFor="zipcode" readOnly>
                        Zipcode
                    </label>
                    <input
                        type="text"
                        name="zipcode"
                        id="zipcode"
                        defaultValue={account.zipcode}
                        readOnly
                    />
                    <label htmlFor="phonenumber" readOnly>
                        Phonenumber
                    </label>
                    <input
                        type="text"
                        name="phonenumber"
                        id="phonenumber"
                        defaultValue={account.phonenumber}
                        readOnly
                    />
                    <input
                        type="submit"
                        value="Enable editing"
                        onClick={() => {
                            navigate(`/account-update/${id}`);
                        }}
                    />
                </form>
            </div>
        </div>
    );
}
