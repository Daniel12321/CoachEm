import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import './SkillsPage.css';

export default function SkillsPage(props) {
    const [skills, setSkills] = useState([]);
    const [traineeSkills, setTraineeSkills] = useState([]);
    const [categories, setCategories] = useState([]);
    const [ownSkills, setOwnSkills] = useState();

    const [name, setName] = useState();
    const [type, setType] = useState();
    const [category, setCategory] = useState();
    const [minDuration, setMinDuration] = useState();
    const [maxDuration, setMaxDuration] = useState();

    const person = JSON.parse(localStorage.getItem('person'));

    if (props.ownSkills !== ownSkills) {
        setOwnSkills(props.ownSkills);
    }

    useEffect(() => {
        fetch(`http://localhost:8080/api/category/all`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((resp) => resp.json())
            .then((data) => {
                setCategories(data);
            })
            .catch((error) => console.log(error));
    }, []);

    useEffect(() => {
        if (ownSkills) {
            fetch(`http://localhost:8080/api/traineeskill/user/${person.id}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            })
                .then((resp) => resp.json())
                .then((data) => {
                    setTraineeSkills(data);
                })
                .catch((error) => console.log(error));
        } else {
            fetch(`http://localhost:8080/api/skill/all`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            })
                .then((resp) => resp.json())
                .then((data) => {
                    setSkills(data);
                })
                .catch((error) => console.log(error));
        }
    }, [ownSkills]);

    function filterSkills(skill) {
        if (skill !== null) {
            return (
                !(
                    name &&
                    !skill.name.toLowerCase().includes(name.toLowerCase())
                ) &&
                !(category && category !== skill.category.name) &&
                !(
                    minDuration &&
                    minDuration !== 0 &&
                    minDuration > skill.duration
                ) &&
                !(
                    maxDuration &&
                    maxDuration !== 0 &&
                    maxDuration < skill.duration
                )
            );
        }
    }

    const FilterTraineeSkills = (trainee) =>
        trainee.filter((s) => filterSkills(s.skill));

    const FilterSkills = (skills2) => skills2.filter((s) => filterSkills(s));

    let filteredSkills = ownSkills
        ? FilterTraineeSkills(traineeSkills)
        : FilterSkills(skills);

    // if (type !== 'any') {
    //     filteredSkills = filteredSkills.filter(
    //         (s) => !(type && type !== (s.skill.type ? 'hard' : 'soft'))
    //     );
    // }

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
                            <Link
                                key={skill.skill.id}
                                to={`/skill/${skill.skill.id}`}
                            >
                                <div className="skill-item">
                                    <h3>{skill.skill.name}</h3>
                                    <p>{skill.skill.description}</p>
                                </div>
                            </Link>
                        ) : (
                            <div key={skill.id} className="skill-item">
                                <h3>{skill.name}</h3>
                                <p>{skill.description}</p>
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
