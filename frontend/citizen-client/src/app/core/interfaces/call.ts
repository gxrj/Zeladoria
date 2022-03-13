import Address from "./address";
import Duty from "./duty";
import User from "./user";

interface Call {
    id: string | null,
    duty: Duty | null,
    protocol: string | null,
    status: string,
    description: string,
    address: Address,
    images: string[] | null,
    author: User | any,
    created_at: string|null
}

export default Call