import { useEffect, useState } from 'react';
import './AccountsPage.css';
import { useNavigate } from 'react-router-dom';
import { useLocalStorage } from '../common/LocalStorage';

export default function AccountsPage({ logout }) {
    const [api] = useLocalStorage('api');
    const [accounts, setAccounts] = useState([]);
    const [name, setName] = useState();
    const [email, setEmail] = useState();
    const navigate = useNavigate();

    useEffect(() => {
        async function getAllAccounts() {
            const res = await fetch(`${api}/api/person/all`, {
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
        getAllAccounts();
    }, [logout, api]);

    const filteredAccounts = accounts
        .filter(
            (s) => !(name && !s.name.toLowerCase().includes(name.toLowerCase()))
        )
        .filter(
            (s) =>
                !(email && !s.email.toLowerCase().includes(email.toLowerCase()))
        );

    return (
        <div className="accounts-page">
            <h1>Accounts Dashboard</h1>
            <div className="accounts">
                <div className="account-filters">
                    <h2>Filters</h2>
                    <div className="account-filter-box">
                        <NameFilter setName={setName} />
                        <EmailFilter setEmail={setEmail} />
                    </div>
                </div>
                <div className="account-list">
                    {filteredAccounts.map((account) => (
                        <div
                            key={account.id}
                            className="account-item"
                            onClick={() => {
                                navigate(`/account-view/${account.id}`);
                            }}
                        >
                            <h3>{account.name}</h3>
                            <h4>{account.user.email}</h4>
                        </div>
                    ))}
                    {filteredAccounts.length < 1 && (
                        <p className="emptylist">no accounts</p>
                    )}
                </div>
            </div>
        </div>
    );
}

const NameFilter = ({ setName }) => (
    <div className="account-filter account-filter-name">
        <p>Name</p>
        <input
            type="text"
            name="name"
            id="name"
            onChange={(e) => setName(e.target.value)}
        />
    </div>
);

const EmailFilter = ({ setEmail }) => (
    <div className="account-filter account-filter-email">
        <p>Email</p>
        <input
            type="text"
            name="email"
            id="email"
            onChange={(e) => setEmail(e.target.value)}
        />
    </div>
);
