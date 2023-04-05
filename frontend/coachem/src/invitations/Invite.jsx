import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useLocalStorage } from '../common/LocalStorage';
import { useNavigate } from 'react-router-dom';
import './Invite.css';

export default function Invite({ home, logout, reloadNotifications }) {
    const questionOne = 'has eye for detail';
    const questionTwo = 'is on time with deadlines';
    const questionThree = 'follows procedures';
    const questionFour = 'learns professional skills quickly';
    const questionFive = 'is good with critical feedback';
    const questionSix = 'do you have anything else to add?';

    const [api] = useLocalStorage('api');
    const { id } = useParams();
    const [invite, setInvite] = useState();
    const [route] = useLocalStorage('route', '');
    const navigate = useNavigate();
    const [formState, setFormState] = useState({
        q1: 0,
        q2: 0,
        q3: 0,
        q4: 0,
        q5: 0,
        q6: '',
    });
    const person = JSON.parse(localStorage.getItem('person'));

    useEffect(() => {
        fetch(`${api}/api/invite/get/${id}`, {
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
                setInvite(data);
                if (data.questionOne !== 0) {
                    setFormState({
                        q1: data.questionOne,
                        q2: data.questionTwo,
                        q3: data.questionThree,
                        q4: data.questionFour,
                        q5: data.questionFive,
                        q6: data.questionSix,
                        trainee: data.trainee,
                    });
                }
            });
    }, [home, logout, api, id]);

    function submitInvite(e) {
        e.preventDefault();
        const data = {
            questionOne: formState.q1,
            questionTwo: formState.q2,
            questionThree: formState.q3,
            questionFour: formState.q4,
            questionFive: formState.q5,
            questionSix: formState.q6,
            accepted: true,
        };
        let dataJSON = JSON.stringify(data);
        fetch(`${api}/api/invite/update/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
            body: dataJSON,
        }).then((response) => {
            if (response.status === 401) {
                logout();
            } else if (response.status === 403) {
                home();
            } else if (!response.ok) {
                alert('fill in all fields');
            } else if (response.ok) {
                reloadNotifications();
                navigate(`${route}/invites`);
            }
            return response.json();
        });
    }

    return (
        <div>
            <h1>360 degree feedback for {invite && invite.trainee.name}</h1>
            <form onSubmit={(e) => submitInvite(e)}>
                <fieldset
                    {...(formState.trainee && formState.trainee.id === person.id
                        ? { disabled: true }
                        : {})}
                >
                    {formState.q1 !== 0 && (
                        <>
                            <div className="grid">
                                <span></span>
                                <span>disagree</span>
                                <span> somewhat disagree</span>
                                <span>neutral</span>
                                <span>somewhat agree</span>
                                <span>agree</span>
                                <label htmlFor="q1">{questionOne}</label>
                                <input
                                    type="radio"
                                    name="q1"
                                    value={1}
                                    defaultChecked={
                                        formState.q1 === 1 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q1: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q1"
                                    value={2}
                                    defaultChecked={
                                        formState.q1 === 2 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q1: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q1"
                                    value={3}
                                    defaultChecked={
                                        formState.q1 === 3 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q1: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q1"
                                    value={4}
                                    defaultChecked={
                                        formState.q1 === 4 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q1: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q1"
                                    value={5}
                                    defaultChecked={
                                        formState.q1 === 5 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q1: e.target.value,
                                        })
                                    }
                                />

                                <label htmlFor="q2">{questionTwo}</label>
                                <input
                                    type="radio"
                                    name="q2"
                                    value={1}
                                    defaultChecked={
                                        formState.q2 === 1 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q2: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q2"
                                    value={2}
                                    defaultChecked={
                                        formState.q2 === 2 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q2: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q2"
                                    value={3}
                                    defaultChecked={
                                        formState.q2 === 3 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q2: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q2"
                                    value={4}
                                    defaultChecked={
                                        formState.q2 === 4 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q2: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q2"
                                    value={5}
                                    defaultChecked={
                                        formState.q2 === 5 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q2: e.target.value,
                                        })
                                    }
                                />

                                <label htmlFor="q3">{questionThree}</label>
                                <input
                                    type="radio"
                                    name="q3"
                                    value={1}
                                    defaultChecked={
                                        formState.q3 === 1 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q3: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q3"
                                    value={2}
                                    defaultChecked={
                                        formState.q3 === 2 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q3: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q3"
                                    value={3}
                                    defaultChecked={
                                        formState.q3 === 3 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q3: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q3"
                                    value={4}
                                    defaultChecked={
                                        formState.q3 === 4 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q3: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q3"
                                    value={5}
                                    defaultChecked={
                                        formState.q3 === 5 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q3: e.target.value,
                                        })
                                    }
                                />

                                <label htmlFor="q4">{questionFour}</label>
                                <input
                                    type="radio"
                                    name="q4"
                                    value={1}
                                    defaultChecked={
                                        formState.q4 === 1 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q4: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q4"
                                    value={2}
                                    defaultChecked={
                                        formState.q4 === 2 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q4: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q4"
                                    value={3}
                                    defaultChecked={
                                        formState.q4 === 3 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q4: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q4"
                                    value={4}
                                    defaultChecked={
                                        formState.q4 === 4 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q4: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q4"
                                    value={5}
                                    defaultChecked={
                                        formState.q4 === 5 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q4: e.target.value,
                                        })
                                    }
                                />

                                <label htmlFor="q5">{questionFive}</label>
                                <input
                                    type="radio"
                                    name="q5"
                                    value={1}
                                    defaultChecked={
                                        formState.q5 === 1 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q5: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q5"
                                    value={2}
                                    defaultChecked={
                                        formState.q5 === 2 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q5: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q5"
                                    value={3}
                                    defaultChecked={
                                        formState.q5 === 3 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q5: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q5"
                                    value={4}
                                    defaultChecked={
                                        formState.q5 === 4 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q5: e.target.value,
                                        })
                                    }
                                />
                                <input
                                    type="radio"
                                    name="q5"
                                    value={5}
                                    defaultChecked={
                                        formState.q5 === 5 ? true : false
                                    }
                                    onChange={(e) =>
                                        setFormState({
                                            ...formState,
                                            q5: e.target.value,
                                        })
                                    }
                                />
                            </div>

                            <label htmlFor="q6" id="q6-label">
                                {questionSix}
                            </label>
                            <br />
                            <textarea
                                name="q6"
                                id="q6"
                                cols="30"
                                rows="10"
                                defaultValue={formState.q6}
                                onChange={(e) =>
                                    setFormState({
                                        ...formState,
                                        q6: e.target.value,
                                    })
                                }
                            ></textarea>
                        </>
                    )}
                    <input type="submit" id="submit" />
                </fieldset>
            </form>
        </div>
    );
}
