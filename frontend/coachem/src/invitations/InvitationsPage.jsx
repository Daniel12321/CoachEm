import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useLocalStorage } from '../common/LocalStorage';
import './InvitationsPage.css';

export default function InvitationsPage({ logout, reloadNotifications }) {
    const [api] = useLocalStorage('api');
    const [route] = useLocalStorage('route', '');
    const [yourInvites, setYourInvites] = useState([]);
    const [invites, setInvites] = useState([]);
    const [reload, setReload] = useState(false);
    const role = localStorage.getItem('user_role').toLowerCase();
    const person = JSON.parse(localStorage.getItem('person'));
    const options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric',
    };

    useEffect(() => {
        fetch(`${api}/api/invite/sent/${person.id}`, {
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
            .then((data) => {
                const uniqueDates = {};
                const filteredData = data.filter((item) => {
                    if (!uniqueDates[item.time]) {
                        uniqueDates[item.time] = true;
                        return true;
                    }
                    return false;
                });
                setYourInvites(filteredData);
            });
        fetch(`${api}/api/invite/received/${person.id}`, {
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
            .then((data) => {
                setInvites(data);
            });
    }, [logout, person.id, reload, api]);

    function acceptInvite(id) {
        fetch(`${api}/api/invite/accept/${id}`, {
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
            })
            .then(() => reloadNotifications())
            .then(() => setReload(!reload));
    }

    function denyInvite(id) {
        fetch(`${api}/api/invite/delete/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        })
            .then((response) => {
                if (response.status === 401) {
                    logout();
                }
            })
            .then(() => reloadNotifications())
            .then(() => setReload(!reload));
    }

    function sortByDate(arr) {
        return arr.sort((a, b) => new Date(a.time) - new Date(b.time));
    }

    return (
        <>
            <div className="invite-header">
                <h1>360 degrees</h1>
                {role === 'trainee' && (
                    <div>
                        <Link
                            className="new-invite-button"
                            to={`${route}/new-invite`}
                        >
                            New Invite
                        </Link>
                    </div>
                )}
            </div>
            <div>
                <div className="">
                    <h2>appointments</h2>
                </div>
                {sortByDate(
                    yourInvites.concat(
                        invites.filter((invite) => invite.accepted)
                    )
                ).map((invite) => (
                    <div key={invite.id} className="invite">
                        <div className="invite-time">
                            <h3>Time: </h3>
                            <p>
                                {new Date(invite.time).toLocaleString(
                                    'en-EN',
                                    options
                                )}
                            </p>
                        </div>
                        <div className="invite-trainee">
                            <h3>Trainee:</h3>
                            <p>{invite.trainee.name}</p>
                        </div>
                    </div>
                ))}
                {yourInvites.concat(invites.filter((invite) => invite.accepted))
                    .length < 1 && <p className="emptylist">no invites</p>}
            </div>
            <h2>invites</h2>
            {invites
                .filter((invite) => !invite.accepted)
                .map((inv) => (
                    <div key={inv.id} className="invite">
                        <div className="invite-time">
                            <h3>Time: </h3>
                            <p>
                                {new Date(inv.time).toLocaleString(
                                    'en-EN',
                                    options
                                )}
                            </p>
                        </div>
                        <div className="invite-trainee">
                            <h3>Trainee:</h3>
                            <p>{inv.trainee.name}</p>
                        </div>
                        <div className="accept">
                            <img
                                src="./../../img/checkmark.png"
                                alt="checkmark"
                                onClick={() => acceptInvite(inv.id)}
                            />
                            <img
                                src="./../../img/cross.png"
                                alt="cross"
                                onClick={() => denyInvite(inv.id)}
                            />
                        </div>
                    </div>
                ))}
            {invites.filter((invite) => !invite.accepted).length < 1 && (
                <p className="emptylist">no invites</p>
            )}
        </>
    );
}
