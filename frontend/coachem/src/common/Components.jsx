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

export function Components3({account, updateInfo}){
    return(
    <div className="personal-info">
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
    </div>
)
}