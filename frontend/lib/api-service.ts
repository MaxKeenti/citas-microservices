import { Service } from "@/lib/definitions";

export async function getServices(): Promise<Service[]> {
    const backendUrl = process.env.CATALOG_SERVICE_URL || "http://catalog-service:8080";
    try {
        const res = await fetch(`${backendUrl}/services`, {
            cache: 'no-store' // Dynamically fetch every time
        });
        if (!res.ok) {
            console.error("Failed to fetch services:", res.status, res.statusText);
            return [];
        }
        return res.json();
    } catch (e) {
        console.error("Error fetching services:", e);
        return [];
    }
}
