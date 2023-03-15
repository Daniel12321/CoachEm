import './AccountPage.css';

export default function AccountPage() {
    const info = {
        name: 'Daniel',
        address: 'Grevengoedlaan 106',
        city: 'Doetinchem',
        zipcode: '7009DX',
        phonenumber: '0612345678',
    };

    const updateInfo = (e) => {
        e.preventDefault();
        console.log(e);
    };

    const updatePassword = (e) => {
        e.preventDefault();

        const body = {
            oldPassword: e.target[0].value,
            newPassword1: e.target[1].value,
            newPassword2: e.target[2].value,
        };

        fetch('http://127.0.0.1:8080/api/auth/change_password', {
            method: 'POST',
            body: JSON.stringify(body),
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${localStorage.getItem('access_token')}`,
            },
        }).then((resp) => console.log(resp.status));
    };

    return (
        <div className="accountpage">
            <h1>Account Information</h1>
            <div className="personal-info">
                <h2>Personal Details</h2>
                <form onSubmit={updateInfo}>
                    <label htmlFor="name">Name</label>
                    <input
                        type="text"
                        name="name"
                        id="name"
                        defaultValue={info.name}
                    />
                    <label htmlFor="address">Address</label>
                    <input
                        type="text"
                        name="address"
                        id="address"
                        value={info.address}
                    />
                    <label htmlFor="city">City</label>
                    <input
                        type="text"
                        name="city"
                        id="city"
                        value={info.city}
                    />
                    <label htmlFor="zipcode">Zipcode</label>
                    <input
                        type="text"
                        name="zipcode"
                        id="zipcode"
                        value={info.zipcode}
                    />
                    <label htmlFor="phonenumber">Phonenumber</label>
                    <input
                        type="text"
                        name="phonenumber"
                        id="phonenumber"
                        value={info.phonenumber}
                    />
                    <input type="submit" value="Update Information" />
                </form>
            </div>
            <div className="change-password">
                <h2>Change Password</h2>
                <form onSubmit={updatePassword}>
                    <label htmlFor="old-assword">Old Password</label>
                    <input
                        type="password"
                        name="old-password"
                        id="old-password"
                    />
                    <label htmlFor="new-password1">New Password</label>
                    <input
                        type="password"
                        name="new-password1"
                        id="new-password1"
                    />
                    <label htmlFor="new-password2">New Password</label>
                    <input
                        type="password"
                        name="new-password2"
                        id="new-password2"
                    />
                    <input type="submit" value="Change Password" />
                </form>
            </div>
        </div>
    );
}
