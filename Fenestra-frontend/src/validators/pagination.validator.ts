import type { HateoasPage, Bill } from "../types/dto/bill.dto";
import { isValidBill } from "./bill.validator";

export function isValidPage<K extends string>(
    data: unknown,
    key: K
): data is HateoasPage<Bill, K> {
    if (typeof data !== "object" || data === null) return false;

    const obj = data as any;

    if (
        typeof obj._embedded !== "object" ||
        obj._embedded === null ||
        !Array.isArray(obj._embedded[key]) ||
        typeof obj._links !== "object" ||
        obj._links === null ||
        typeof obj.page !== "object" ||
        obj.page === null
    ) {
        return false;
    }
    return obj._embedded[key].every(isValidBill)
}