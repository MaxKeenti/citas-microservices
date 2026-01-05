import { SessionOptions } from "iron-session";
import { User } from "./definitions";

export interface SessionData {
  user?: User;
  isLoggedIn: boolean;
}

export const sessionOptions: SessionOptions = {
  password: "complex_password_at_least_32_characters_long", // In real app, use ENV variable
  cookieName: "citas_microservices_session",
  cookieOptions: {
    secure: process.env.NODE_ENV === "production",
    httpOnly: true,
  },
};

export const defaultSession: SessionData = {
  isLoggedIn: false,
};
