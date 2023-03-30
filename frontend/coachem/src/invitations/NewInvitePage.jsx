import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useLocalStorage } from '../common/LocalStorage';
import './NewInvitePage.css';

export default function InvitationsPage({ home, logout }) {
    const [api] = useLocalStorage('api');
    const navigate = useNavigate();
    const [people, setPeople] = useState([]);
    const loggedInPerson = JSON.parse(localStorage.getItem('person'));

    const submit = (e) => {
        e.preventDefault();
        if (people.length < 1) {
            alert('you have to add people to the list to send the invite to');
        }
        if (new Date(e.target[0].value) < new Date()) {
            alert('must use a future date');
            return;
        }
        const body = {
            time: new Date(e.target[0].value + ' ' + e.target[1].value),
            accepted: false,
        };

        people.map((person) => {
            return fetch(
                `${api}/api/invite/new/${loggedInPerson.id}/${person.id}`,
                {
                    method: 'POST',
                    body: JSON.stringify(body),
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem(
                            'access_token'
                        )}`,
                    },
                }
            ).then((resp) => {
                if (resp.ok) {
                    navigate('/invites');
                } else if (resp.status === 401) {
                    logout();
                } else if (resp.status === 403) {
                    home();
                }
                return resp;
            });
        });
    };

    const addPerson = (e) => {
        e.preventDefault();
        fetch(`${api}/api/person/email/${e.target[0].value}`, {
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
            .then((data) => {
                let newperson = true;
                if (data.id === loggedInPerson.id) {
                    alert('cannot add yourself');
                    e.target[0].value = '';
                    return;
                }
                if (data.user.role === 'HR') {
                    alert('cannot add hr employees');
                    e.target[0].value = '';
                    return;
                }
                people.map((person) => {
                    if (person.id === data.id) {
                        alert('person already added');
                        e.target[0].value = '';
                        newperson = false;
                    }
                    return person;
                });
                if (newperson) {
                    setPeople(people.concat(data));
                    e.target[0].value = '';
                }
            });
    };

    return (
        <div className="new-invite-page">
            <h1>New Invite</h1>
            <form
                id="invite-form"
                className="new-invite-form"
                onSubmit={submit}
            >
                <label htmlFor="date">Date</label>
                <input type="date" name="date" id="date" required />

                <label htmlFor="time">Time</label>
                <input type="time" name="time" id="time" required />
            </form>

            <form className="new-person" onSubmit={addPerson}>
                <label htmlFor="email">Add people by email</label>
                <br />
                <div className="invite-addpeople">
                    <input
                        type="email"
                        name="email"
                        id="email"
                        placeholder="email"
                    />
                    <input type="submit" value="Add person" />
                </div>
            </form>

            {people.length !== 0 && (
                <>
                    <h3 className="invite-people">People:</h3>
                    {people.map((person) => (
                        <p key={person.id}>{person.name}</p>
                    ))}

                    <input
                        type="submit"
                        className="input"
                        form="invite-form"
                        value="Send invite"
                    />
                </>
            )}
        </div>
    );
}
