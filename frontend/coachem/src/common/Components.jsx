export function NameFilter({setName}) {
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

export function EmailFilter({setEmail}) {
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

export function OldDataViewComponent({data, updateInfo}){
    return(
    <div className="personal-info">
        <h2>Current Personal Details</h2>
        <form className="form" onSubmit={updateInfo}>
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
)
}

export function NewDataViewComponent({data, updateInfo, rejectInfoChange}){
    return(
    <div className="personal-info">
        <h2>New Personal Details</h2>
        <form className="form" onSubmit={updateInfo}>
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
)
}