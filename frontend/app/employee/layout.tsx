import { getIronSession } from "iron-session";
import { sessionOptions, SessionData } from "@/lib/session";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";

export default async function EmployeeLayout({
    children,
}: {
    children: React.ReactNode;
}) {
    const session = await getIronSession<SessionData>(await cookies(), sessionOptions);

    if (!session.isLoggedIn) {
        redirect("/login");
    }

    const isEmployee = session.user?.roles.includes("employee");

    if (!isEmployee) {
        redirect("/dashboard");
    }

    return (
        <div className="min-h-screen bg-gray-100 dark:bg-gray-900">
            <main className="container mx-auto py-8">
                {children}
            </main>
        </div>
    );
}
