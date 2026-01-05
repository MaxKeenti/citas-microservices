import { getIronSession } from "iron-session";
import { cookies } from "next/headers";
import { sessionOptions, SessionData } from "@/lib/session";
import { redirect } from "next/navigation";
import { ProfileForm } from "./profile-form";

export default async function ProfilePage() {
    const cookieStore = await cookies();
    const session = await getIronSession<SessionData>(cookieStore, sessionOptions);

    if (!session.isLoggedIn || !session.user) {
        redirect("/login");
    }

    return (
        <div className="container mx-auto py-10 px-4">
             <div className="mb-8 text-center">
                <h1 className="text-3xl font-bold">Mi Perfil</h1>
                <p className="text-muted-foreground mt-2">Gestiona tu información personal</p>
            </div>
            <ProfileForm user={session.user} />
        </div>
    );
}
