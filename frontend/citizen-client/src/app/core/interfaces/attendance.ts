import { Agent } from "./agent";
import Call from "./call";

export interface Attendance {
    call: Call,
    protocol: string,
    description: string,
    responsible: Agent,
    type: string,
    feedback: string | null,
    [key: string]: any
}