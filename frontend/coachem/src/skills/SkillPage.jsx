import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './SkillPage.css';

export default function SkillPage() {
    const { id } = useParams();
    const [skill, setSkill] = useState([]);
    const [feedback, setFeedback] = useState([
        { text: 'good job', time: '03-03-2023' },
        { text: 'great work!', time: '04-03-2023' },
    ]);
    const [progress, setProgress] = useState([]);
    const yoyo = false;

    useEffect(() => {
        setSkill({
            id: 4,
            type: 'hard',
            name: 'Learn Spring Boot',
            category: 'java',
            duration: 7,
            time: '07-03-2020',
            description:
                'there is no description yet but eventually we will have one i promise',
        });
    }, []);

    function addProgress(e) {
        e.preventDefault();
        if (e.target[0].value !== '' && e.target[0].value !== null) {
            setProgress([e.target[0].value].concat(progress));
        }
        e.target[0].value = '';
    }
    return (
        <div className="skill-page">
            <div className="skill-page__info">
                <h1>{skill.name}</h1>
                <p className="singleskill-cat">category: {skill.category}</p>
                <p>
                    <b>Type:</b> {skill.type} skill
                </p>
                <p className="singleskill-date">{skill.time}</p>
                <p className="singleskill-desc">
                    <b>Description:</b> {skill.description}
                </p>
                <p>
                    <b>Duration:</b> {skill.duration} hours
                </p>
            </div>
            <div className="flex-container">
                <div>
                    <h2>Progress</h2>
                    <div
                        className={
                            'flex-item ' +
                            (yoyo ? 'comment-items' : 'nocomment-items')
                        }
                    >
                        {progress.map((prog, index) => (
                            <div key={index} className="flex-singleitem">
                                <p className="text">{prog}</p>
                                <button
                                    onClick={() =>
                                        setProgress(
                                            progress.filter(
                                                (p) => progress[index] !== p
                                            )
                                        )
                                    }
                                >
                                    remove
                                </button>
                            </div>
                        ))}
                    </div>
                    {yoyo && (
                        <form id="commentform" onSubmit={(e) => addProgress(e)}>
                            <textarea
                                name="Text1"
                                rows="4"
                                form="commentform"
                                placeholder={yoyo ? 'progress' : 'feedback'}
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
                            (!yoyo ? 'comment-items' : 'nocomment-items')
                        }
                    >
                        {feedback.map((feedback, index) => (
                            <div key={index} className="flex-singleitem">
                                <p
                                    className={
                                        !yoyo
                                            ? 'comment-date'
                                            : 'nocomment-date'
                                    }
                                >
                                    {feedback.time}
                                </p>
                                <p className="text">{feedback.text}</p>
                            </div>
                        ))}
                    </div>
                    {!yoyo && (
                        <form id="commentform" onSubmit={(e) => addProgress(e)}>
                            <textarea
                                name="Text1"
                                rows="4"
                                form="commentform"
                                placeholder={yoyo ? 'progress' : 'feedback'}
                            ></textarea>{' '}
                            <br />
                            <button id="commentAddButton" type="submit">
                                add progress comment
                            </button>
                        </form>
                    )}
                </div>
            </div>
        </div>
    );
}
