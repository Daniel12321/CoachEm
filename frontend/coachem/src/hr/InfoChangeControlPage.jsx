import './InfoChangeControlPage.css';
import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import {
    OldDataViewComponent,
    NewDataViewComponent,
} from '../common/Components';

export default function InfoChangeControlPage({ logout }) {
    const [newDetails, setNewDetails] = useState([]);
    const [oldDetails, setOldDetails] = useState([]);
    const [ infoChangeId, personId ] = useParams();

    useEffect(() => {
        async function getInfoChange() {
            const res = await fetch(
                `http://localhost:8080/api/infochange/get/${infoChangeId}`,
                {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem(
                            'access_token'
                        )}`,
                    },
                }
            );
            if (res.status === 401) {
                logout();
            }
            const data = await res.json();
            setNewDetails(data);
        }

        async function getOldDetails() {
            const res = await fetch(
                `http://localhost:8080/api/person/${personId}`,
                {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem(
                            'access_token'
                        )}`,
                    },
                }
            );
            if (res.status === 401) {
                logout();
            }
            const data = await res.json();
            setOldDetails(data);
        }
        getInfoChange();
        getOldDetails();
    }, [infoChangeId, personId, logout]);

    const updateInfoChange = async (e) => {
        e.preventDefault();
        fetch(`http://127.0.0.1:8080/api/person/infochange/${infoChangeId}`, {
            method: 'PUT',

            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        }).then((response) => {
            if (response.status === 401) {
                logout();
            }
            if (response.status === 404) {
                e.target.form[6].value =
                    'the info change you are trying to procces has been removed';
            }
            if (response.status === 200) {
                e.target.form[6].value = 'saved';
            }
            return response.json();
        });
    };

    const rejectInfoChange = async (e) => {
        e.preventDefault();
        fetch(`http://127.0.0.1:8080/api/infochange/delete/${infoChangeId}`, {
            method: 'DELETE',

            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        }).then((response) => {
            if (response.status === 401) {
                logout();
            }
            if (response.status === 404) {
                e.target.form[5].value = 'already removed';
            }
            if (response.status === 200) {
                e.target.form[5].value = 'changes rejected';
            }
            return response.json();
        });
    };

    return (
        <div className="infoChangePage">
            <h1>Info change request: {infoChangeId}</h1>
            <div className="formsbox">
                <div className="formsbox-left">
                    <OldDataViewComponent
                        data={oldDetails}
                        updateInfo={updateInfoChange}
                    />
                </div>
                <div className="formsbox-right">
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
