import React from 'react';
import { useNavigate } from 'react-router-dom';

import './NewEvaluationPage.css';

export default function NewEvaluationPage({ logout }) {
    const navigate = useNavigate();

    const submit = (e) => {
        e.preventDefault();

        const body = {
            time: e.target[0].value + ' ' + e.target[1].value,
            email: e.target[2].value,
        };
        console.log(body);

        fetch('http://127.0.0.1:8080/api/evaluation/new', {
            method: 'POST',
            body: JSON.stringify(body),
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        }).then((resp) => {
            if (resp.ok) {
                navigate('/evals');
            } else if (resp.status === 401) {
                logout();
            }
        });
    };

    return (
        <div className="new-eval-page">
            <h1>New Evaluation</h1>
            <form className="new-eval-form" onSubmit={submit}>
                <label htmlFor="date">Date</label>
                <input type="date" name="date" id="date" />

                <label htmlFor="time">Time</label>
                <input type="time" name="time" id="time" />

                <label htmlFor="trainee">Trainee</label>
                <input type="email" name="email" id="email" />
                <input type="submit" value="Submit" />
            </form>
        </div>
    );
}
