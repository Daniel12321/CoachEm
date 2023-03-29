import { Routes, Route, Navigate } from 'react-router-dom';

import { useCallback, useEffect, useState } from 'react';

import Header from './common/Header';
import LoginPage from './login/LoginPage';
import AccountPage from './account/AccountPage';
import SkillsPage from './skills/SkillsPage';
import SkillPage from './skills/SkillPage';
import EvaluationsPage from './evaluations/EvaluationsPage';
import NewEvaluationPage from './evaluations/NewEvaluationPage';
import InvitationsPage from './invitations/InvitationsPage';
import TraineesPage from './trainees/TraineesPage';
import AccountsPage from './hr/AccountsPage';
import AccountAddPage from './hr/AccountAddPage';
import AccountUpdatePage from './hr/AccountUpdatePage';
import AccountViewPage from './hr/AccountViewPage';
import InfoChangesPage from './hr/InfoChangesPage';
import InfoChangeControllPage from './hr/InfoChangeControllPage'
import './App.css';

export default function App() {
    const [role, setRole] = useState(localStorage.getItem('user_role'));
    const [notifications, setNotifications] = useState();

    const logout = useCallback(() => {
        localStorage.removeItem('access_token');
        localStorage.removeItem('user_role');
        setRole(undefined);
    }, []);

    const reloadNotifications = useCallback(() => {
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

    useEffect(() => {
        reloadNotifications();
    }, [role, reloadNotifications]);

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
                        path="/skills/:id"
                        element={
                            <SkillsPage
                                ownSkills={true}
                                coachView
                                logout={logout}
                            />
                        }
                    />
                    <Route
                        path="/skill/:id"
                        element={
                            <SkillPage
                                logout={logout}
                                reloadNotifications={reloadNotifications}
                            />
                        }
                    />
                    <Route
                        path="/evals"
                        element={
                            <EvaluationsPage
                                logout={logout}
                                reloadNotifications={reloadNotifications}
                            />
                        }
                    />
                    <Route
                        path="/invites"
                        element={
                            <InvitationsPage
                                logout={logout}
                                reloadNotifications={reloadNotifications}
                            />
                        }
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
                    <Route
                        path="/account-update/:id"
                        element={<AccountUpdatePage logout={logout} />}
                    />
                    <Route
                        path="/account-view/:id"
                        element={<AccountViewPage logout={logout} />}
                    />
                    <Route
                        path="/infoChanges"
                        element={<InfoChangesPage logout={logout} />}
                    />
                    <Route
                        path="/infoChange-controll/:infoChangeId/:personId" 
                        element={<InfoChangeControllPage logout={logout} />}
                    />
                    <Route
                        path="/new-eval"
                        element={<NewEvaluationPage logout={logout} />}
                    />
                </Routes>
            </div>
        </div>
    );
}
