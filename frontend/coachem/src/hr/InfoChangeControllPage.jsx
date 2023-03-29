import './InfoChangeControllPage.css';
import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import {OldDataViewComponent, NewDataViewComponent} from '../common/Components';

export default function AccountUpdatePage({ logout }) {
    const [account, setAccounts] = useState([]);
    const [oldDetails, setOldDetails] = useState([]);
    const { infoChangeId, personId } = useParams();

    useEffect(() => {
        async function getInfoChange() {
            const res = await fetch(`http://localhost:8080/api/infochange/get/${infoChangeId}`, {
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
            }
            const data = await res.json();
            setAccounts(data);
        }

        async function getOldDetails(){
            const res = await fetch(`http://localhost:8080/api/person/${personId}`, {
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
            }
            const data = await res.json();
            setOldDetails(data);
        }

        getInfoChange();
        getOldDetails();

    }, [infoChangeId, personId,logout]);

    const updateInfo = async (e) => {
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
                e.target.form[6].value = 'the info change you are trying to procces has been removed';
            }
            if (response.status === 200) {
                 e.target.form[6].value = 'saved';
             }
            return response.json();
        });
        
    };

    const rejectInfo = async (e) => {
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
    });}

    return (
        <div className="infoChangePage">
            <h1>Info change request: {infoChangeId}</h1>
            <div className="formsbox">
                <div className="formsbox-left">
                    <OldDataViewComponent account={oldDetails} updateInfo={updateInfo} />
                </div>
                <div className="formsbox-right">
                    <NewDataViewComponent account={account} updateInfo={updateInfo} rejectInfoChange={rejectInfo}/>
                </div>
            </div>
            {/* <div className="personal-info">
                <h2>Personal Details</h2>
                <form onSubmit={updateInfo}>
                    <label htmlFor="name">Name</label>
                    <input
                        type="text"
                        name="name"
                        id="name"
                        defaultValue={account.name}
                    />
                    <label htmlFor="address">Address</label>
                    <input
                        type="text"
                        name="address"
                        id="address"
                        defaultValue={account.address}
                    />
                    <label htmlFor="city">City</label>
                    <input
                        type="text"
                        name="city"
                        id="city"
                        defaultValue={account.city}
                    />
                    <label htmlFor="zipcode">Zipcode</label>
                    <input
                        type="text"
                        name="zipcode"
                        id="zipcode"
                        defaultValue={account.zipcode}
                    />
                    <label htmlFor="phonenumber">Phonenumber</label>
                    <input
                        type="text"
                        name="phonenumber"
                        id="phonenumber"
                        defaultValue={account.phonenumber}
                    />
                    <input
                        className="dark-on-hover"
                        type="submit"
                        value="Save Changes"
                    />
                </form>
            </div>   */}
        </div>
    );
}
