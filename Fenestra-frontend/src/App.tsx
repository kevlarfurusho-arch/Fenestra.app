import './App.css';

import { useState } from 'react';

import { Header } from './components/header/header';
import { Sidebar } from './components/sidebar/sidebar';
import { Footer } from './components/footer/footer';
import type { Page } from './types/navigation';

import { Dashboard } from './pages/dashboard';
import { Members } from './pages/members';
import { House } from './pages/house';
import { Senate } from './pages/senate';

function App() {
    const [currentPage, setCurrentPage] = useState<Page>('dashboard');

    const renderPage = () => {
        switch (currentPage) {
            case 'dashboard':
                return <Dashboard />
            case 'house':
                return <House />
            case 'senate':
                return <Senate />
            case 'members':
                return <Members />
            default:
                return <Dashboard />
        }
    };

    return (
        <div id="app-container">
            <aside><Sidebar currentPage={currentPage} setCurrentPage={setCurrentPage}/></aside>

            <div id="app-right">
                <header>
                    <Header />
                </header>
                
                <main id="app-content">
                    {renderPage()}
                </main>

                <footer>
                    <Footer />
                </footer>
            </div>
        </div>
    );
}

export default App
