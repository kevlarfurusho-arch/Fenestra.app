import { createSidebarButtons } from "./navigation.js";
import { showDashboard } from './dashboard.js';
import { fetchLegislation } from './api.js';

// Get containers
const sidebar = document.getElementById('sidebar');
const output = document.getElementById('output');

const actions = {
    showDashboard: () => showDashboard(output),
    fetchLegislation: (chamber) => fetchLegislation(chamber, output),
}



createSidebarButtons(sidebar, actions);