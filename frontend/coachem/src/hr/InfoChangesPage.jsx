import { useEffect, useState } from 'react';
import './InfoChangesPage.css';
import { useNavigate } from 'react-router-dom';
import {Components, Components2} from '../common/Components.jsx';


export default function InfoChangesPage({ logout }) {
    const [infoChanges, setInfoChanges] = useState([]);
    const [name, setName] = useState();
    const [email, setEmail] = useState();
    const navigate = useNavigate();

    useEffect(() => {
        async function getAllInfoChanges() {
            await fetch('http://localhost:8080/api/infochange/all', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${localStorage.getItem('access_token')}`,
                },
            })
                .then((response) => {
                    if (response.status === 401) {
                        logout();
                    }
                    return response.json();
                })
                .then((data) => setInfoChanges(data));
        }

        getAllInfoChanges();
    }, [logout]);



     const filteredInfoChanges = infoChanges
        .filter(
            (s) => !(name && !s.name.toLowerCase().includes(name.toLowerCase()))
        )
        .filter(
            (s) =>
                !(email && !s.email.toLowerCase().includes(email.toLowerCase()))
        );

    return (
        <div className="infoChanges-page">
            <h1>infoChanges Dashboard</h1>
            <div className="infoChanges">
                <div className="infoChange-filters">
                    <h2>Filters</h2>
                   
                    <div className="infoChange-filter-box">
                        <Components setName={setName}/>
                        <Components2 setEmail={setEmail}/>
                         {/* <NameFilter setName={setName} />
                        <EmailFilter setEmail={setEmail} />  */}
                    </div>
                </div>
                <div className="infoChange-list">
                    {filteredInfoChanges.map((infoChange) => (
                        <div
                            key={infoChange.id}
                            className="infoChange-item"
                            onClick={() => {
                                navigate(`/infoChange-controll/${infoChange.id}/${infoChange.person.id}`);
                            }}
                        >    
                            <h3>Infochange ID: {infoChange.id}</h3>
                            <h4>{infoChange.person.name}</h4>
                            <h5>{infoChange.personemail}</h5>  
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

        // const NameFilter = ({ setName }) => (
        //     <div className="infoChange-filter infoChange-filter-name">
        //         <p>Name</p>
        //         <input
        //             type="text"
        //             name="name"
        //             id="name"
        //             onChange={(e) => setName(e.target.value)}
        //         />
        //     </div>
        // );

        // const EmailFilter = ({ setEmail }) => (
        //     <div className="infoChange-filter trainee-filter-email">
        //         <p>Email</p>
        //         <input
        //             type="text"
        //             name="email"
        //             id="email"
        //             onChange={(e) => setEmail(e.target.value)}
        //         />
        //     </div>
        // );