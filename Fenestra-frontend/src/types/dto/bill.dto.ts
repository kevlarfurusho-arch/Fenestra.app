export interface Bill {
    congress: number;
    billNumber: number;
    billType: string;
    title: string;
}

/* 
// TODO: Move this out of bill.dto and into its own folder for UI-Ready components. Something like view?
This is unused.
export interface BillSummary {
    id: string;
    displayId: string;
    title: string;
}
*/

// TODO: Update BillDetail to include detailed bill information for modal
export interface BillDetail {
    congress: number;
    billNumber: number;
    billType: string;
    title: string;
}

export interface HateoasPage<T, K extends string> {
    _embedded: {
        [P in K]: T[];
    };
    _links: {
        first?: { href: string };
        prev?: { href: string };
        self: { href: string };
        next?: { href: string };
        last?: { href: string };
    };
    page: {
        number: number;
        size: number;
        totalElements: number;
        totalPages: number;
    };
}