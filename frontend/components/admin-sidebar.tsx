"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import { cn } from "@/lib/utils";
import {
  LayoutDashboard,
  Scissors,
  Users,
  CalendarDays,
  MapPin,
  LogOut
} from "lucide-react";

const sidebarItems = [
  { href: "/admin", label: "Dashboard", icon: LayoutDashboard },
  { href: "/admin/services", label: "Servicios", icon: Scissors },
  { href: "/admin/employees", label: "Empleados", icon: Users },
  { href: "/admin/schedules", label: "Horarios", icon: CalendarDays },
  { href: "/admin/branches", label: "Sucursales", icon: MapPin },
  { href: "/admin/price-lists", label: "Lista de Precios", icon: CalendarDays }, // Reusing Calendar or similar icon
];

export function AdminSidebar() {
  const pathname = usePathname();

  return (
    <div className="flex flex-col h-screen w-64 bg-gray-900 text-white border-r border-gray-800">
      <div className="p-6 border-b border-gray-800">
        <h1 className="text-2xl font-bold tracking-tight">BarberAdmin</h1>
      </div>
      
      <nav className="flex-1 p-4 space-y-2">
        {sidebarItems.map((item) => {
          const Icon = item.icon;
          const isActive = pathname === item.href;
          
          return (
            <Link
              key={item.href}
              href={item.href}
              className={cn(
                "flex items-center gap-3 px-4 py-3 rounded-lg transition-colors",
                isActive 
                  ? "bg-primary text-primary-foreground" 
                  : "hover:bg-gray-800 text-gray-400 hover:text-white"
              )}
            >
              <Icon className="w-5 h-5" />
              <span className="font-medium">{item.label}</span>
            </Link>
          );
        })}
      </nav>

      <div className="p-4 border-t border-gray-800">
        <form action="/api/auth/logout" method="POST">
            <button
              type="submit"
              className="flex items-center gap-3 px-4 py-3 rounded-lg text-red-400 hover:bg-gray-800 hover:text-red-300 transition-colors w-full"
            >
              <LogOut className="w-5 h-5" />
              <span className="font-medium">Cerrar Sesión</span>
            </button>
        </form>
      </div>
    </div>
  );
}
