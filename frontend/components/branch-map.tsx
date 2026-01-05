"use client";

import { MapContainer, TileLayer, Marker, useMapEvents } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import L from "leaflet";
import { useEffect, useState } from "react";

// Fix Leaflet icons
const iconUrl = "https://unpkg.com/leaflet@1.7.1/dist/images/marker-icon.png";
const iconRetinaUrl = "https://unpkg.com/leaflet@1.7.1/dist/images/marker-icon-2x.png";
const shadowUrl = "https://unpkg.com/leaflet@1.7.1/dist/images/marker-shadow.png";

const DefaultIcon = L.icon({
    iconUrl,
    iconRetinaUrl,
    shadowUrl,
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});

L.Marker.prototype.options.icon = DefaultIcon;

function LocationMarker({ position, setPosition }: { position: [number, number] | null, setPosition: (pos: [number, number]) => void }) {
  useMapEvents({
    click(e) {
      setPosition([e.latlng.lat, e.latlng.lng]);
    },
  });

  return position === null ? null : (
    <Marker position={position} />
  );
}

export default function BranchMap({ 
    lat, 
    lng, 
    onChange 
}: { 
    lat?: number, 
    lng?: number, 
    onChange: (lat: number, lng: number) => void 
}) {
    const [position, setPosition] = useState<[number, number] | null>(lat && lng ? [lat, lng] : null);

    useEffect(() => {
        if (lat && lng) setPosition([lat, lng]);
    }, [lat, lng]);

    const handleSetPosition = (pos: [number, number]) => {
        setPosition(pos);
        onChange(pos[0], pos[1]);
    };

    return (
        <MapContainer 
            center={position || [19.4326, -99.1332]} // Default CDMX
            zoom={13} 
            scrollWheelZoom={false} 
            className="h-[300px] w-full rounded-md z-0"
        >
            <TileLayer
                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />
            <LocationMarker position={position} setPosition={handleSetPosition} />
        </MapContainer>
    );
}
