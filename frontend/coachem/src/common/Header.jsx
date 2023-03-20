import React from 'react';
import { Link, NavLink } from 'react-router-dom';
import './Header.css';

export default function Header({ loggedIn, role }) {
    const nav =
        role === 'trainee' ? (
            <TraineeNav />
        ) : role === 'coach' || role === 'manager' ? (
            <CoachNav />
        ) : role === 'hr' ? (
            <HRNav />
        ) : (
            <nav />
        );

    const accountElem = loggedIn ? (
        <Link className="header-account" to="/account">
            Account
        </Link>
    ) : (
        <Link className="header-account" to="/login">
            Log in
        </Link>
    );

    return (
        <div className="header-wrapper">
            <header>
                <Link className="header-logo" to="/">
                    <img src="" alt="Logo" />
                </Link>
                {nav}
                {accountElem}
            </header>
        </div>
    );
}

const TraineeNav = () => (
    <nav>
        <NavLink to="/skills">Skills</NavLink>
        <NavLink to="/skills-all">New Skills</NavLink>
        <NavLink to="/evals">Evaluations</NavLink>
        <NavLink to="/invites">Invitations</NavLink>
    </nav>
);

const CoachNav = () => (
    <nav>
        <NavLink to="/trainees">Trainees</NavLink>
        <NavLink to="/skills-all">All Skills</NavLink>
        <NavLink to="/evals">Evaluations</NavLink>
        <NavLink to="/invites">Invitations</NavLink>
    </nav>
);

const HRNav = () => (
    <nav>
        <NavLink to="/trainees">Trainees</NavLink>
        <NavLink to="/accounts">Accounts</NavLink>
        <NavLink to="/account-add">Add Account</NavLink>
    </nav>
);
