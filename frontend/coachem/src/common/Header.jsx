import React from 'react';
import './Header.css';

export default function Header({ loggedIn }) {
    return (
        <div className="header-wrapper">
            <header>
                <div className="header-box">
                    <div className="header-logo">
                        <img src="" alt="Logo" />
                    </div>
                    <nav>
                        <div className="header-nav-item">Skills</div>
                        <div className="header-nav-item">Meetings</div>
                        <div className="header-nav-item">Meetings</div>
                        <div className="header-nav-item">Meetings</div>
                    </nav>
                    <div className="header-account">{loggedIn ? "Account" : "Log in"}</div>
                </div>
            </header>
        </div>
    );
}
