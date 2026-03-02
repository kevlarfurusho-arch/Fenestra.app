interface SidebarButtonProps {
    name: string;
    iconClass: string;
    onClick: () => void;
    id?: string;
    className?: string;
    active?: boolean;
}

export function SidebarButton({
    name,
    iconClass,
    onClick,
    id,
    className,
    active = false,
}: SidebarButtonProps) {
    const buttonClassName = [
        className,
        active ? 'current': null,
    ]
    .filter(Boolean)
    .join(' ');

    return (
        <button
            className={buttonClassName}
            id={id}
            onClick={onClick}
            disabled={active}
        >
            <i
                className={iconClass}
                aria-hidden="true"
            ></i>
            <span
                className="buttonName"
            >{name}
            </span>
        </button>
    );
}