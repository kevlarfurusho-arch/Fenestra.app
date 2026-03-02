import { useEffect, useState } from "react";
import type { Bill, HateoasPage } from "../types/dto/bill.dto";
import { fetchSenateBills } from "../api/congress.api";
import { extractBills, mapBillview } from "../mappers/summary.mapper";
import { Pagination } from "../components/pagination"
import { Table } from "../components/tables/table.component";
import "./page.css"

export function Senate() {
    // Key must match the method name returned by the API
    const key = "billSummaryList" as const;

    // sets the values for the "Display" dropdown.
    const pageSizes = [10, 20, 50, 100, 200]

    const [ bills, setBills ] = useState<Bill[]>([]);
    const [ links, setLinks ] = useState<HateoasPage<Bill, typeof key>["_links"] | null>(null);
    const [ currentPage, setCurrentPage ] = useState<number>(0);
    const [ currentSize, setCurrentSize ] = useState<number>(10);
    const [ pageInfo, setPageInfo ] = useState<HateoasPage<Bill, typeof key>['page']|null>(null);    

    useEffect (() => {
        let cancelled = false;
        fetchSenateBills(currentPage, currentSize)
        .then(page => {
            if (cancelled) {
                return;
            }

            const {bills, links, pageInfo} = extractBills(page);
            setBills(bills);
            setLinks(links);
            setPageInfo(pageInfo);
        })
        .catch(err => {
            if (!cancelled) {
                console.error("fetch failed:", err)
                return (<div>Unable to fetch House Bills.</div>)
            }
        });

        return () => {
            cancelled = true;
        };
    }, [currentPage, currentSize]);

    // "Loading" value displayed until the api responds and objects "pageInfo" and "links" are populated.
    if (!pageInfo || !links) {
        return <div>Loading Bills...</div>
    }

    const first = 0;
    const self = pageInfo!.number;
    const last = pageInfo!.totalPages -1;
    const prev = Math.max(self - 1, first);
    const next = Math.min(self + 1, last);


    if (self === undefined || first === undefined || last === undefined) {
        return <div>Pagination metadata missing.</div>;
    }
    const current = self;
    const pageWindow = 3;
    const start = Math.max(first, current - pageWindow);
    const end = Math.min(last, current + pageWindow);
    const pageNumbers: number[] = [];
    for (let i = start; i <= end; i++) {
        pageNumbers.push(i);
    }

    const displayBills = bills.map(mapBillview)
    type BillRow = typeof displayBills[number]
    const billColumns: {header: string; accessor: keyof BillRow }[] = [
        { header: "Bill ID", accessor: "billID" },
        { header: "Title", accessor: "title" }
    ];
    return (
        <div className="pageContent">
            <div className="pageTitle">Senate Bills grid</div>
            <span>Display: 
                <select
                    value={currentSize}
                    onChange={(e) => {
                        setCurrentSize(Number(e.target.value));
                        setCurrentPage(0);
                    }}
                >
                    {pageSizes.map((size) => (
                        <option key={size} value={String(size)}>
                            {size}
                        </option>
                    ))}
                </select>
            </span>
            <Table
                data={displayBills}
                columns={billColumns}
            />
            <Pagination
                first={first}
                prev={prev}
                current={current}
                next={next}
                last={last}
                pageNumbers={pageNumbers}
                changePage={setCurrentPage}
            />
        </div>
    );
}

export default Senate;