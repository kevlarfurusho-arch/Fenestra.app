function createBillCard(bill) {
    const card = document.createElement('div');
    card.className = 'bill-card';

    const title = document.createElement('h3');
    title.textContent = bill.title || "Untitled Bill";

    const number = document.createElement('p');
    number.textContent = `${bill.type.toUpperCase()} ${bill.number}`;

    card.appendChild(title);
    card.appendChild(number);

    return card;
}