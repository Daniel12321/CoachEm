import './LoginPage.css';

export default function LoginPage({ setRole }) {
    const login = (e) => {
        e.preventDefault();

        const body = {
            email: e.target[0].value,
            password: e.target[1].value,
        };

        fetch('http://127.0.0.1:8080/api/auth/login', {
            method: 'POST',
            body: JSON.stringify(body),
            headers: { 'Content-Type': 'application/json' },
        })
            .then((resp) => resp.json())
            .then((data) => {
                localStorage.setItem('access_token', data.token);
                localStorage.setItem('user_role', data.role);
                setRole(data.role);
            });
    };

    return (
        <div className="loginpage">
            <form className="login-form" onSubmit={login}>
                <h1>Login</h1>
                <input
                    type="text"
                    id="email"
                    name="email"
                    placeholder="Email"
                    required
                />
                <input
                    type="password"
                    id="password"
                    name="password"
                    placeholder="Password"
                    required
                />
                <input type="submit" value="Login" />
            </form>
        </div>
    );
}
