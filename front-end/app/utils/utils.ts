export function formatDate(input: string | undefined): string {
    if (input != null && input != undefined) {
        const date = new Date(input);

        if (isNaN(date.getTime())) return 'Invalid Date';

        const pad = (n: number) => n.toString().padStart(2, '0');

        const month = pad(date.getMonth() + 1); // Months are 0-based
        const day = pad(date.getDate());
        const year = date.getFullYear();

        const hours = pad(date.getHours());
        const minutes = pad(date.getMinutes());

        return `${month}/${day}/${year} ${hours}:${minutes}`;
    } else {
        return '';
    }

}
