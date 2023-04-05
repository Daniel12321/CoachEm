import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { useLocalStorage } from '../common/LocalStorage';
import './InfoChangeControlPage.css';

export default function InfoChangeControlPage({
    home,
    logout,
    reloadNotifications,
}) {
    const [api] = useLocalStorage('api');
    const [newDetails, setNewDetails] = useState([]);
    const [oldDetails, setOldDetails] = useState([]);
    const { infoChangeId, personId } = useParams();

    useEffect(() => {
        fetch(`${api}/api/infochange/get/${infoChangeId}`, {
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
            .then((data) => setNewDetails(data));

        fetch(`${api}/api/person/${personId}`, {
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
            .then((data) => setOldDetails(data));
    }, [infoChangeId, personId, home, logout, api]);

    const updateInfoChange = async (e) => {
        e.preventDefault();
        fetch(`${api}/api/person/infochange/${infoChangeId}`, {
            method: 'PUT',

            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        }).then((response) => {
            if (response.status === 401) {
                logout();
            } else if (response.status === 403) {
                home();
            } else if (response.status === 404) {
                e.target.form[6].value =
                    'the info change you are trying to procces has been removed';
            } else if (response.status === 200) {
                reloadNotifications();
                e.target.form[6].value = 'saved';
                home();
            }
            return response.json();
        });
    };

    const rejectInfoChange = async (e) => {
        e.preventDefault();
        fetch(`${api}/api/infochange/delete/${infoChangeId}`, {
            method: 'DELETE',

            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        }).then((response) => {
            if (response.status === 401) {
                logout();
            } else if (response.status === 403) {
                home();
            } else if (response.status === 404) {
                e.target.form[5].value = 'already removed';
            } else if (response.status === 200) {
                e.target.form[5].value = 'changes rejected';
                reloadNotifications();
            }
            return response.json();
        });
    };

    return (
        <div className="infochangepage">
            <h1>Info Change Request</h1>
            <div className="infochange-formsbox">
                <div>
                    <OldDataViewComponent
                        data={oldDetails}
                        updateInfo={updateInfoChange}
                    />
                </div>
                <div>
                    <NewDataViewComponent
                        data={newDetails}
                        updateInfo={updateInfoChange}
                        rejectInfoChange={rejectInfoChange}
                    />
                </div>
            </div>
        </div>
    );
}

function OldDataViewComponent({ data, updateInfo }) {
    return (
        <div className="personal-info">
            <h2>Current</h2>
            <form onSubmit={updateInfo}>
                <label htmlFor="name">Name</label>
                <input
                    type="text"
                    name="name"
                    id="name"
                    defaultValue={data.name}
                    readOnly
                />
                <label htmlFor="address">Address</label>
                <input
                    type="text"
                    name="address"
                    id="address"
                    defaultValue={data.address}
                    readOnly
                />
                <label htmlFor="city">City</label>
                <input
                    type="text"
                    name="city"
                    id="city"
                    defaultValue={data.city}
                    readOnly
                />
                <label htmlFor="zipcode">Zipcode</label>
                <input
                    type="text"
                    name="zipcode"
                    id="zipcode"
                    defaultValue={data.zipcode}
                    readOnly
                />
                <label htmlFor="phonenumber">Phonenumber</label>
                <input
                    type="text"
                    name="phonenumber"
                    id="phonenumber"
                    defaultValue={data.phonenumber}
                    readOnly
                />
            </form>
        </div>
    );
}

function NewDataViewComponent({ data, updateInfo, rejectInfoChange }) {
    return (
        <div className="personal-info">
            <h2>New</h2>
            <form onSubmit={updateInfo}>
                <label htmlFor="name">Name</label>
                <input
                    type="text"
                    name="name"
                    id="name"
                    defaultValue={data.name}
                    readOnly
                />
                <label htmlFor="address">Address</label>
                <input
                    type="text"
                    name="address"
                    id="address"
                    defaultValue={data.address}
                    readOnly
                />
                <label htmlFor="city">City</label>
                <input
                    type="text"
                    name="city"
                    id="city"
                    defaultValue={data.city}
                    readOnly
                />
                <label htmlFor="zipcode">Zipcode</label>
                <input
                    type="text"
                    name="zipcode"
                    id="zipcode"
                    defaultValue={data.zipcode}
                    readOnly
                />
                <label htmlFor="phonenumber">Phonenumber</label>
                <input
                    type="text"
                    name="phonenumber"
                    id="phonenumber"
                    defaultValue={data.phonenumber}
                    readOnly
                />
                <input
                    className="reject dark-on-hover"
                    type="submit"
                    value="reject Changes"
                    onClick={rejectInfoChange}
                />
                <input
                    className="accept dark-on-hover"
                    type="button"
                    value="accept Changes"
                    onClick={updateInfo}
                />
            </form>
        </div>
    );
}
