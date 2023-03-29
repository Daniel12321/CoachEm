import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { useCallback } from 'react';
import './SkillsPage.css';
import { useLocalStorage } from '../common/LocalStorage';

export default function SkillsPage({ logout, ownSkills }) {
    const [api] = useLocalStorage('api');
    const { id } = useParams();
    const [skills, setSkills] = useState([]);
    const [traineeSkills, setTraineeSkills] = useState([]);
    const [categories, setCategories] = useState([]);
    const role = localStorage.getItem('user_role');

    const [name, setName] = useState();
    const [type, setType] = useState();
    const [category, setCategory] = useState();
    const [minDuration, setMinDuration] = useState();
    const [maxDuration, setMaxDuration] = useState();

    const person = JSON.parse(localStorage.getItem('person'));

    let trainee;
    if (role === 'TRAINEE') {
        trainee = true;
    } else {
        trainee = false;
    }

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
                }
                return response.json();
            })
            .then((data) => {
                setCategories(data);
            })
            .catch((error) => console.log(error));
    }, [logout, api]);

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
                    }
                    return response.json();
                })
                .then((data) => {
                    setTraineeSkills(data);
                })
                .catch((error) => console.log(error));
        },
        [logout, api]
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
                }
                return response.json();
            })
            .then((data) => {
                setSkills(data);
            })
            .catch((error) => console.log(error));
    }, [ownSkills, logout, api]);

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
                }
                return response.json();
            })
            .then((data) => {
                setTraineeSkills(traineeSkills.concat(data));
            })
            .catch((error) => console.log(error));
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

    return (
        <div className="skills-page">
            <h1>Skills Dashboard</h1>
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
                    {filteredSkills.map((skill) =>
                        ownSkills ? (
                            <Link key={skill.id} to={`/skill/${skill.id}`}>
                                <div className="skill-item">
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
                        ) : (
                            <div key={skill.id} className="skill-item">
                                <h3>{skill.name}</h3>
                                <p>{skill.description}</p>
                                {trainee && completed(skill)}
                            </div>
                        )
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

// Naam
// Type
// Category
// Tijdsduur
// Datum van upload (order)
//
// Recommended ?????
