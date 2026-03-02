import './Sidebar.css';
import '../button/button.css'
import { SidebarButton } from '../button/button.tsx';
import type { Page } from '../../types/navigation.ts';
import { useState } from 'react';
import { useEffect } from 'react';
import type { AppDataDTO } from '../../types/dto/AppData.dto.ts'

interface SidebarProps {
    currentPage: Page;
    setCurrentPage: (page: Page) => void;
}

interface SidebarButtonData {
    name: string,
    iconClass: string,
    page: Page
}


export function Sidebar ({ currentPage, setCurrentPage }: SidebarProps) {
    const sidebarButtons: SidebarButtonData[] = [
        { name: 'Dashboard', iconClass: 'fa-solid fa-gauge', page: 'dashboard' },
        { name: 'House Bills', iconClass: 'fa-solid fa-house', page: 'house' },
        { name: 'Senate Bills', iconClass: 'fa-solid fa-landmark-dome', page: 'senate' },
        { name: 'Members', iconClass: 'fa-solid fa-users', page: 'members' },
    ];

    const [appData, setAppData] = useState<AppDataDTO | null>(null);
    
    useEffect(() => {
        fetch('/api/appdata')
            .then(res => res.json())
            .then((data: AppDataDTO) => setAppData(data))
            .catch(err => console.error('Error fetching app data:', err));
            }, []);

    return (
        <div id="sidebar">
            <div className="logo">
                <span id="logo-text">{appData?.name || "Name error"}</span><br/>
                <span id="version-text">{appData?.version || "Version error"}</span>
            </div>

            {sidebarButtons.map((button) => (
                <SidebarButton
                    key={button.page}
                    name={button.name}
                    iconClass={button.iconClass}
                    onClick={() => setCurrentPage(button.page)}
                    className='sidebar-button'
                    active={currentPage===button.page}
                />
            ))}
        </div>
    );
}

export default Sidebar