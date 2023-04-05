import { useEffect, useState, useCallback } from 'react';
import { Link, useParams } from 'react-router-dom';
import { useLocalStorage } from '../common/LocalStorage';
import './SkillsPage.css';

export default function SkillsPage({ home, logout, ownSkills, notifications }) {
    const [api] = useLocalStorage('api');
    const [route] = useLocalStorage('route', '');
    const { id } = useParams();
    const [skills, setSkills] = useState([]);
    const [traineeSkills, setTraineeSkills] = useState([]);
    const [categories, setCategories] = useState([]);
    const [recommendations, setRecommendations] = useState();
    const [trainees, setTrainees] = useState([]);
    const role = localStorage.getItem('user_role');

    const [name, setName] = useState();
    const [type, setType] = useState();
    const [category, setCategory] = useState();
    const [minDuration, setMinDuration] = useState();
    const [maxDuration, setMaxDuration] = useState();

    const person = JSON.parse(localStorage.getItem('person'));
    const trainee = role === 'TRAINEE';

    useEffect(() => {
        fetch(`${api}/api/category/all`, {
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
                setCategories(data);
            })
            .catch((error) => console.log(error));

        if (!trainee) {
            fetch(`${api}/api/person/trainees/forcoach`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${localStorage.getItem(
                        'access_token'
                    )}`,
                },
            })
                .then((response) => {
                    if (response.status === 401) {
                        logout();
                    }
                    return response.json();
                })
                .then((data) => {
                    setTrainees(data);
                })
                .catch((error) => console.log(error));
        }
    }, [home, logout, api, trainee]);

    const getSkillById = useCallback(
        (id) => {
            fetch(`${api}/api/traineeskill/user/${id}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${localStorage.getItem(
                        'access_token'
                    )}`,
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
                    setTraineeSkills(data);
                })
                .catch((error) => console.log(error));
        },
        [home, logout, api]
    );

    useEffect(() => {
        if (id) {
            getSkillById(id);
        } else {
            getSkillById(person.id);
        }
    }, [person.id, id, getSkillById, api]);

    useEffect(() => {
        fetch(`${api}/api/skill/all`, {
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
                setSkills(data);
            })
            .catch((error) => console.log(error));

        fetch(`${api}/api/recommendation/trainee/${person.id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        })
            .then((response) => {
                if (response.status === 401) {
                    logout();
                }
                return response.json();
            })
            .then((data) => {
                setRecommendations(data);
            })
            .catch((error) => console.log(error));
    }, [ownSkills, home, logout, api, person.id]);

    function signUp(skillId) {
        fetch(`${api}/api/traineeskill/new/${person.id}/${skillId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
            body: JSON.stringify({ time: new Date(), completed: false }),
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
                setTraineeSkills(traineeSkills.concat(data));
            })
            .catch((error) => console.log(error));

        let removable;
        let bool = false;
        recommendations.forEach((r) => {
            if (r.skill.id === skillId) {
                bool = true;
                removable = r;
            }
        });
        if (bool) {
            fetch(`${api}/api/recommendation/delete/${removable.id}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${localStorage.getItem(
                        'access_token'
                    )}`,
                },
            })
                .then((response) => {
                    if (response.status === 401) {
                        logout();
                    }
                })
                .catch((error) => console.log(error));
            setRecommendations(
                recommendations.filter((r) => skillId !== r.skill.id)
            );
        }
    }

    function completed(skill) {
        let signedUp = false;
        traineeSkills.map((traineeskill) => {
            if (traineeskill.skill.id === skill.id) {
                signedUp = true;
            }
            return traineeskill;
        });
        return signedUp ? (
            <p className="green">
                <b>signed up</b>
            </p>
        ) : (
            <button className="signup" onClick={() => signUp(skill.id)}>
                sign up
            </button>
        );
    }

    function recommended(skill) {
        let res;
        recommendations.forEach((r) => {
            if (r.skill.id === skill.id) {
                res = <p className="recommended">recommended</p>;
            }
        });
        return res;
    }

    function recommend(skill, train) {
        return (
            <form onSubmit={(e) => recommendSkill(skill, e)}>
                <select name="trainees" id="">
                    <option value=""></option>
                    {trainees.length === skills.length &&
                        train.map((t) => {
                            return (
                                <option key={t.id} value={t.id}>
                                    {t.name}
                                </option>
                            );
                        })}
                </select>
                <button type="submit">recommend</button>
            </form>
        );
    }

    function recommendSkill(skill, e) {
        fetch(
            `${api}/api/recommendation/new/${e.target[0].value}/${skill.id}`,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${localStorage.getItem(
                        'access_token'
                    )}`,
                },
            }
        )
            .then((response) => {
                if (response.status === 401) {
                    logout();
                }
                return response.json();
            })
            .then((data) => {
                setRecommendations(recommendations.concat(data));
            })
            .catch((error) => console.log(error));
    }

    function filterSkills(skill) {
        if (skill !== null) {
            return (
                !(
                    name &&
                    !skill.name.toLowerCase().includes(name.toLowerCase())
                ) &&
                !(category && category !== skill.category.name) &&
                !(minDuration && minDuration > skill.duration) &&
                !(maxDuration && maxDuration < skill.duration)
            );
        }
    }

    const filterTraineeSkills = (traineeSkills) =>
        traineeSkills.filter((s) => filterSkills(s.skill));

    const filterAllSkills = (allSkills) =>
        allSkills.filter((s) => filterSkills(s));

    let filteredSkills = ownSkills
        ? filterTraineeSkills(traineeSkills)
        : filterAllSkills(skills);

    if (type !== 'any') {
        filteredSkills = filteredSkills.filter(
            (s) => !(type && type !== (s.type ? 'hard' : 'soft'))
        );
    }

    if (ownSkills) {
        const filteredCompletedSkills = filteredSkills.filter(
            (s) => s.completed === true
        );
        const filteredSkillsInProgress = filteredSkills.filter(
            (s) => s.completed === false
        );

        filteredSkills = filteredSkillsInProgress.concat(
            filteredCompletedSkills
        );
    }

    function getNotificationCount(skill) {
        if (
            !(trainee && ownSkills && notifications && notifications.feedback)
        ) {
            return 0;
        }

        return notifications.feedback.filter(
            (f) => f.traineeSkill.skill.id === skill.id
        ).length;
    }

    function OwnTaskItem({ skill }) {
        const count = getNotificationCount(skill);
        const badge = count ? (
            <div className="notification-badge">{count}</div>
        ) : (
            ''
        );

        return (
            <Link to={`${route}/skill/${skill.id}`}>
                <div className="skill-item">
                    {badge}

                    <h3>{skill.skill.name}</h3>
                    <p>{skill.skill.description}</p>
                    {skill.completed ? (
                        <p className="green">
                            <b>skill completed</b>
                        </p>
                    ) : (
                        <p className="orange">
                            <b>skill in progress</b>
                        </p>
                    )}
                </div>
            </Link>
        );
    }

    function TaskItem({ skill, index }) {
        return (
            <div key={skill.id} className="skill-item">
                <h3>{skill.name}</h3>
                <p>{skill.description}</p>
                {trainee && completed(skill)}
                {trainee && recommendations && recommended(skill)}
                {role === 'COACH' &&
                    trainees.length === skills.length &&
                    recommend(skill, trainees[index].trainees)}
            </div>
        );
    }

    return (
        <div className="skills-page">
            <div className="skill-heading-box">
                <h1>Skills Dashboard</h1>
                {role !== 'TRAINEE' && (
                    <div>
                        <Link
                            className="new-skill-button"
                            to={`${route}/new-skill`}
                        >
                            New Skill
                        </Link>
                    </div>
                )}
            </div>
            <div className="skills">
                <div className="skill-filters">
                    <h2>Filters</h2>
                    <div className="skill-filter-box">
                        <NameFilter setName={setName} />
                        <TypeFilter setType={setType} />
                        <CategoryFilter
                            setCategory={setCategory}
                            categories={categories}
                        />
                        <DurationFilter
                            setMinDuration={setMinDuration}
                            setMaxDuration={setMaxDuration}
                        />
                    </div>
                </div>
                <div className="skill-list">
                    {filteredSkills.map((skill, index) =>
                        ownSkills ? (
                            <OwnTaskItem key={skill.id} skill={skill} />
                        ) : (
                            <TaskItem
                                key={skill.id}
                                skill={skill}
                                index={index}
                            />
                        )
                    )}
                    {filteredSkills.length < 1 && (
                        <p className="emptylist">no skills</p>
                    )}
                </div>
            </div>
        </div>
    );
}

