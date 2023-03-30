import React from 'react';
import { Link, NavLink } from 'react-router-dom';
import { useLocalStorage } from './LocalStorage';

import './Header.css';

export default function Header({ logout, role, notifications }) {
    const [route] = useLocalStorage('route', '');

    const nav =
        role === 'trainee' ? (
            <TraineeNav route={route} />
        ) : role === 'coach' || role === 'manager' ? (
            <CoachNav route={route} />
        ) : role === 'hr' ? (
            <HRNav route={route} />
        ) : (
            <nav />
        );

    return (
        <div className="header-wrapper">
            <header>
                <Link className="header-logo" to={route}>
                    <img src="" alt="Logo" />
                </Link>
                {nav}
                <Account
                    route={route}
                    logout={logout}
                    notifications={notifications}
                />
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

function Account({ route, logout, notifications }) {
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
                <Link className="header-dropdown-item" to={`${route}/account`}>
                    Profile
                </Link>
                {notifications && (
                    <NotificationList
                        route={route}
                        notifications={notifications}
                    />
                )}
                <div className="header-dropdown-item" onClick={logout}>
                    Log out
                </div>
            </div>
        </div>
    );
}

function NotificationList({ route, notifications }) {
    const feedbackTo =
        route +
        (notifications.feedback && notifications.feedback.length > 0
            ? '/skill/' + notifications.feedback[0].traineeSkill.id
            : '/skills');

    return (
        <>
            <NotificationItem
                to={`${route}/evals`}
                message="New Evaluation Invites"
                count={arrayCount(notifications.attendees)}
            />
            <NotificationItem
                to={`${route}/evals`}
                message="New Evaluations"
                count={arrayCount(notifications.evaluations)}
            />
            <NotificationItem
                to={`${route}/invites`}
                message="New 360 Invites"
                count={arrayCount(notifications.invites)}
            />
            <NotificationItem
                to={feedbackTo}
                message="New Feedback"
                count={arrayCount(notifications.feedback)}
            />
            <NotificationItem
                to={`${route}/evals`}
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

const TraineeNav = ({ route }) => (
    <nav>
        <NavLink to={`${route}/skills`}>Skills</NavLink>
        <NavLink to={`${route}/skills-all`}>New Skills</NavLink>
        <NavLink to={`${route}/evals`}>Evaluations</NavLink>
        <NavLink to={`${route}/invites`}>Invitations</NavLink>
    </nav>
);

const CoachNav = ({ route }) => (
    <nav>
        <NavLink to={`${route}/trainees`}>Trainees</NavLink>
        <NavLink to={`${route}/skills-all`}>New Skills</NavLink>
        <NavLink to={`${route}/evals`}>Evaluations</NavLink>
        <NavLink to={`${route}/invites`}>Invitations</NavLink>
    </nav>
);

const HRNav = ({ route }) => (
    <nav>
        <NavLink to={`${route}/accounts`}>Accounts</NavLink>
        <NavLink to={`${route}/account-add`}>Add Account</NavLink>
        <NavLink to={`${route}/infochanges`}>Info change</NavLink>
    </nav>
);
