import "./pagination.css"

type PaginationProps = {
    first?: number;
    prev?: number;
    current: number;
    next?: number;
    last?: number;
    pageNumbers: number[];
    changePage: (page:number) => void;
};

export function Pagination({
    first,
    prev,
    current,
    next,
    last,
    pageNumbers,
    changePage
}: PaginationProps) {
    return (
        <div className="paginationNav">
            <button className="paginationButton" onClick={() => first !== undefined && changePage(first)} disabled={current === first}><i className="fa-solid fa-backward-fast"></i></button>
            <button className="paginationButton" onClick={() => prev !== undefined && changePage(prev)} disabled={current === first}><i className="fa-solid fa-backward-step"></i></button>            
            {pageNumbers.map((page) => (
                <button
                    key={page}
                    className="paginationButton"
                    disabled={page === current}
                    onClick={() => changePage(page)}
                    style={{ fontWeight: page === current ? "bold" : "normal" }}
                >
                    {page + 1}
                </button>
            ))}
            <button className="paginationButton" onClick={() => next !== undefined && changePage(next)} disabled={current === last}><i className="fa-solid fa-forward-step"></i></button>
            <button className="paginationButton" onClick={() => last !== undefined && changePage(last)} disabled={current === last}><i className="fa-solid fa-forward-fast"></i></button>
        </div>
    )
}