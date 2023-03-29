export function Components({setName}) {
    return(
        <div className="infoChange-filter infoChange-filter-name">
        <p>Name</p>
        <input
            type="text"
            name="name"
            id="name"
            onChange={(e) => setName(e.target.value)}
        />
    </div>
    ) 
}

export function Components2({setEmail}) {
    return(
        <div className="infoChange-filter trainee-filter-email">
        <p>Email</p>
        <input
            type="text"
            name="email"
            id="email"
            onChange={(e) => setEmail(e.target.value)}
        />
    </div>
    ) 
}

export function OldDataViewComponent({account, updateInfo}){
    return(
    <div className="personal-info">
        <h2>Current Personal Details</h2>
        <form className="form" onSubmit={updateInfo}>
            <label htmlFor="name">Name</label>
            <input
                type="text"
                name="name"
                id="name"
                defaultValue={account.name}
                readOnly
            />
            <label htmlFor="address">Address</label>
            <input
                type="text"
                name="address"
                id="address"
                defaultValue={account.address}
                readOnly
            />
            <label htmlFor="city">City</label>
            <input
                type="text"
                name="city"
                id="city"
                defaultValue={account.city}
                readOnly
            />
            <label htmlFor="zipcode">Zipcode</label>
            <input
                type="text"
                name="zipcode"
                id="zipcode"
                defaultValue={account.zipcode}
                readOnly
            />
            <label htmlFor="phonenumber">Phonenumber</label>
            <input
                type="text"
                name="phonenumber"
                id="phonenumber"
                defaultValue={account.phonenumber}
                readOnly
            />
        </form>
    </div>
)
}

export function NewDataViewComponent({account, updateInfo, rejectInfoChange}){
    return(
    <div className="personal-info">
        <h2>New Personal Details</h2>
        <form className="form" onSubmit={updateInfo}>
            <label htmlFor="name">Name</label>
            <input
                type="text"
                name="name"
                id="name"
                defaultValue={account.name}
                readOnly
            />
            <label htmlFor="address">Address</label>
            <input
                type="text"
                name="address"
                id="address"
                defaultValue={account.address}
                readOnly
            />
            <label htmlFor="city">City</label>
            <input
                type="text"
                name="city"
                id="city"
                defaultValue={account.city}
                readOnly
            />
            <label htmlFor="zipcode">Zipcode</label>
            <input
                type="text"
                name="zipcode"
                id="zipcode"
                defaultValue={account.zipcode}
                readOnly
            />
            <label htmlFor="phonenumber">Phonenumber</label>
            <input
                type="text"
                name="phonenumber"
                id="phonenumber"
                defaultValue={account.phonenumber}
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
)
}