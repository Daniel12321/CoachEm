import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import './SkillPage.css';
import { useLocalStorage } from '../common/LocalStorage';

export default function SkillPage({ home, logout, reloadNotifications }) {
    const [api] = useLocalStorage('api');
    const { id } = useParams();
    const [skill, setSkill] = useState([]);
    const [traineeSkill, setTraineeSkill] = useState([]);
    const [feedback, setFeedback] = useState([]);
    const [progress, setProgress] = useState([]);
    const role = localStorage.getItem('user_role');
    const person = JSON.parse(localStorage.getItem('person'));

    const options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric',
    };

    const trainee = role === 'TRAINEE';

    useEffect(() => {
        fetch(`${api}/api/traineeskill/get/${id}`, {
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
                setSkill(data.skill);
                setTraineeSkill(data);
            })
            .catch((error) => console.log(error));

        fetch(`${api}/api/feedback/traineeskill/${id}`, {
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
                setFeedback(sortByDate(data));
            })
            .catch((error) => console.log(error));

        fetch(`${api}/api/progress/traineeskill/${id}`, {
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
                setProgress(sortByDate(data));
            })
            .catch((error) => console.log(error));
    }, [home, logout, id, api]);

    useEffect(() => {
        if (trainee) {
            fetch(`${api}/api/feedback/seen/${id}`, {
                method: 'PUT',
                headers: {
                    Authorization: `Bearer ${localStorage.getItem(
                        'access_token'
                    )}`,
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
        }
    }, [id, home, logout, reloadNotifications, api, trainee]);

    function addProgress(e) {
        e.preventDefault();
        if (e.target[0].value === '' || e.target[0].value === null) {
            return;
        }
        const newProgress = [
            {
                text: e.target[0].value,
                time: new Date(),
            },
        ];
        let dataJSON = JSON.stringify(newProgress[0]);
        fetch(`${api}/api/progress/new/${id}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
            body: dataJSON,
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
                setProgress(sortByDate(progress.concat(data)));
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
                time: new Date(),
            },
        ];

        let dataJSON = JSON.stringify(newFeedback[0]);
        fetch(`${api}/api/feedback/new/${person.id}/${id}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
            body: dataJSON,
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
                setFeedback(sortByDate(feedback.concat(data)));
            })
            .catch((error) => console.log(error));
        e.target[0].value = '';
    }

    function deleteFeedback(index) {
        fetch(`${api}/api/feedback/delete/${feedback[index].id}`, {
            method: 'DELETE',
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
            .catch((error) => console.log(error));
        setFeedback(feedback.filter((f) => feedback[index] !== f));
    }

    function deleteProgress(index) {
        fetch(`${api}/api/progress/delete/${progress[index].id}`, {
            method: 'DELETE',
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
            })
            .catch((error) => console.log(error));
        setProgress(progress.filter((p) => progress[index] !== p));
    }

    function completeSkill() {
        fetch(`${api}/api/traineeskill/update/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
            body: JSON.stringify({ completed: !traineeSkill.completed }),
        })
            .then((response) => {
                if (response.status === 401) {
                    logout();
                } else if (response.status === 403) {
                    home();
                }
                return response.json();
            })
            .then(setTraineeSkill)
            .catch((error) => console.log(error));
    }

    function sortByDate(arr) {
        return arr.sort((a, b) => new Date(b.time) - new Date(a.time));
    }

    function uploadFile(e) {
        e.preventDefault();

        const formData = new FormData();
        formData.append('file', e.target[0].files[0]);

        fetch(`${api}/api/traineeskill/upload/${id}`, {
            method: 'POST',
            body: formData,
            headers: {
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        }).then((resp) => {
            if (resp.status === 401) {
                logout();
            } else if (resp.status === 403) {
                home();
            } else if (resp.ok) {
                const newTraineeSkill = { ...traineeSkill };
                newTraineeSkill.report = e.target[0].files[0].name;

                setTraineeSkill(newTraineeSkill);
            }
        });
    }

    function downloadFile() {
        fetch(`${api}/api/traineeskill/download/${id}`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        })
            .then((resp) => {
                if (resp.status === 401) {
                    logout();
                } else if (resp.status === 403) {
                    home();
                }
                return resp.blob();
            })
            .then((blob) => {
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = traineeSkill.report;
                document.body.appendChild(a);
                a.click();
                a.remove();
            });
    }

    const typeReportText = skill.type ? 'Certificate' : 'Report';

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
                <p className="singleskill-date">
                    {new Date(skill.time).toLocaleString('en-EN', options)}
                </p>
                <p className="singleskill-desc">
                    <b>Description:</b> {skill.description}
                </p>
                <p>
                    <b>Duration:</b> {skill.duration} hours
                </p>
                <p>
                    <b>Startdate:</b>{' '}
                    {new Date(traineeSkill.time).toLocaleString(
                        'en-EN',
                        options
                    )}
                </p>
                <br />
                <input
                    id="complete"
                    type="checkbox"
                    defaultChecked={traineeSkill.completed}
                    onChange={() => completeSkill()}
                    disabled={!trainee}
                />
                <label htmlFor="complete">
                    {' '}
                    <b>Completed skill</b>
                </label>
            </div>

            <div className="upload">
                {trainee && <h3>Upload {typeReportText}</h3>}
                <div className="upload-box">
                    {trainee && (
                        <form className="upload-form" onSubmit={uploadFile}>
                            <input type="file" name="file" id="file" />
                            <input type="submit" value="Upload" />
                        </form>
                    )}
                    {traineeSkill.report && (
                        <div className="uploaded-file">
                            <div>
                                <h4>Uploaded {typeReportText}</h4>
                                <p>{traineeSkill.report}</p>
                            </div>
                            <div>
                                <button onClick={downloadFile}>Download</button>
                            </div>
                        </div>
                    )}
                </div>
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
                                    {new Date(prog.time).toLocaleString(
                                        'en-EN',
                                        options
                                    )}
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
                                    {new Date(feedb.time).toLocaleString(
                                        'en-EN',
                                        options
                                    )}{' '}
                                    {person.name}
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
