import { getIronSession } from "iron-session";
import { cookies } from "next/headers";
import { sessionOptions, SessionData } from "@/lib/session";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

export default async function DashboardPage() {
  const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
  const user = session.user;

  return (
    <div className="p-8">
      <Card>
        <CardHeader>
          <CardTitle>Welcome, {user?.nombre} {user?.primerApellido}!</CardTitle>
        </CardHeader>
        <CardContent>
          <p className="text-muted-foreground">You have successfully logged in.</p>
          <div className="mt-4">
             <h3 className="font-semibold">User Details</h3>
             <ul className="list-disc list-inside mt-2">
               <li>ID: {user?.id}</li>
               <li>Roles: {user?.roles?.join(", ")}</li>
             </ul>
          </div>
          <div className="mt-6">
            <a 
              href="/services" 
              className="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 bg-primary text-primary-foreground hover:bg-primary/90 h-10 px-4 py-2"
            >
              View Available Services
            </a>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
