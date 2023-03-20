import './AccountUpdatePage.css';
import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function AccountUpdatePage() {

  
    const [trainees, setTrainees] = useState([]);


    let { id } = useParams();
    useEffect(() => {
        getAllTrainees();
    }, []);


    async function getAllTrainees() {
        const res = await fetch(`http://localhost:8080/api/person/${id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        });
        const data = await res.json();
        setTrainees(data);
        console.log(data)
    }

    
    const updateInfo = (e) => {
        console.log("update")
        e.preventDefault();

        const body = {
            name: e.target[0].value,
            address: e.target[1].value,
            city: e.target[2].value,
            zipcode: e.target[3].value,
            phonenumber: e.target[4].value,
        };

       const res = fetch('http://127.0.0.1:8080/api/infochange/new', {
            method: 'POST',
            body: JSON.stringify(body),
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        });
        const data = res.json();
        console.log(data);
    };

    // const updatePassword = (e) => {
    //     e.preventDefault();

    //     const body = {
    //         oldPassword: e.target[0].value,
    //         newPassword1: e.target[1].value,
    //         newPassword2: e.target[2].value,
    //     };

    //     fetch('http://127.0.0.1:8080/api/auth/change_password', {
    //         method: 'POST',
    //         body: JSON.stringify(body),
    //         headers: {
    //             'Content-Type': 'application/json',
    //             Authorization: `Bearer ${localStorage.getItem('access_token')}`,
    //         },
    //     }).then((resp) => console.log(resp.status));
    // };

    return (
        <div className="accountpage">
              
    
    <h1>update Page for accountt {id}</h1>
          
            <div className="personal-info">
                <h2>Personal Details</h2>
                <form>
                    <label htmlFor="name">Name</label>
                    <input
                        type="text"
                        name="name"
                        id="name"
                        defaultValue={trainees.name}
                    />
                    <label htmlFor="address">Address</label>
                    <input
                        type="text"
                        name="address"
                        id="address"
                        defaultValue={trainees.address}
                    />
                    <label htmlFor="city">City</label>
                    <input
                        type="text"
                        name="city"
                        id="city"
                        defaultValue={trainees.city}
                    />
                    <label htmlFor="zipcode">Zipcode</label>
                    <input
                        type="text"
                        name="zipcode"
                        id="zipcode"
                        defaultValue={trainees.zipcode}
                    />
                    <label htmlFor="phonenumber">Phonenumber</label>
                    <input
                        type="text"
                        name="phonenumber"
                        id="phonenumber"
                        defaultValue={trainees.phonenumber}
                    />
                    <input type="submit" value="Update Information" />
                </form>
            </div>

        </div>
    );
}