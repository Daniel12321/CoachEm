import { useEffect, useState } from 'react';
import './SkillsPage.css';

const defCats = [
    { id: 1, name: 'javascript', title: 'Javascript' },
    { id: 2, name: 'java', title: 'Java' },
    { id: 3, name: 'sql', title: 'SQL' },
    { id: 4, name: 'c#', title: 'C#' },
    { id: 5, name: 'python', title: 'Python' },
    { id: 6, name: 'personal', title: 'Personal' },
];

const defSkills = [
    {
        id: 1,
        type: 'hard',
        name: 'Learn React basics',
        category: 'javascript',
        duration: 4,
    },
    {
        id: 2,
        type: 'hard',
        name: 'Learn React: Advanced',
        category: 'javascript',
        duration: 5,
    },
    {
        id: 3,
        type: 'hard',
        name: 'Learn React: Expert',
        category: 'javascript',
        duration: 6,
    },
    {
        id: 4,
        type: 'hard',
        name: 'Learn Spring Boot',
        category: 'java',
        duration: 7,
    },
    {
        id: 5,
        type: 'hard',
        name: 'Learn Mapstruct',
        category: 'java',
        duration: 1,
    },
    {
        id: 6,
        type: 'hard',
        name: 'Learn Java Generics',
        category: 'java',
        duration: 2,
    },
    {
        id: 7,
        type: 'hard',
        name: 'Learn SQL basics',
        category: 'sql',
        duration: 4,
    },
    {
        id: 8,
        type: 'hard',
        name: 'Learn SQL: Advanced',
        category: 'sql',
        duration: 6,
    },
    {
        id: 9,
        type: 'hard',
        name: 'Learn SQL: Expert',
        category: 'sql',
        duration: 6,
    },
    {
        id: 10,
        type: 'hard',
        name: 'Learn NoSQL',
        category: 'sql',
        duration: 6,
    },
    {
        id: 11,
        type: 'soft',
        name: 'Learn to make personal goals',
        category: 'personal',
    },
];

export default function SkillsPage() {
    const [skills, setSkills] = useState([]);
    const [categories, setCategories] = useState([]);

    const [name, setName] = useState();
    const [type, setType] = useState();
    const [category, setCategory] = useState();
    const [minDuration, setMinDuration] = useState();
    const [maxDuration, setMaxDuration] = useState();

    useEffect(() => {
        setSkills(defSkills);
        setCategories(defCats);
        // fetch("http://localhost:8080/skills")
        //   .then((resp) => resp.toJson())
        //   .then(setSkills);
    }, []);

    const filteredSkills = skills
        .filter(
            (s) => !(name && !s.name.toLowerCase().includes(name.toLowerCase()))
        )
        .filter((s) => !(type && type !== s.type))
        .filter((s) => !(category && category !== s.category))
        .filter(
            (s) =>
                !(minDuration && minDuration !== 0 && minDuration > s.duration)
        )
        .filter(
            (s) =>
                !(maxDuration && maxDuration !== 0 && maxDuration < s.duration)
        );

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
                    {filteredSkills.map((skill) => (
                        <div key={skill.id} className="skill-item">
                            <h3>{skill.name}</h3>
                            <p>
                                Deze skill heeft op dit moment nog geen
                                omschrijving. Dit moet nog worden toegevoegd.
                                Komt nog!
                            </p>
                        </div>
                    ))}
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
                    {cat.title}
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