const NameFilter = ({ setName }) => (
    <div className="skill-filter skill-filter-name">
        <p>Name</p>
        <input
            type="text"
            name="name"
            id="name"
            onChange={(e) => setName(e.target.value)}
        />
    </div>
);

const TypeFilter = ({ setType }) => (
    <div className="skill-filter skill-filter-type">
        <p>Type</p>
        <input
            type="radio"
            name="skill-type"
            id="any"
            value="any"
            onChange={(e) => setType(e.target.value)}
            defaultChecked
        />
        <label htmlFor="any">Any</label>
        <br />
        <input
            type="radio"
            name="skill-type"
            id="soft"
            value="soft"
            onChange={(e) => setType(e.target.value)}
        />
        <label htmlFor="soft">Soft</label>
        <br />
        <input
            type="radio"
            name="skill-type"
            id="hard"
            value="hard"
            onChange={(e) => setType(e.target.value)}
        />
        <label htmlFor="hard">Hard</label>
    </div>
);
const CategoryFilter = ({ setCategory, categories }) => (
    <div className="skill-filter skill-filter-category">
        <p>Category</p>
        <select
            name="category"
            id="category"
            default="any"
            onChange={(e) => setCategory(e.target.value)}
        >
            <option value="" id="any">
                Any
            </option>
            {categories.map((cat) => (
                <option key={cat.id} value={cat.name}>
                    {cat.name}
                </option>
            ))}
        </select>
    </div>
);

const DurationFilter = ({ setMinDuration, setMaxDuration }) => (
    <div className="skill-filter skill-filter-duration">
        <p>Duration (hours)</p>
        <label htmlFor="min-duration">Min</label>
        <input
            type="number"
            id="min-duration"
            defaultValue={0}
            min={0}
            onChange={(e) => setMinDuration(e.target.value)}
        />
        <label htmlFor="max-duration">Max</label>
        <input
            type="number"
            id="max-duration"
            defaultValue={0}
            min={0}
            onChange={(e) => setMaxDuration(e.target.value)}
        />
    </div>
);

// maak knopje voor coach met "recommend" erop, wat iets uitklapt met een select met alle trainees om die skill aan te recommenden
