import { useCallback, useEffect, useState } from 'react';
import { Routes, Route } from 'react-router-dom';

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
import NewInvitePage from './invitations/NewInvitePage';
import NewSkill from './skills/NewSkill';
import { useLocalStorage } from './common/LocalStorage';
import InfoChangesPage from './hr/InfoChangesPage';
import InfoChangeControlPage from './hr/InfoChangeControlPage';
import './App.css';

export default function App() {
    const [api] = useLocalStorage('api');
    const [route] = useLocalStorage('route', '');
    const [role, setRole] = useState(localStorage.getItem('user_role'));
    const [notifications, setNotifications] = useState();

    const logout = useCallback(() => {
        localStorage.removeItem('access_token');
        localStorage.removeItem('user_role');
        setRole(undefined);
    }, []);

    const reloadNotifications = useCallback(() => {
        if (role) {
            fetch(`${api}/api/notification/all`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem(
                        'access_token'
                    )}`,
                },
            })
                .then((resp) => resp.json())
                .then(setNotifications);
        }
    }, [role, api]);

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

    const skillsPage = (
        <SkillsPage
            ownSkills={true}
            logout={logout}
            notifications={notifications}
        />
    );
    const traineesPage = <TraineesPage logout={logout} />;

    return (
        <div className="app">
            <Header
                logout={logout}
                role={role.toLowerCase()}
                notifications={notifications}
            />
            <div className="container">
                <Routes>
                    <Route
                        path={route}
                        element={
                            role.toLowerCase() === 'trainee'
                                ? skillsPage
                                : traineesPage
                        }
                    />
                    <Route
                        path={`${route}/account`}
                        element={<AccountPage logout={logout} />}
                    />
                    <Route
                        path={`${route}/skills-all`}
                        element={
                            <SkillsPage ownSkills={false} logout={logout} />
                        }
                    />
                    <Route path={`${route}/skills`} element={skillsPage} />
                    <Route
                        path={`${route}/skills/:id`}
                        element={
                            <SkillsPage
                                ownSkills={true}
                                coachView
                                logout={logout}
                            />
                        }
                    />
                    <Route
                        path={`${route}/skill/:id`}
                        element={
                            <SkillPage
                                logout={logout}
                                reloadNotifications={reloadNotifications}
                            />
                        }
                    />
                    <Route
                        path={`${route}/evals`}
                        element={
                            <EvaluationsPage
                                logout={logout}
                                reloadNotifications={reloadNotifications}
                            />
                        }
                    />
                    <Route
                        path={`${route}/invites`}
                        element={
                            <InvitationsPage
                                logout={logout}
                                reloadNotifications={reloadNotifications}
                            />
                        }
                    />
                    <Route path={`${route}/trainees`} element={traineesPage} />
                    <Route
                        path={`${route}/accounts`}
                        element={<AccountsPage logout={logout} />}
                    />
                    <Route
                        path={`${route}/account-add`}
                        element={<AccountAddPage logout={logout} />}
                    />
                    <Route
                        path={`${route}/account-update/:id`}
                        element={<AccountUpdatePage logout={logout} />}
                    />
                    <Route
                        path={`${route}/account-view/:id`}
                        element={<AccountViewPage logout={logout} />}
                    />
                    <Route
                        path={`${route}/infoChanges`}
                        element={<InfoChangesPage logout={logout} />}
                    />
                    <Route
                        path={`${route}/infoChange-control/:infoChangeId/:personId`}
                        element={<InfoChangeControlPage 
                        logout={logout} 
                        reloadNotifications={reloadNotifications}/>}
                    />
                    <Route
                        path={`${route}/new-eval`}
                        element={<NewEvaluationPage logout={logout} />}
                    />
                    <Route
                        path={`${route}/new-invite`}
                        element={<NewInvitePage logout={logout} />}
                    />
                    <Route
                        path={`${route}/new-skill`}
                        element={<NewSkill logout={logout} />}
                    />
                </Routes>
            </div>
        </div>
    );
}
