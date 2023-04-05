import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { useLocalStorage } from '../common/LocalStorage';
import './InvitationsPage.css';

export default function InvitationsPage({ home, logout }) {
    const { id, date } = useParams();
    const [api] = useLocalStorage('api');
    const [route] = useLocalStorage('route', '');
    const [yourInvites, setYourInvites] = useState([]);
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
        fetch(`${api}/api/invite/sent/${id}`, {
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
                setYourInvites(data);
            });
    }, [home, logout, person.id, api, id]);

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
                    <h2>filled in</h2>
                </div>
                {sortByDate(yourInvites)
                    .filter((inv) => inv.accepted)
                    .filter((invite) => invite.time === date)
                    .map((invite) => (
                        <Link
                            key={invite.id}
                            className="invite"
                            to={`${route}/invite/${invite.id}`}
                        >
                            <div className="invite">
                                <div className="invite-trainee">
                                    <h3>Invited:</h3>
                                    <p>{invite.invited.name}</p>
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
                {yourInvites
                    .filter((invite) => invite.accepted)
                    .filter((invite) => invite.time === date).length < 1 && (
                    <p className="emptylist">no invites</p>
                )}
            </div>
            <h2>awaiting</h2>
            {yourInvites
                .filter((invite) => !invite.accepted)
                .filter((invite) => invite.time === date)
                .map((inv) => (
                    <Link
                        key={inv.id}
                        className="invite"
                        to={`${route}/invite/${inv.id}`}
                    >
                        <div className="invite">
                            <div className="invite-trainee">
                                <h3>Invited:</h3>
                                <p>{inv.invited.name}</p>
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
            {yourInvites
                .filter((invite) => !invite.accepted)
                .filter((invite) => invite.time === date).length < 1 && (
                <p className="emptylist">no invites</p>
            )}
        </>
    );
}
