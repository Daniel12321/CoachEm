import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import {
    OldDataViewComponent,
    NewDataViewComponent,
} from '../common/Components';
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
        async function getInfoChange() {
            const res = await fetch(
                `${api}/api/infochange/get/${infoChangeId}`,
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
            } else if (res.status === 403) {
                home();
            }
            const data = await res.json();
            setNewDetails(data);
        }

        async function getOldDetails() {
            const res = await fetch(`${api}/api/person/${personId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${localStorage.getItem(
                        'access_token'
                    )}`,
                },
            });
            if (res.status === 401) {
                logout();
            } else if (res.status === 403) {
                home();
            }
            const data = await res.json();
            setOldDetails(data);
        }
        getInfoChange();
        getOldDetails();
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
            }
            if (response.status === 404) {
                e.target.form[5].value = 'already removed';
            }
            if (response.status === 200) {
                e.target.form[5].value = 'changes rejected';
                reloadNotifications();
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
