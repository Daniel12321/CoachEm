import { useEffect, useState } from 'react';
import { useLocalStorage } from '../common/LocalStorage';
import './NewSkill.css';

export default function SkillPage({ home, logout }) {
    const [api] = useLocalStorage('api');
    const [categories, setCategories] = useState([]);

    function addSkill(e) {
        e.preventDefault();
        let type;
        if (e.target[1].value === 'hard') {
            type = true;
        } else {
            type = false;
        }
        const data = {
            name: e.target[0].value,
            type: type,
            duration: e.target[2].value,
            description: e.target[4].value,
            time: new Date(),
        };
        let dataJSON = JSON.stringify(data);
        fetch(`${api}/api/skill/new/${e.target[3].value}`, {
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
            .catch((error) => console.log(error));
    }
    function addCategory(e) {
        e.preventDefault();
        const data = {
            name: e.target[0].value,
        };
        let dataJSON = JSON.stringify(data);
        fetch(`${api}/api/category/new`, {
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
                setCategories(categories.concat(data));
                e.target[0].value = '';
            })
            .catch((error) => console.log(error));
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
                } else if (response.status === 403) {
                    home();
                }
                return response.json();
            })
            .then((data) => {
                setCategories(data);
            })
            .catch((error) => console.log(error));
    }, [home, logout, api]);

    function categoryOptions() {
        if (categories.length === 0) {
            return;
        }
        return categories.map((category) => (
            <option key={category.id} value={category.id}>
                {category.name}
            </option>
        ));
    }

    return (
        <div>
            <h1>New skill</h1>
            <div className="newSkill">
                <form className="new-skill-form" onSubmit={(e) => addSkill(e)}>
                    <label htmlFor="name">Name: </label>
                    <input
                        id="name"
                        type="text"
                        placeholder="name"
                        maxLength={30}
                        required
                    />
                    <label htmlFor="type">Type: </label>
                    <select name="type" id="type">
                        <option value="hard">hard skill</option>
                        <option value="soft">soft skill</option>
                    </select>
                    <br />
                    <label htmlFor="duration">Duration: </label>
                    <input
                        id="duration"
                        type="number"
                        placeholder="duration in hours"
                        maxLength={30}
                        required
                    />
                    <label htmlFor="category">Category: </label>
                    <select name="category" id="category">
                        {categoryOptions()}
                    </select>
                    <br />
                    <label htmlFor="description">Description: </label>
                    <input
                        id="description"
                        type="text"
                        placeholder="description"
                        maxLength={30}
                        required
                    />
                    <input type="submit" value="add new skill"></input>
                </form>
            </div>
            <h1>New category</h1>
            <div className="newCategory">
                <form
                    className="new-skill-form"
                    onSubmit={(e) => addCategory(e)}
                >
                    <label htmlFor="name">Name: </label>
                    <input
                        id="name"
                        type="text"
                        placeholder="name"
                        maxLength={30}
                        required
                    />
                    <input value="add category" type="submit"></input>
                </form>
            </div>
        </div>
    );
}
