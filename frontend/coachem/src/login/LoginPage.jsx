import { useLocalStorage } from '../common/LocalStorage';
import './LoginPage.css';

export default function LoginPage({ setRole, logout }) {
    const [api] = useLocalStorage('api');
    const login = (e) => {
        e.preventDefault();

        const body = {
            email: e.target[0].value,
            password: e.target[1].value,
        };

        fetch(`${api}/api/auth/login`, {
            method: 'POST',
            body: JSON.stringify(body),
            headers: { 'Content-Type': 'application/json' },
        })
            .then((response) => {
                if (response.status === 401) {
                    logout();
                }
                return response.json();
            })
            .then((data) => {
                localStorage.setItem('access_token', data.token);
                localStorage.setItem('user_role', data.person.user.role);
                localStorage.setItem('person', JSON.stringify(data.person));
                setRole(data.person.user.role);
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
