import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

import './EvaluationsPage.css';

export default function EvaluationsPage(props) {
    const [trainee, setTrainee] = useState([]);
    const [attendee, setAttendee] = useState([]);

    useEffect(() => {
        fetch(`http://127.0.0.1:8080/api/evaluation/trainee`, {
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        })
            .then((response) => {
                if (response.status === 401) {
                    props.logout();
                }
                return response.json();
            })
            .then(setTrainee);
        fetch(`http://127.0.0.1:8080/api/evaluation/attendee`, {
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        })
            .then((response) => {
                if (response.status === 401) {
                    props.logout();
                }
                return response.json();
            })
            .then(setAttendee);
    }, []);

    const role = localStorage.getItem('user_role').toLowerCase();

    const evaluationList =
        role === 'trainee' ? (
            <div className="trainee-evaluationss">
                <h2>Your Evaluations</h2>
                {trainee
                    .sort((t1, t2) => t1.time - t2.time)
                    .map((evalu) => (
                        <Evaluation key={evalu.id} evaluation={evalu} />
                    ))}
            </div>
        ) : (
            ''
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

                {role !== 'trainee' ? (
                    <div>
                        <Link className="new-eval-button" to="/new-eval">
                            New Evaluation
                        </Link>
                    </div>
                ) : (
                    ''
                )}
            </div>

            {evaluationList}
            <div className="attendee-evaluations">
                <h2>Attending</h2>
                {attendee
                    .sort((t1, t2) => t1.time - t2.time)
                    .map((evalu) => (
                        <Attending
                            key={evalu.id}
                            evaluation={evalu}
                            role={role}
                        />
                    ))}
            </div>
            <div className="new-evaluations"></div>
        </div>
    );
}

const Evaluation = ({ evaluation }) => (
    <div className="evaluation">
        <div className="evaluation-time">
            <h3>Time: </h3>
            <p>{evaluation.time}</p>
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

function Attending({ role, evaluation }) {
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
                    <p>{evaluation.time}</p>
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
            {role === 'coach' || role === 'manager' ? (
                <div className="evaluation-add-attendee">
                    <form>
                        <label htmlFor="email">Email</label>
                        <input type="email" name="email" id="email" />
                        <input
                            className="new-attendee-button"
                            type="submit"
                            value="Add attendee"
                        />
                    </form>
                </div>
            ) : (
                ''
            )}
        </div>
    );
}
