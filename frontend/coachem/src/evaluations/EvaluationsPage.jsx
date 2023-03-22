import { useEffect, useState } from 'react';

import './EvaluationsPage.css';

export default function EvaluationsPage() {
    const [trainee, setTrainee] = useState([]);
    const [attendee, setAttendee] = useState([]);

    useEffect(() => {
        fetch(`http://127.0.0.1:8080/api/evaluation/trainee`, {
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        })
            .then((resp) => resp.json())
            .then(setTrainee);
        fetch(`http://127.0.0.1:8080/api/evaluation/attendee`, {
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        })
            .then((resp) => resp.json())
            .then(setAttendee);
    }, []);

    return (
        <div className="evalpage">
            <h1>Evaluations</h1>

            <div className="trainee-evaluationss">
                <h2>Your Evaluations</h2>
                {trainee
                    .sort((t1, t2) => t1.time - t2.time)
                    .map((evalu) => (
                        <Evaluation key={evalu.id} evaluation={evalu} />
                    ))}
            </div>
            <div className="attendee-evaluations">
                <h2>Attending</h2>
                {attendee
                    .sort((t1, t2) => t1.time - t2.time)
                    .map((evalu) => (
                        <Attending key={evalu.id} evaluation={evalu} />
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

const Attending = ({ evaluation }) => (
    <div className="evaluation">
        <div className="evaluation-trainee">
            <h3>Trainee: </h3>
            <p>
                {evaluation.trainee.name} ({evaluation.trainee.user.email})
            </p>
        </div>
        <div className="evaluation-time">
            <h3>Time: </h3>
            <p>{evaluation.time}</p>
        </div>
    </div>
);
