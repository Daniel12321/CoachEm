import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import './SkillPage.css';

export default function SkillPage() {
    const { id } = useParams();
    const [skill, setSkill] = useState([]);
    const [traineeSkill, setTraineeSkill] = useState([]);
    const [feedback, setFeedback] = useState([]);
    const [progress, setProgress] = useState([]);
    const role = localStorage.getItem('user_role');
    const person = JSON.parse(localStorage.getItem('person'));

    let trainee;
    if (role === 'TRAINEE') {
        trainee = true;
    } else {
        trainee = false;
    }

    useEffect(() => {
        fetch(`http://localhost:8080/api/traineeskill/get/${id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((resp) => resp.json())
            .then((data) => {
                setSkill(data.skill);
                setTraineeSkill(data);
            })
            .catch((error) => console.log(error));
    }, []);

    useEffect(() => {
        fetch(`http://localhost:8080/api/feedback/traineeskill/${id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((resp) => resp.json())
            .then(setFeedback)
            .catch((error) => console.log(error));
    }, []);

    useEffect(() => {
        fetch(`http://localhost:8080/api/progress/traineeskill/${id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((resp) => resp.json())
            .then(setProgress)
            .catch((error) => console.log(error));
    }, []);

    function addProgress(e) {
        e.preventDefault();
        if (e.target[0].value === '' || e.target[0].value === null) {
            return;
        }
        const newProgress = [
            {
                text: e.target[0].value,
                time: new Date().toLocaleDateString(),
            },
        ];
        let dataJSON = JSON.stringify(newProgress[0]);
        fetch(`http://localhost:8080/api/progress/new/${id}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: dataJSON,
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to add feedbacks');
                }
                return response.json();
            })
            .then((data) => {
                setProgress(progress.concat(data));
            })
            .catch((error) => console.log(error));
        e.target[0].value = '';
    }

    function addFeedback(e) {
        e.preventDefault();
        if (e.target[0].value === '' || e.target[0].value === null) {
            return;
        }
        const newFeedback = [
            {
                text: e.target[0].value,
                time: new Date().toLocaleDateString(),
            },
        ];

        let dataJSON = JSON.stringify(newFeedback[0]);
        fetch(`http://localhost:8080/api/feedback/new/${person.id}/${id}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: dataJSON,
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to add feedbacks');
                }
                return response.json();
            })
            .then((data) => {
                setFeedback(feedback.concat(data));
            })
            .catch((error) => console.log(error));
        e.target[0].value = '';
    }

    function deleteFeedback(index) {
        fetch(
            `http://localhost:8080/api/feedback/delete/${feedback[index].id}`,
            {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
            }
        )
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to remove feedback');
                }
            })
            .catch((error) => console.log(error));
        setFeedback(feedback.filter((f) => feedback[index] !== f));
    }

    function deleteProgress(index) {
        fetch(
            `http://localhost:8080/api/progress/delete/${progress[index].id}`,
            {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
            }
        )
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to remove progress');
                }
            })
            .catch((error) => console.log(error));
        setProgress(progress.filter((p) => progress[index] !== p));
    }
    if (!skill || !skill.category) {
        return '';
    }
    return (
        <div className="skill-page">
            <div className="skill-page__info">
                <h1>{skill.name}</h1>
                <p className="singleskill-cat">
                    category: {skill.category.name}
                </p>
                <p>
                    <b>Type:</b> {skill.type ? 'hard' : 'soft'} skill
                </p>
                <p className="singleskill-date">{skill.time}</p>
                <p className="singleskill-desc">
                    <b>Description:</b> {skill.description}
                </p>
                <p>
                    <b>Duration:</b> {skill.duration} hours
                </p>
                <p>
                    <b>Startdate:</b> {traineeSkill.time}
                </p>
            </div>
            <div className="flex-container">
                <div>
                    <h2>Progress</h2>
                    <div
                        className={
                            'flex-item ' +
                            (trainee ? 'comment-items' : 'nocomment-items')
                        }
                    >
                        {progress.map((prog, index) => (
                            <div key={index} className="flex-singleitem">
                                <p
                                    className={
                                        trainee
                                            ? 'comment-date'
                                            : 'nocomment-date'
                                    }
                                >
                                    {prog.time}
                                </p>
                                <p className="text">{prog.text}</p>
                                {trainee && (
                                    <button
                                        onClick={() => deleteProgress(index)}
                                    >
                                        remove
                                    </button>
                                )}
                            </div>
                        ))}
                    </div>
                    {trainee && (
                        <form id="commentform" onSubmit={(e) => addProgress(e)}>
                            <textarea
                                name="Text1"
                                rows="4"
                                form="commentform"
                                placeholder={trainee ? 'progress' : 'feedback'}
                            ></textarea>{' '}
                            <br />
                            <button id="commentAddButton" type="submit">
                                add progress comment
                            </button>
                        </form>
                    )}
                </div>
                <div>
                    <h2>Feedback</h2>
                    <div
                        className={
                            'flex-item ' +
                            (!trainee ? 'comment-items' : 'nocomment-items')
                        }
                    >
                        {feedback.map((feedb, index) => (
                            <div key={index} className="flex-singleitem">
                                <p
                                    className={
                                        !trainee
                                            ? 'comment-date'
                                            : 'nocomment-date'
                                    }
                                >
                                    {feedb.time} {person.name}
                                </p>
                                <p className="text">{feedb.text}</p>
                                {!trainee && (
                                    <button
                                        onClick={() => deleteFeedback(index)}
                                    >
                                        remove
                                    </button>
                                )}
                            </div>
                        ))}
                    </div>
                    {!trainee && (
                        <form id="commentform" onSubmit={(e) => addFeedback(e)}>
                            <textarea
                                name="Text1"
                                rows="4"
                                form="commentform"
                                placeholder="feedback"
                            ></textarea>{' '}
                            <br />
                            <button id="commentAddButton" type="submit">
                                add feedback comment
                            </button>
                        </form>
                    )}
                </div>
            </div>
        </div>
    );
}
