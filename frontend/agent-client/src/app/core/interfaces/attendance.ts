import Call from "./call";
import User from "./user";

export interface Attendance {
    call: Call,
    protocol: string,
    description: string,
    responsible: User,
    [key: string]: any
}