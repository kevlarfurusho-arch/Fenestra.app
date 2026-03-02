import type { Bill } from "../types/dto/bill.dto";

export function isValidBill(
    data: unknown
): data is Bill {
    if (typeof data !== "object" || data === null) return false;

    const obj = data as any;

    return (
        typeof obj.congress === "number" &&
        Number.isInteger(obj.congress) &&
        typeof obj.billNumber === "number" &&
        Number.isInteger(obj.billNumber) &&
        typeof obj.billType === "string" &&
        obj.billType.length > 0 &&
        typeof obj.title === "string" &&
        obj.title.length > 0
    );
}