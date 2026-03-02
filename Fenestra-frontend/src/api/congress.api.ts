import type { Bill, HateoasPage, BillDetail } from "../types/dto/bill.dto";
import { isValidPage } from "../validators/pagination.validator";

export async function fetchHouseBills(
    page: number = 0,
    size: number = 10
): Promise<HateoasPage<Bill, "billSummaryList">> {
    const key = "billSummaryList" as const;
    const url = `/api/congress/bill/house?page=${page}&size=${size}`
    const res = await fetch(url);

    if (!res.ok) {
        throw new Error(`Failed to fetch house bills: ${res.status}`)
    };

    const data: unknown = await res.json();
    
    if (!isValidPage(data, key)){
        throw new Error(`Invalid API Response shape!`)
    }

    return data;
}

export async function fetchSenateBills(
    page: number = 0,
    size: number = 10
): Promise<HateoasPage<Bill, "billSummaryList">> {
    const key = "billSummaryList" as const;
    const url = `/api/congress/bill/senate?page=${page}&size=${size}`
    const res = await fetch(url);

    if (!res.ok) {
        throw new Error(`Failed to fetch house bills: ${res.status}`)
    };

    const data: unknown = await res.json();
    
    if (!isValidPage(data, key)){
        throw new Error(`Invalid API Response shape!`)
    }

    return data;
}

// TODO: Update BillDetail fetch to include subjects and policy areas
export async function fetchBillDetail(
    congress: number,
    billType: string,
    billNumber: number
): Promise<BillDetail> {
    const res = await fetch(`/api/congress/bill/${congress}/${billType}/${billNumber}`);
    
    if (!res.ok) {
        throw new Error("Failed to fetch bill detail")
    };

    return res.json();
}