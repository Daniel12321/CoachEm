import { Routes, Route, Navigate } from 'react-router-dom';

import { useState } from 'react';

import Header from './common/Header';
import LoginPage from './login/LoginPage';
import AccountPage from './account/AccountPage';
import SkillsPage from './skills/SkillsPage';
import SkillPage from './skills/SkillPage';
import MeetingsPage from './meetings/MeetingsPage';
import InvitationsPage from './invitations/InvitationsPage';
import TraineesPage from './trainees/TraineesPage';
import AccountsPage from './hr/AccountsPage';
import AccountAddPage from './hr/AccountAddPage';
import './App.css';

export default function App() {
    const [role, setRole] = useState(localStorage.getItem('user_role'));

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
            <Header loggedIn={true} role={role.toLowerCase()} />
            <div className="container">
                <Routes>
                    <Route path="/" element={<Navigate to={redir} />} />
                    <Route path="/account" element={<AccountPage />} />
                    <Route path="/skills-all" element={<SkillsPage />} />
                    <Route path="/skills" element={<SkillsPage />} />
                    <Route path="/skill/:id" element={<SkillPage />} />
                    <Route path="/meets" element={<MeetingsPage />} />
                    <Route path="/invites" element={<InvitationsPage />} />
                    <Route path="/trainees" element={<TraineesPage />} />
                    <Route path="/accounts" element={<AccountsPage />} />
                    <Route path="/account-add" element={<AccountAddPage />} />
                </Routes>
            </div>
        </div>
    );
}
