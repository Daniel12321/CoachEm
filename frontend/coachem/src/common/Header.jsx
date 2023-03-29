import React from 'react';
import { Link, NavLink } from 'react-router-dom';

import './Header.css';

export default function Header({ logout, role, notifications }) {
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

    return (
        <div className="header-wrapper">
            <header>
                <Link className="header-logo" to="/">
                    <img src="" alt="Logo" />
                </Link>
                {nav}
                <Account logout={logout} notifications={notifications} />
            </header>
        </div>
    );
}

function arrayCount(arr) {
    return arr ? arr.length : 0;
}

function notificationCount(dto) {
    if (!dto) {
        return 0;
    }

    return (
        arrayCount(dto.attendees) +
        arrayCount(dto.invites) +
        arrayCount(dto.evaluations) +
        arrayCount(dto.feedback) +
        arrayCount(dto.infoChanges)
    );
}

function Account({ logout, notifications }) {
    const count = notificationCount(notifications);
    const badge = count ? (
        <div className="notification-badge">{count}</div>
    ) : (
        ''
    );

    return (
        <div className="header-dropdown">
            {'Account'}
            {badge}
            <div className="header-dropdown-content">
                <Link className="header-dropdown-item" to={'/account'}>
                    Profile
                </Link>
                {notifications && (
                    <NotificationList notifications={notifications} />
                )}
                <div className="header-dropdown-item" onClick={logout}>
                    Log out
                </div>
            </div>
        </div>
    );
}

function NotificationList({ notifications }) {
    const feedbackTo =
        notifications.feedback && notifications.feedback.length > 0
            ? '/skill/' + notifications.feedback[0].traineeSkill.id
            : '/skills';

    return (
        <>
            <NotificationItem
                to="/evals"
                message="New Evaluation Invites"
                count={arrayCount(notifications.attendees)}
            />
            <NotificationItem
                to="/evals"
                message="New Evaluations"
                count={arrayCount(notifications.evaluations)}
            />
            <NotificationItem
                to="/invites"
                message="New 360 Invites"
                count={arrayCount(notifications.invites)}
            />
            <NotificationItem
                to={feedbackTo}
                message="New Feedback"
                count={arrayCount(notifications.feedback)}
            />
            <NotificationItem
                to="/evals"
                message="New Info Changes"
                count={arrayCount(notifications.infoChanges)}
            />
        </>
    );
}

function NotificationItem({ message, count, to, clear }) {
    if (!count) {
        return '';
    }

    return (
        <Link
            onClick={clear}
            className="header-dropdown-item notification"
            to={to}
        >
            {message}
        </Link>
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
