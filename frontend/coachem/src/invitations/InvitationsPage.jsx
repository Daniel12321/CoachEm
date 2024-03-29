import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useLocalStorage } from '../common/LocalStorage';
import './InvitationsPage.css';

export default function InvitationsPage({ home, logout, reloadNotifications }) {
    const [api] = useLocalStorage('api');
    const [route] = useLocalStorage('route', '');
    const [yourInvites, setYourInvites] = useState([]);
    const [invites, setInvites] = useState([]);
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
                } else if (response.status === 403) {
                    home();
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
                } else if (response.status === 403) {
                    home();
                }
                return response.json();
            })
            .then((data) => {
                setInvites(data);
            });
    }, [home, logout, person.id, api]);

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
            {role === 'trainee' && (
                <div>
                    <div className="">
                        <h2>my invites</h2>
                    </div>
                    {sortByDate(yourInvites).map((invite) => (
                        <Link
                            key={invite.id}
                            className="invite"
                            to={`${route}/invite/my-invites/${person.id}/${invite.time}`}
                        >
                            <div className="invite">
                                <div className="invite-trainee">
                                    <h3>trainee:</h3>
                                    <p>{invite.trainee.name}</p>
                                </div>
                            </div>
                            <div>
                                <h3>date:</h3>
                                <p>
                                    {new Date(invite.time).toLocaleDateString(
                                        'en-EN',
                                        options
                                    )}
                                </p>
                            </div>
                        </Link>
                    ))}
                    {yourInvites.concat(
                        invites.filter((invite) => invite.accepted)
                    ).length < 1 && <p className="emptylist">no invites</p>}
                </div>
            )}
            <h2>invites</h2>
            {invites
                .filter((invite) => !invite.accepted)
                .map((inv) => (
                    <Link
                        key={inv.id}
                        className="invite"
                        to={`${route}/invite/${inv.id}`}
                    >
                        <div className="invite">
                            <div className="invite-trainee">
                                <h3>Trainee:</h3>
                                <p>{inv.trainee.name}</p>
                            </div>
                        </div>
                        <div>
                            <h3>date:</h3>
                            <p>
                                {new Date(inv.time).toLocaleDateString(
                                    'en-EN',
                                    options
                                )}
                            </p>
                        </div>
                    </Link>
                ))}
            {invites.filter((invite) => !invite.accepted).length < 1 && (
                <p className="emptylist">no invites</p>
            )}
        </>
    );
}
