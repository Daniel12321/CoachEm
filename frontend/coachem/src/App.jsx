import { Routes, Route } from 'react-router-dom';
import Header from './common/Header';
import SkillsPage from './skills/SkillsPage'
import './App.css';

export default function App() {
    return (
        <div className="app">
            <Header loggedIn={false} />
            <div className="container">
                <Routes>
                    <Route path="/" element={<SkillsPage />} />
                    <Route path="/skills" element={<SkillsPage />} />
                    {/* <Route path="/tasks" element={<TasksPage tasks={tasks} updateTask={updateTask} deleteTask={deleteTask} />} /> */}
                    {/* <Route path="/websites" element={<WebsitesPage websites={websites} verses={verses} updateWebsite={updateWebsite} />} /> */}
                    {/* <Route path="/verses" element={<BibleVersesPage verses={verses} />} /> */}
                </Routes>
            </div>
        </div>
    );
}
