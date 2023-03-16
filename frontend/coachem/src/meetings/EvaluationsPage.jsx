import { useEffect, useState } from 'react';

import './EvaluationsPage.css';

export default function EvaluationsPage() {
    const [trainee, setTrainee] = useState([]);
    const [attendee, setAttendee] = useState([]);

    // String time, PersonDto trainee, PersonDto attendee

    useEffect(() => {
        fetch(`http://127.0.0.1:8080/api/evaluation/trainee`);
        fetch(`http://127.0.0.1:8080/api/evaluation/attendee`);
    }, []);

    return (
        <div className="evalpage">
            <h1>Meetings</h1>

            <div className="trainee-evals">
                {evaluations.map((evalu) => (
                    <Evaluation evaluation={evalu} />
                ))}
            </div>
            <div className="attendee-evals">
                {evaluations.map((evalu) => (
                    <Evaluation evaluation={evalu} />
                ))}
            </div>
            <div className="new-evals"></div>
        </div>
    );
}

const Evaluation = ({ evaluation }) => (
    <div className="evaluation">
        <div className="evaluation-time">
            <h3>{evaluation.time}</h3>
        </div>
        <div className="evaluation-people">
            <div className="evaluation-trainee">
                <h3>{evaluation.trainee.name}</h3>
            </div>
            <div className="evaluation-attendee">
                <h3>{evaluation.attendee.name}</h3>
            </div>
        </div>
    </div>
);
