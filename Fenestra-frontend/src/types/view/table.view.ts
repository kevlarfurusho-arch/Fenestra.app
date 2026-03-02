export interface TableProps<T>{
    data: T[];
    columns: {header: string; accessor: keyof T}[]
}
