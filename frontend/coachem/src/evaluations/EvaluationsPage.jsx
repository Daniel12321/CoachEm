import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useLocalStorage } from '../common/LocalStorage';

import './EvaluationsPage.css';

const options = {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
};

export default function EvaluationsPage({ home, logout, reloadNotifications }) {
    const [api] = useLocalStorage('api');
    const [route] = useLocalStorage('route', '');
    const [trainee, setTrainee] = useState([]);
    const [attendee, setAttendee] = useState([]);

    useEffect(() => {
        fetch(`${api}/api/evaluation/trainee`, {
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
            .then(setTrainee);
        fetch(`${api}/api/evaluation/attendee`, {
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
            .then(setAttendee);
    }, [home, logout, api]);

    useEffect(() => {
        fetch(`${api}/api/evaluation/seen`, {
            method: 'PUT',
            headers: {
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        }).then((resp) => {
            if (resp.status === 401) {
                logout();
            } else if (resp.status === 403) {
                home();
            } else if (resp.ok) {
                reloadNotifications();
            }
        });
    }, [home, logout, reloadNotifications, api]);

    const addAttendee = (id, e) => {
        e.preventDefault();

        const body = { email: e.target[0].value };

        fetch(`${api}/api/evaluation/attendee/${id}`, {
            method: 'POST',
            body: JSON.stringify(body),
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        })
            .then((resp) => {
                if (resp.status === 401) {
                    logout();
                } else if (resp.status === 403) {
                    home();
                } else if (!resp.ok) {
                    throw new Error(resp.statusText);
                }
                return resp.json();
            })
            .then((data) => {
                setAttendee(
                    attendee.filter((a) => a.id !== data.id).concat(data)
                );
            });
    };

    const role = localStorage.getItem('user_role').toLowerCase();

    const evaluationList = role === 'trainee' && (
        <div className="trainee-evaluationss">
            <h2>Your Evaluations</h2>
            {trainee
                .sort((t1, t2) => t1.time - t2.time)
                .map((evalu) => (
                    <Evaluation key={evalu.id} evaluation={evalu} />
                ))}
        </div>
    );

    const attendingList = (
        <div className="attendee-evaluations">
            <h2>Attending</h2>
            {attendee
                .sort((t1, t2) => t1.time - t2.time)
                .map((evalu) => (
                    <Attending
                        key={evalu.id}
                        evaluation={evalu}
                        role={role}
                        addAttendee={addAttendee}
                    />
                ))}
        </div>
    );

    const person = JSON.parse(localStorage.getItem('person'));

    return (
        <div className="evalpage">
            <div className="eval-heading-box">
                <h1>Evaluations</h1>
                <div className="current-user">
                    <h4>
                        Logged in as {person.name} ({role})
                    </h4>
                </div>

                {role !== 'trainee' && (
                    <div>
                        <Link
                            className="new-eval-button"
                            to={`${route}/new-eval`}
                        >
                            New Evaluation
                        </Link>
                    </div>
                )}
            </div>

            {evaluationList}
            {attendingList}
        </div>
    );
}

const Evaluation = ({ evaluation }) => (
    <div className="evaluation">
        <div className="evaluation-time">
            <h3>Time: </h3>
            <p>{new Date(evaluation.time).toLocaleString('en-EN', options)}</p>
        </div>
        <div className="evaluation-attendees">
            <h3>Attendees:</h3>
            {evaluation.attendees.map((attendee) => (
                <div key={attendee.id} className="evaluation-attendee">
                    <p>
                        {attendee.person.name} ({attendee.person.user.email})
                    </p>
                </div>
            ))}
        </div>
    </div>
);

function Attending({ role, evaluation, addAttendee }) {
    return (
        <div className="evaluation">
            <div className="evaluation-info">
                <div className="evaluation-trainee">
                    <h3>Trainee: </h3>
                    <p>
                        {evaluation.trainee.name} (
                        {evaluation.trainee.user.email})
                    </p>
                </div>
                <div className="evaluation-time">
                    <h3>Time: </h3>
                    <p>
                        {new Date(evaluation.time).toLocaleString(
                            'en-EN',
                            options
                        )}
                    </p>
                </div>
            </div>
            <div className="evaluation-attendees">
                <h3>Attendees:</h3>
                {evaluation.attendees.map((attendee) => (
                    <div key={attendee.id} className="evaluation-attendee">
                        <p>
                            {attendee.person.name} ({attendee.person.user.email}
                            )
                        </p>
                    </div>
                ))}
            </div>
            {(role === 'coach' || role === 'manager') && (
                <div className="evaluation-add-attendee">
                    <form onSubmit={(e) => addAttendee(evaluation.id, e)}>
                        <label htmlFor="email">Email</label>
                        <input type="email" name="email" id="email" />
                        <input
                            className="new-attendee-button"
                            type="submit"
                            value="Add attendee"
                        />
                    </form>
                </div>
            )}
        </div>
    );
}
