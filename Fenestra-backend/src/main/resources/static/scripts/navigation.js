export function createSidebarButtons(sidebar, actions) {
    // Define Sidebar Buttons
    const sidebarButtons = [
        { name: 'Dashboard',
        icon: 'fa-solid fa-gauge',
        action: actions.showDashboard },
        { name: 'House Legislation',
        icon: 'fa-solid fa-landmark-dome',
        action: () => actions.fetchLegislation('house') },
        { name: 'Senate Legislation',
        icon: 'fa-solid fa-building-columns',
        action: () => actions.fetchLegislation('senate') },
    ];

    // For each button, create and append to sidebar
    sidebarButtons.forEach(({ name, icon, action }) => {
        const button = document.createElement('button');
        const buttonIcon = document.createElement('i');
        const buttonText = document.createElement('span');
        buttonIcon.className = icon;
        buttonText.textContent = name;

        button.appendChild(buttonIcon);
        button.appendChild(buttonText);
        button.addEventListener('click', action);
        
        sidebar.appendChild(button);
    });
}