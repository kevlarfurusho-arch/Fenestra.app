import type { Bill, HateoasPage } from "../types/dto/bill.dto";

const key = "billSummaryList"

export function extractBills(page: HateoasPage<Bill, typeof key>): {
    bills: Bill[];
    links: typeof page._links
    pageInfo: typeof page.page
} {
    const bills: Bill[] = page._embedded[ key ];
    const links = page._links;
    const pageInfo = page.page;

    return { bills, links, pageInfo }
}

export interface billView{
    billID: string;
    title: string;
    updateDate: string;
}

export function mapBillview(
    bill: Bill
): billView {
    return {
        billID: `${bill.billType} ${bill.billNumber}`,
        title: `${bill.title}`,
        updateDate: `${bill.updateDate}`
    };
}