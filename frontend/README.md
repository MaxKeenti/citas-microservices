# Frontend Client

The **Frontend** application is a modern web interface built with Next.js, serving as the primary interaction point for users (Clients and Admins).

## Features

- **User Interface**: Responsive design for booking appointments.
- **Admin Dashboard**: Interfaces for managing services, employees, and appointments.
- **Client Flow**: Step-by-step wizard for scheduling appointments.
- **Authentication**: Login and session handling.

## Tech Stack

- **Framework**: Next.js (React)
- **Styling**: Tailwind CSS / Vanilla CSS
- **Language**: TypeScript

## Configuration

The application is configured via environment variables (see `.env.local` or Docker compose).

| Variable | Description | Default |
|----------|-------------|---------|
| `NEXT_PUBLIC_API_URL` | Base URL for API calls (Client-side) | `http://localhost:3000/api` |
| `ACCESS_CONTROL_SERVICE_URL` | URL for Access Control Service (SSR) | `http://localhost:8081` |
| `CATALOG_SERVICE_URL` | URL for Catalog Service (SSR) | `http://localhost:8082` |
| `HRESOURCES_SERVICE_URL` | URL for HR Service (SSR) | `http://localhost:8083` |
| `APPOINTMENT_SERVICE_URL` | URL for Appointment Service (SSR) | `http://localhost:8084` |

## Development

### Prerequisites
- Node.js 18+
- npm / yarn / pnpm

### Running in Dev Mode
```bash
npm install
npm run dev
```
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

### Building for Production
```bash
npm run build
npm start
```
