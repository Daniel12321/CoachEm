import { useEffect, useState } from 'react';
import './TraineesPage.css';
import { useNavigate, useNavigation } from 'react-router-dom';

export default function TraineesPage() {
    const [trainees, setTrainees] = useState([]);
    const [name, setName] = useState();
    const [email, setEmail] = useState();
    const navigate = useNavigate();

    useEffect(() => {
        getAllTrainees();
    }, []);

    async function getAllTrainees() {
        const res = await fetch('http://localhost:8080/api/person/trainees', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        });
        const data = await res.json();
        setTrainees(data);
    }

    const filteredTrainees = trainees
        .filter(
            (s) => !(name && !s.name.toLowerCase().includes(name.toLowerCase()))
        )
        .filter(
            (s) =>
                !(email && !s.email.toLowerCase().includes(email.toLowerCase()))
        );

    return (
        <div className="trainees-page">
            <h1>Trainees Dashboard</h1>
            <div className="trainees">
                <div className="trainee-filters">
                    <h2>Filters</h2>
                    <div className="trainee-filter-box">
                        <NameFilter setName={setName} />
                        <EmailFilter setEmail={setEmail} />
                    </div>
                </div>
                <div className="trainee-list">
                    {filteredTrainees.map((trainee) => (
                        <div
                            key={trainee.id}
                            className="trainee-item"
                            onClick={() => {
                                navigate(`/skills/${trainee.id}`);
                            }}
                        >
                            <h3>{trainee.name}</h3>
                            <h4>{trainee.user.email}</h4>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

const NameFilter = ({ setName }) => (
    <div className="trainee-filter trainee-filter-name">
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
    <div className="trainee-filter trainee-filter-email">
        <p>Email</p>
        <input
            type="text"
            name="email"
            id="email"
            onChange={(e) => setEmail(e.target.value)}
        />
    </div>
);