import Address from "./address";
import User from "./user";

interface Call {
    status: string,
    duty: any,
    description: string,
    address: Address,
    author: User | any,
    [key:string]: any
}

export default Call