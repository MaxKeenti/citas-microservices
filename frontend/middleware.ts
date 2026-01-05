import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';
import { getIronSession } from 'iron-session';
import { sessionOptions, SessionData } from './lib/session';

export async function middleware(request: NextRequest) {
  const session = await getIronSession<SessionData>(request,  await NextResponse.next(), sessionOptions);

  // Protected routes pattern
  if (request.nextUrl.pathname.startsWith('/dashboard') || 
      request.nextUrl.pathname.startsWith('/appointments')) {
    
    if (!session.isLoggedIn) {
      return NextResponse.redirect(new URL('/login', request.url));
    }
  }

  return NextResponse.next();
}

export const config = {
  matcher: ['/dashboard/:path*', '/appointments/:path*'],
};
