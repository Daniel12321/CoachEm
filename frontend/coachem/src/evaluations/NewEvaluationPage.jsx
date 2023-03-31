import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useLocalStorage } from '../common/LocalStorage';

import './NewEvaluationPage.css';

export default function NewEvaluationPage({ home, logout }) {
    const [api] = useLocalStorage('api');
    const [route] = useLocalStorage('route', '');
    const navigate = useNavigate();

    const submit = (e) => {
        e.preventDefault();

        if (new Date(e.target[0].value) < new Date()) {
            alert('must use a future date');
            return;
        }

        const body = {
            time: e.target[0].value + ' ' + e.target[1].value,
            email: e.target[2].value,
        };

        fetch(`${api}/api/evaluation/new`, {
            method: 'POST',
            body: JSON.stringify(body),
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        }).then((resp) => {
            if (resp.status === 401) {
                logout();
            } else if (resp.status === 403) {
                home();
            } else if (resp.ok) {
                navigate(`${route}/evals`);
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
