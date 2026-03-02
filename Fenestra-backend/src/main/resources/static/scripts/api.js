export async function fetchLegislation(chamber, output) {
    // Construct the URL dynamically
    let url;

    if (chamber === "house") {
        url = `/api/congress/test/119/hr/4405`; // testing only. Change  to /api/congress/bills endpoint for prod.
    } else if (chamber === "senate") {
        url = `/api/congress/test/119/s/10`; // testing only. Change  to /api/congress/bills endpoint for prod.
    } else {
        output.textContent = "Invalid chamber";
        return;
    }

    try {
        output.textContent = `Fetching ${chamber} legislation...`;
        const response = await fetch(url);
        if (!response.ok) throw new Error("Network response not ok");
        const data = await response.json();
        output.textContent = JSON.stringify(data, null, 2);
    } catch (err) {
        output.textContent = "Error fetching data: " + err.mes;
    }
}
