import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useLocalStorage } from '../common/LocalStorage';
import './InfoChangesPage.css';

export default function InfoChangesPage({ home, logout }) {
    const [api] = useLocalStorage('api');
    const [route] = useLocalStorage('route', '');
    const [infoChanges, setInfoChanges] = useState([]);
    const [name, setName] = useState();
    const [email, setEmail] = useState();
    const navigate = useNavigate();

    useEffect(() => {
        fetch(`${api}/api/infochange/all`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        })
            .then((response) => {
                if (response.status === 401) {
                    logout();
                } else if (response.status === 403) {
                    home();
                }
                return response.json();
            })
            .then((data) => setInfoChanges(data));
    }, [home, logout, api]);

    const filteredInfoChanges = infoChanges
        .filter(
            (s) => !(name && !s.name.toLowerCase().includes(name.toLowerCase()))
        )
        .filter(
            (s) =>
                !(email && !s.email.toLowerCase().includes(email.toLowerCase()))
        );

    return (
        <div className="infochanges-page">
            <h1>infoChanges Dashboard</h1>
            <div className="infochanges">
                <div className="infochange-filters">
                    <h2>Filters</h2>

                    <div className="infochange-filter-box">
                        <NameFilter setName={setName} />
                        <EmailFilter setEmail={setEmail} />
                    </div>
                </div>
                <div className="infochange-list">
                    {filteredInfoChanges.map((infoChange) => (
                        <div
                            key={infoChange.id}
                            className="infochange-item"
                            onClick={() => {
                                navigate(
                                    `${route}/infochange-control/${infoChange.id}/${infoChange.person.id}`
                                );
                            }}
                        >
                            <h3>{infoChange.person.name}</h3>
                            <h4>{infoChange.person.user.email}</h4>
                        </div>
                    ))}
                    {filteredInfoChanges.length < 1 && (
                        <p className="emptylist">no infochanges</p>
                    )}
                </div>
            </div>
        </div>
    );
}

function NameFilter({ setName }) {
    return (
        <div className="infochange-filter infochange-filter-name">
            <p>Name</p>
            <input
                type="text"
                name="name"
                id="name"
                onChange={(e) => setName(e.target.value)}
            />
        </div>
    );
}

function EmailFilter({ setEmail }) {
    return (
        <div className="infochange-filter infochange-filter-email">
            <p>Email</p>
            <input
                type="text"
                name="email"
                id="email"
                onChange={(e) => setEmail(e.target.value)}
            />
        </div>
    );
}
