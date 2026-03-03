import type { TableProps } from "../../types/view/table.view"
import "./tables.css"
import { useRef, useEffect } from 'react';


export function Table<T> (
    { data, columns }: TableProps<T>
) {
    const wrapperRef = useRef<HTMLDivElement>(null);
    const rowHeight = 32;
    const rowsPerScroll = 3;

    useEffect(() => {
        const wrapper = wrapperRef.current;
        if (!wrapper) return;

        const handleWheel = (e:WheelEvent) => {
            e.preventDefault();

            const direction = e.deltaY > 0 ? 1 : -1;
            
            const scrollTarget = wrapper.scrollTop + direction * rowHeight * rowsPerScroll;

            wrapper.scrollTop = Math.round(scrollTarget/rowHeight) * rowHeight;
        };

        wrapper.addEventListener('wheel', handleWheel, { passive: false });
        return () => {
            wrapper.removeEventListener('wheel', handleWheel);
        };
    }, [rowHeight, rowsPerScroll])

    return (
        <div className="tableWrapper" ref={wrapperRef}>
            <table>
                <thead>
                    <tr>
                        {columns.map((col) => (
                            <th key={String(col.accessor)} className={String(col.accessor)}>{col.header}</th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                    {data.map((row, index) => (
                        <tr key={index}>
                            {columns.map(col => (
                                <td key={String(col.accessor)} className={String(col.accessor)}>{String(row[col.accessor])}</td>
                            ))}
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}