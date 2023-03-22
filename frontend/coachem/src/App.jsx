import { Routes, Route, Navigate } from 'react-router-dom';

import { useEffect, useState } from 'react';

import Header from './common/Header';
import LoginPage from './login/LoginPage';
import AccountPage from './account/AccountPage';
import SkillsPage from './skills/SkillsPage';
import SkillPage from './skills/SkillPage';
import EvaluationsPage from './evaluations/EvaluationsPage';
import InvitationsPage from './invitations/InvitationsPage';
import TraineesPage from './trainees/TraineesPage';
import AccountsPage from './hr/AccountsPage';
import AccountAddPage from './hr/AccountAddPage';
import './App.css';

export default function App() {
    const [notifications, setNotifications] = useState();
    const [role, setRole] = useState(localStorage.getItem('user_role'));

    useEffect(() => {
        if (role) {
            fetch('http://127.0.0.1:8080/api/notification/all', {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem(
                        'access_token'
                    )}`,
                },
            })
                .then((resp) => resp.json())
                .then(setNotifications);
        }
    }, [role]);

    if (!role) {
        return (
            <div className="app">
                <div className="container">
                    <LoginPage setRole={setRole} />
                </div>
            </div>
        );
    }

    const redir = role.toLowerCase() === 'trainee' ? '/skills' : '/trainees';

    const logout = () => {
        localStorage.removeItem('access_token');
        localStorage.removeItem('user_role');
        setRole(undefined);
    };

    return (
        <div className="app">
            <Header
                logout={logout}
                role={role.toLowerCase()}
                notifications={notifications}
            />
            <div className="container">
                <Routes>
                    <Route path="/" element={<Navigate to={redir} />} />
                    <Route
                        path="/account"
                        element={<AccountPage logout={logout} />}
                    />
                    <Route
                        path="/skills-all"
                        element={
                            <SkillsPage ownSkills={false} logout={logout} />
                        }
                    />
                    <Route
                        path="/skills"
                        element={
                            <SkillsPage ownSkills={true} logout={logout} />
                        }
                    />
                    <Route
                        path="/skill/:id"
                        element={<SkillPage logout={logout} />}
                    />
                    <Route
                        path="/evals"
                        element={<EvaluationsPage logout={logout} />}
                    />
                    <Route
                        path="/invites"
                        element={<InvitationsPage logout={logout} />}
                    />
                    <Route
                        path="/trainees"
                        element={<TraineesPage logout={logout} />}
                    />
                    <Route
                        path="/accounts"
                        element={<AccountsPage logout={logout} />}
                    />
                    <Route
                        path="/account-add"
                        element={<AccountAddPage logout={logout} />}
                    />
                </Routes>
            </div>
        </div>
    );
}
